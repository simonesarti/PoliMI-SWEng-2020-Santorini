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

        //TODO Ale: Simo, ho cambiato questa cosa perché ho ritenuto più sicuro che la lista di movimenti fosse composta da nuove istanze di Position.
        //prima era scomodo scrivere test con una sola istanza di Position perché la equals tra posizioni restituiva
        //sempre vero dato che il riferimento era allo stesso oggetto
        Position pos = new Position(position.getX(),position.getY(),position.getZ());
        turnMovementList.add(0,pos);
    }

    public void trimMovementHistory(){
        Position p=turnMovementList.get(0);
        turnMovementList.clear();
        turnMovementList.add(p);
    }

}
