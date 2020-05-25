package it.polimi.ingsw.GUI;

import it.polimi.ingsw.client.ClientSideConnection;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel{

    private final ClientSideConnection connection;
    private Image background;

    public MainPanel(ClientSideConnection connection){
        this.connection=connection;
        initializePanel();
    }

    private void initializePanel(){
        setPanelLook();
        setPanelProperties();
    }

    private void setPanelLook(){
        background=Images.getImage(Images.INITIAL_BACKGROUND);
    }

    private void setPanelProperties(){
        setPreferredSize(new Dimension(SettingsGUI.frameWidth,SettingsGUI.frameHeight));
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background,0,0,null);
    }



}
