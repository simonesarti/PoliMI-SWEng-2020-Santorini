package it.polimi.ingsw.GUI.messages;

/**
 * message that contains the coordinates of the cell that the player selected from the board
 */
public class BoardSelection extends ActionMessage{

    private final int x;
    private final int y;

    public BoardSelection(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
