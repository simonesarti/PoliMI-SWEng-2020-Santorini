package it.polimi.ingsw.GUI;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel{

    private Image background;

    public MainPanel(){
        background=Images.getIcon(Images.INITIAL_BACKGROUND);
        initializePanel();
    }

    private void initializePanel(){
        setPanelLook();
        setPanelProperties();
    }

    private void setPanelLook(){
        setPreferredSize(new Dimension(SettingsGUI.frameWidth,SettingsGUI.frameHeight));
    }

    private void setPanelProperties(){}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background,0,0,null);
    }



}
