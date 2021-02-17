package model;

import lombok.Data;
import util.Constant;

@Data
public class Cell {

    private Position position;
    private int number;

    private Agent agent;

    public Cell(Position position){
        this.position=position;
        this.number=(position.getX()*Constant.GRID_SIZE)+position.getY();
        this.agent=null;
    }

    public boolean hasAgent(){
        return this.agent!=null;
    }

    @Override
    public String toString(){
        //return String.valueOf(number);
        return (hasAgent()) ? this.agent.toString() : "  ";
    }
}
