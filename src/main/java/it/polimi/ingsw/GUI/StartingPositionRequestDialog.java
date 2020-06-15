package it.polimi.ingsw.GUI;

import javax.swing.*;

public class StartingPositionRequestDialog extends JDialog {

    public StartingPositionRequestDialog(JFrame frame, GuiController guiController) {
        super(frame);
        setLocationRelativeTo(null);
        setTitle("Starting Position selection");
        setSize(250, 250);
        add(new PositionSelectionPanel(this,guiController));
        pack();
        setVisible(true);

    }

}