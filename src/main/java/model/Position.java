package model;

import lombok.Data;
import util.Constant;

import java.util.Objects;

@Data
public class Position {
    private int x;
    private int y;

    public Position(int x, int y){
        this.x=x;
        this.y=y;
    }

    public Position(Position p, Direction d){
        this.x=p.getX()+d.getX();
        this.y=p.getY()+d.getY();
    }

    public int getDistance(Position target){
        return Math.abs(this.x-target.getX())+Math.abs(this.y-target.getY());
    }

    public boolean isInGrid(){
        return this.x >= 0 && this.x < Constant.GRID_SIZE && this.y >= 0 && this.y < Constant.GRID_SIZE;
    }

    public boolean equals(Position p){
        return this.x == p.getX() && this.y == p.getY();
    }

    @Override
    public String toString(){
        return "("+this.x+","+this.y+")";
    }
}
