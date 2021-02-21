package model;

import lombok.Data;
import util.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

@Data
public class Environment extends Observable{
    private List<List<Cell>> grid;
    private List<Agent> agents;

    boolean mutex = true;

    public Environment(){
        int gridSize= Constant.GRID_SIZE;

        //Remplissage de cases dans la grille
        List<Position>positions=new ArrayList<>();
        this.grid=new ArrayList<>();
        for(int i=0;i<gridSize;i++){
            List<Cell>list=new ArrayList<>();
            for(int j=0;j<gridSize;j++){
                Position p=new Position(i,j);
                positions.add(p);
                list.add(new Cell(p));
            }
            this.grid.add(list);
        }

        //Creation des agents
        List<Position>positionsCopy=new ArrayList<>(positions);
        this.agents=new ArrayList<>();
        int nbAgents=(gridSize*gridSize)-1;
        for(int i=0;i<nbAgents;i++){
            Position target=positions.remove(0);
            Position position=positionsCopy.remove(new Random().nextInt(positionsCopy.size()));
            Agent a=new Agent(this,position,target);
            this.grid.get(position.getX()).get(position.getY()).setAgent(a);
            this.agents.add(a);
        }
    }
    
    public boolean takeMutex() {
    	synchronized (this) {
    		if (this.mutex) {
    			this.mutex = false;
    			return true;
    		}
    		return false;
        }
    }
    
    public void releaseMutex() {
    	synchronized (this) {
    		this.mutex = true;
        }
    }

    public void move(Agent a,Position p){
    	synchronized (this) {
            this.grid.get(a.getPosition().getX()).get(a.getPosition().getY()).setAgent(null);
            this.grid.get(p.getX()).get(p.getY()).setAgent(a);
            a.setPosition(p);
            updateView();
        }
        
    }

    public Position getEmptyCellPosition(){
        for(List<Cell> listC : this.grid){
            for(Cell c : listC){
                if(!c.hasAgent()){
                    return c.getPosition();
                }
            }
        }
        return null;
    }

    public boolean isGridCompleted(){
        for(Agent a : agents){
            if(!a.isOnTarget()){
                return false;
            }
        }
        System.out.println("--- TAQUIN COMPLETED ---");
        return true;
    }

    public void display(){
        int gridSize= Constant.GRID_SIZE;
        System.out.println();
        for(int i=0;i<gridSize;i++){
            StringBuilder line= new StringBuilder();
            for(int j=0;j<gridSize;j++){
                line.append("[").append(this.grid.get(i).get(j).toString()).append("]");
            }
            System.out.println(line);
        }
        System.out.println();
    }

	public List<List<Cell>> getGrid() {
		return grid;
	}

	public List<Agent> getAgents() {
		return agents;
	}
	
	public Agent get(Position position) {
		int index = Position.posToIndex(position.getX(), position.getY(), Constant.GRID_SIZE);
		if (index >= agents.size()) {
			return null;
		}else {
        return agents.get(index);
		}
	}
	
	public void updateView() {
        notifyObservers();
        setChanged();
    }
}
