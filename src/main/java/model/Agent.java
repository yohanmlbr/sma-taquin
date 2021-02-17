package model;

import lombok.Data;
import util.Constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Data
public class Agent {
    private Environment env;

    private Position position;
    private int number;
    private Position target;
    private Message message;

    public Agent(Environment env, Position position, Position target){
        this.env=env;
        this.position=position;
        this.number=(target.getX()* Constant.GRID_SIZE)+target.getY();
        this.target=target;
        this.message=null;
    }

    public void percept(){

    }

    public void action(){
        if(hasMessage()&&isOnTarget() || !isOnTarget()){
            Direction d = chooseDirection();
            Position newP = new Position(this.position,d);
            Cell c=this.env.getGrid().get(newP.getX()).get(newP.getY());
            if(!c.hasAgent()){
                move(newP);
                System.out.println("Agent "+this+" move to "+newP);
            }
            else{
                sendMessageToAgent(c.getAgent());
                System.out.println("Agent "+this+" send msg to "+c.getAgent());
            }
        }
    }

    public void sendMessageToAgent(Agent receiver){
        receiver.message=new Message(this,receiver);
    }

    public void deleteMessage(){
        this.message=null;
    }


    public void move(Position p){
        this.env.move(this,p);
        deleteMessage();
    }

    public boolean hasMessage(){
        return this.message!=null;
    }

    public boolean isOnTarget(){
        return this.position.equals(this.target);
    }

    public Direction chooseDirection(){
        List<Direction> directions= Arrays.asList(Direction.values());
        List<Direction> bestDirections = new ArrayList<>();
        //force to move if is on target
        if(isOnTarget()||hasMessage()){
            for(Direction d : directions){
                Position newP = new Position(this.position,d);
                if(calculateEmptyCellDistanceFromNewPosition(newP)<calculateEmptyCellDistance() && newP.isInGrid()){
                    bestDirections.add(d);
                }
            }
        }
        //find best directions
        else{
            for(Direction d : directions){
                Position newP = new Position(this.position,d);
                if(calculateTargetDistanceFromNewPosition(newP)<calculateTargetDistance() && newP.isInGrid()){
                    bestDirections.add(d);
                }
            }
        }
        //pick the free empty direction
        for(Direction d : bestDirections){
            Position newP = new Position(this.position,d);
            if(!this.env.getGrid().get(newP.getX()).get(newP.getY()).hasAgent()){
                return d;
            }
        }
        //pick random direction
        return bestDirections.get(new Random().nextInt(bestDirections.size()));
    }

    public int calculateTargetDistance(){
        return this.target.getDistance(this.position);
    }

    public int calculateTargetDistanceFromNewPosition(Position p){
        return this.target.getDistance(p);
    }

    public int calculateEmptyCellDistance(){
        return this.env.getEmptyCellPosition().getDistance(this.position);
    }

    public int calculateEmptyCellDistanceFromNewPosition(Position p){
        return this.env.getEmptyCellPosition().getDistance(p);
    }

    @Override
    public String toString(){
        String s="";
        if(this.number<10){
            s="0";
        }
        return s+this.number;
    }
}
