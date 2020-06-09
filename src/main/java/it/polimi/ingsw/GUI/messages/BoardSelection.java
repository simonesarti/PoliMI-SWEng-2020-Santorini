package it.polimi.ingsw.GUI.messages;

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
