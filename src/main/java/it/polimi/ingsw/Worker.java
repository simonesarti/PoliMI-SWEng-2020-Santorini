package it.polimi.ingsw;

public class Worker {

    private Position previousPosition;
    private Position currentPosition;

    public Worker(Position startingPosition) {
        this.previousPosition=null;
        this.currentPosition=startingPosition;
    }

}
