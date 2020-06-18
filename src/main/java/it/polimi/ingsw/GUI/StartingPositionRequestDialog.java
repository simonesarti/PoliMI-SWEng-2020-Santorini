package it.polimi.ingsw.GUI;

import javax.swing.*;

/**
 * Dialog used to ask the user for the positions in which he wants to place his workers at
 * the beginning of the game. Contains the panel which does that
 */
public class StartingPositionRequestDialog extends JDialog {

    public StartingPositionRequestDialog(JFrame frame, GuiController guiController) {

        super(frame);
        setDialog();
        add(new PositionSelectionPanel(this,guiController));
        showDialog(frame);

    }

    private void setDialog(){
        setTitle("Starting Position selection");
        setSize(250, 250);
    }
    private void showDialog(JFrame frame){
        pack();
        setLocationRelativeTo(frame);
        setModal(true);
        setVisible(true);
    }

}