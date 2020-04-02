package it.polimi.ingsw.model;

import java.util.List;
import java.util.ArrayList;

public class Worker {

    private Colour colour;
    private List<Position> turnMovementList = new ArrayList<>();

    public Worker(Colour colour) {
        this.colour=colour;
    }

    public Position getCurrentPosition(){
        return turnMovementList.get(0);
    }

    public void movedToPosition(Position position){
        turnMovementList.add(0,position);
    }

    public void trimMovementHistory(){
        Position p=turnMovementList.get(0);
        turnMovementList.clear();
        turnMovementList.add(p);
    }

}
