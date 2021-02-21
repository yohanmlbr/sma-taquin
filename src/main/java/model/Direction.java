package model;

import lombok.Getter;

@Getter
public enum Direction {
    N(-1,0),
    S(1,0),
    O(0,-1),
    E(0,1);

    private int x;
    private int y;

    Direction(int x, int y){
        this.x=x;
        this.y=y;
    }

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
