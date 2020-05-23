package it.polimi.ingsw.GUI;

import java.awt.*;

public abstract class Sprite {

    private Image image;
    private boolean inGame;

    protected int x;
    protected int y;

    public Sprite(){
        inGame=false;
    }

    public abstract  void move();

    public Image getImage() {
        return image;
    }

    public boolean isInGame() {
        return inGame;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
