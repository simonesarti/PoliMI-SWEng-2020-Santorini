package it.polimi.ingsw.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndTurnButton extends JButton implements ActionListener {

    public EndTurnButton() {
        setIcons();
        setPosition();
        setSize();
    }

    //TODO
    private void setIcons(){
        setIcon(Images.getIcon(Images.END_TURN_BUTTON_ICON));
        setPressedIcon(Images.getIcon(Images.PRESSED_END_TURN_BUTTON_ICON));
    }
    private void setPosition(){}
    private void setSize(){}


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
