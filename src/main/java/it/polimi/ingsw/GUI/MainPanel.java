package it.polimi.ingsw.GUI;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel{

    public MainPanel(){
        initializePanel();
    }

    private void initializePanel(){
        setPanelLook();
        setPanelProperties();
    }

    private void setPanelLook(){
        setPreferredSize(new Dimension(SettingsGUI.frameWidth,SettingsGUI.frameHeight));
        setBackground(SettingsGUI.backgroundColour);
    }
    private void setPanelProperties(){}

}
