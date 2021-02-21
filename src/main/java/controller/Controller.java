package controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Environment;
import model.Position;
import model.Agent;
import util.Constant;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.shape.Rectangle;

public class Controller extends Application implements Observer {
	
	Environment environment;
    private int sceneSize = 300;
    private GridPane gridPane = new GridPane();
    
    private static final double gridWidth = 100;
    private static final double gridHeight = 100;

	public Controller() {
		
	}


    @Override
    public void update(Observable o, Object arg) {
        updateTaquin();
    }

	@Override
	public void start(Stage primaryStage) {
		Environment e=new Environment();
		e.display();
		e.addObserver(this);
        
		Scene scene = new Scene(gridPane, sceneSize, sceneSize);
		
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		
		startTaquin();
		
	}
	
	private void updateTaquin() {
		for (int i = 0; i < Constant.GRID_SIZE; i++) {
            for (int j = 0; j < Constant.GRID_SIZE; j++) {
            	final Environment env = environment;
            	final int finalI = i;
                final int finalJ = j;
            	Platform.runLater(new Runnable() {
                    @Override public void run() {
                    	gridPane.addColumn(finalJ);
                    	Agent agent = environment.get(new Position(finalI,finalJ));
	                    if (agent == null) {
	                    	StackPane stackPane = getRectangle("");
	                    	gridPane.add(stackPane, finalJ, finalI);
	                    } else {
	                    	StackPane stackPane = getRectangle(env.getGrid().get(finalI).get(finalJ).toString() + "");
	                    	gridPane.add(stackPane, finalJ, finalI);
	                    }
	                    
                    }
                });
            }
        }
	}
	
	private StackPane getRectangle(String content){
        Label label = new Label(content);
		label.setFont(new Font("Arial", 20));
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(gridWidth);
        rectangle.setHeight(gridHeight);
        rectangle.setStroke(Color.BLACK);
        rectangle.setFill(Color.WHITE);
        return new StackPane(rectangle, label);
    }

	private void startTaquin() {
		environment = new Environment();
		environment.addObserver(this);
		updateTaquin();
		Thread[] threads = new Thread[environment.getAgents().size()];

        for (int i = 0; i < environment.getAgents().size(); i++) {
            threads[i] = new Thread(environment.getAgents().get(i));
            threads[i].start();
        }
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
