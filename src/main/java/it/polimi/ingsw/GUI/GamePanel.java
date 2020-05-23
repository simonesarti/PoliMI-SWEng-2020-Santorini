package it.polimi.ingsw.GUI;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private Image gameBoard;

    public GamePanel(){
        gameBoard=Images.getImage(Images.GAMEBOARD);
        initializePanel();
    }

    private void initializePanel(){
        setPanelProperties();

    }
    private void setPanelProperties(){
        setPreferredSize(new Dimension(SettingsGUI.frameWidth,SettingsGUI.frameHeight));
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
