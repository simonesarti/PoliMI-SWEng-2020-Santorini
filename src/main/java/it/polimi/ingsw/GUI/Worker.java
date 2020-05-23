package it.polimi.ingsw.GUI;

public class Worker extends Sprite{


    public Worker(int x, int y) {

        //setImage();
        setX(SettingsGUI.convertToPixel(x));
        setY(SettingsGUI.convertToPixel(y));
        setInGame(true);
    }

    @Override
    public void move() {

    }
}
