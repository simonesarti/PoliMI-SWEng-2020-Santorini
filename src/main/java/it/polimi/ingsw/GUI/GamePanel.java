package it.polimi.ingsw.GUI;

import it.polimi.ingsw.client.ClientSideConnection;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private final ClientSideConnection connection;
    private Image gameBoard;

    public GamePanel(ClientSideConnection connection){
        this.connection=connection;
        initializePanel();
    }

    private void initializePanel(){
        setPanelLook();
        setPanelProperties();
    }

    private void setPanelLook(){
        gameBoard=Images.getImage(Images.GAMEBOARD);
    }

    private void setPanelProperties(){
        setPreferredSize(new Dimension(SettingsGUI.frameWidth,SettingsGUI.frameHeight));
        setVisible(true);
        setLayout(new GridBagLayout());
    }

    public void refresh(){
        update();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(gameBoard,0,0,null);
    }

    private void update(){}

}
