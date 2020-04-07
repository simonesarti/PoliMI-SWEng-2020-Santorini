package it.polimi.ingsw.model;

import java.util.Objects;

public class Position {

    private int x;
    private int y;
    private int z;


    public Position() {
    }

    public Position(int x, int y, int z){
        this.x=x;
        this.y=y;
        this.z=z;
    }

    public void setPosition(int x, int y, int z){
        this.x=x;
        this.y=y;
        this.z=z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    /**
     *
     * checks if otherPosition is adjacent to this position
     * it does not deal with z-axis
     * @return false if positions are not adjacent
     */

    public boolean adjacent(int z, int y){

        if(     x < this.getX()-1 ||

                x > this.getX()+1
            )return false;
        else if(
                        y < this.getY()-1 ||

                        y > this.getY()+1
        )return false;

        else if(
                x == this.getX() && y == this.getY()
        )return false;

        else return true;


    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x &&
                y == position.y &&
                z == position.z;
    }

}

