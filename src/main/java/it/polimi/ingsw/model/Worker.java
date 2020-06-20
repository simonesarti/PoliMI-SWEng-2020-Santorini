package it.polimi.ingsw.model;

public class Worker {

    private final Colour colour;
    private final int number;
    private Position currentPosition;
    private Position previousPosition;

    /**
     * Sets the worker's colour on creation
     * @param colour is the player's colour
     * @param number is the worker's number
     */
    public Worker(Colour colour, int number) {
        this.colour=colour;
        this.number=number;
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
     * Sets the worker's starting position, and initializes to null the field previousPosition
     * @param x starting coordinate
     * @param y staring coordinate
     */
    public void setStartingPosition(int x, int y){
        currentPosition=new Position(x,y,0);
        previousPosition=new Position();
    }

    /**
     * Sets the worker's current position to newPosition, and previousPosition to the position
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
