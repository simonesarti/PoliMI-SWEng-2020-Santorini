package it.polimi.ingsw.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BuildButton extends JButton implements ActionListener {

    public BuildButton() {

        setIcons();
        setPosition();
        setSize();
    }

    //TODO
    private void setIcons(){
        setIcon(Images.getIcon(Images.BUILD_BUTTON_ICON));
    }
    private void setPosition(){}
    private void setSize(){}


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
