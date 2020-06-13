package it.polimi.ingsw.GUI;

import javax.swing.*;
import java.awt.*;

public class LobbyPanel extends JPanel {

    private final Image background;

    public LobbyPanel(JFrame frame) {
        background=Images.getImage(Images.INITIAL_BACKGROUND);
        setPreferredSize(new Dimension(frame.getHeight(),frame.getWidth()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background,0,0,this.getWidth(),this.getHeight(),this);
    }
}
