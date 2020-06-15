package it.polimi.ingsw.GUI;

import javax.swing.*;

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
        setVisible(true);
    }

}