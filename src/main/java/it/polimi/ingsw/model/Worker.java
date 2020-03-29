package it.polimi.ingsw.model;

import java.util.List;
import java.util.ArrayList;

public class Worker {

    private Colour colour;
    private List<Position> turnMovementList = new ArrayList<>();

    public Worker(Colour colour, Position startingPosition) {
        this.colour=colour;
        turnMovementList.add(startingPosition);
    }

}
