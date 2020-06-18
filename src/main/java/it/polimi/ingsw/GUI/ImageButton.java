package it.polimi.ingsw.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * this class extends Jbutton and uses the paintComponent method to show an image in such a way that it perfectly
 * fits the dimensions of the button
 */
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