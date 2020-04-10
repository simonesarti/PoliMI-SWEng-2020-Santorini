package it.polimi.ingsw.model;

public class Worker {

    private final Colour colour;
    private final int number;
    private Position currentPosition;
    private Position previousPosition;

    /**
     * sets the worker's colour on creation
     * @param colour player's colour
     */
    public Worker(Colour colour, int number) {
        this.colour=colour;
        this.number=number;
        currentPosition=new Position();
        previousPosition=new Position();
    }

    public Colour getColour() {
        return colour;
    }

    public int getNumber(){
        return number;
    }

    /**
     * @return the worker's current position on the gameboard
     */
    public Position getCurrentPosition(){
        return currentPosition;
    }

    /**
     *
     * @return the worker's previous position on the gameboard
     */
    public Position getPreviousPosition(){
        return previousPosition;
    }

    /**
     * sets the worker's starting position, and initializes to null the field previousPosition
     * @param x starting coordinate
     * @param y staring coordinate
     */
    public void setStartingPosition(int x, int y){
        currentPosition.setPosition(x,y,0);
    }

    /**
     * sets the worker's current position to newPosition, and previousPosition to the position
     * it was occupying before this change
     * @param x new x coordinate
     * @param y new y coordinate
     * @param z new z coordinate
     */
    public void movedToPosition(int x, int y, int z){
        previousPosition.setPosition(currentPosition.getX(),currentPosition.getY(),currentPosition.getZ());
        currentPosition.setPosition(x,y,z);
    }

}
