package it.polimi.ingsw.GUI;

import it.polimi.ingsw.client.ClientSideConnection;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel{

    private final Image background;

    public MainPanel(JFrame frame){
        background=Images.getImage(Images.INITIAL_BACKGROUND);
        setPreferredSize(new Dimension(frame.getHeight(),frame.getWidth()));
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background,0,0,null);
    }



}
