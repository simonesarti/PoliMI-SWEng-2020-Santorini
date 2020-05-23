package it.polimi.ingsw.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameLoop implements ActionListener {

    private GamePanel gamepanel;

    public GameLoop(GamePanel gamepanel) {
        this.gamepanel = gamepanel;
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        gamepanel.refresh();
    }
}
