package it.polimi.ingsw.GUI;

import javax.swing.*;
import java.awt.*;

public class ImageButton extends JButton {

    private Image buttonImage;

    public void setButtonImage(Image buttonImage){
        this.buttonImage=buttonImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(buttonImage, 0, 0, this.getWidth(), this.getHeight(), this);
        repaint();
        revalidate();

    }
}