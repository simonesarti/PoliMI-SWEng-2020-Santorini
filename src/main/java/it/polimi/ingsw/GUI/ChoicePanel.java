package it.polimi.ingsw.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ChoicePanel extends JPanel {

    JButton moveButton;
    JButton buildButton;
    JButton endButton;
    JButton quitButton;


    public ChoicePanel(){

        setBackground(Color.BLACK);
        setLayout(new GridLayout(4,1));
        setButtons();
        setVisible(true);
    }

    private void setButtons(){

        setToolTipTexts();
        setButtonIcons();
        setBorders();
        addListeners();

        add(moveButton);
        add(buildButton);
        add(endButton);
        add(quitButton);
    }

    private void setToolTipTexts(){
        moveButton.setToolTipText("MOVE");
        buildButton.setToolTipText("BUILD");
        endButton.setToolTipText("END YOUR TURN");
        quitButton.setToolTipText("QUIT GAME (only if eliminated)");
    }

    //TODO
    private void setButtonIcons(){
        moveButton.setIcon(Images.getIcon(Images.MOVE_BUTTON_ICON));
        //moveButton.setPressedIcon(Images.getIcon(Images.));
        buildButton.setIcon(Images.getIcon(Images.BUILD_BUTTON_ICON));
        //buildButton.setPressedIcon(Images.getIcon(Images.));
        endButton.setIcon(Images.getIcon(Images.END_TURN_BUTTON_ICON));
        endButton.setPressedIcon(Images.getIcon(Images.PRESSED_END_TURN_BUTTON_ICON));
        quitButton.setIcon(Images.getIcon(Images.QUIT_BUTTON_ICON));
        quitButton.setPressedIcon(Images.getIcon(Images.PRESSED_QUIT_BUTTON_ICON));
    }

    //TODO
    private void setBorders(){

    }

    private void addListeners(){

        moveButton.addMouseListener(new ChoiceListener());
        buildButton.addMouseListener(new ChoiceListener());
        endButton.addMouseListener(new ChoiceListener());
        quitButton.addMouseListener(new ChoiceListener());
    }

    //TODO
    private class ChoiceListener implements MouseListener{


        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            //do nothing
        }

        @Override
        public void mouseExited(MouseEvent e) {
            //do nothing
        }
    }


}
