package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Environment;

import java.util.Random;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Environment e=new Environment();
        e.display();

        for(int i=0;!e.isGridCompleted();i++){
            int index=new Random().nextInt(e.getAgents().size());
            System.out.println("Agent "+index+" #"+i);
            e.pickAgent(index).action();
            e.display();
        }


        Parent root = FXMLLoader.load(getClass().getResource("/fxml/sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
