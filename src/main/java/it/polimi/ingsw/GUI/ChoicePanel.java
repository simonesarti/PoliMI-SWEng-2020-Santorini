package it.polimi.ingsw.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ChoicePanel extends JPanel {

    JButton moveButton=new JButton();
    JButton buildButton=new JButton();
    JButton endButton=new JButton();
    JButton quitButton=new JButton();


    public ChoicePanel(){

        setBackground(Color.BLACK);
        setLayout(new GridBagLayout());
        setButtons();
        setVisible(true);
    }

    private void setButtons(){

        setToolTipTexts();
        setButtonIcons();
        addListeners();

        add(moveButton,setButtonConstraints(0));
        add(buildButton,setButtonConstraints(1));
        add(endButton,setButtonConstraints(2));
        add(quitButton,setButtonConstraints(3));
    }

    private void setToolTipTexts(){
        moveButton.setToolTipText("MOVE");
        buildButton.setToolTipText("BUILD");
        endButton.setToolTipText("END YOUR TURN");
        quitButton.setToolTipText("QUIT GAME (only if eliminated)");
    }

    private void setButtonIcons(){
        moveButton.setIcon(Images.getIcon(Images.MOVE_BUTTON_ICON));
        buildButton.setIcon(Images.getIcon(Images.BUILD_BUTTON_ICON));
        endButton.setIcon(Images.getIcon(Images.END_TURN_BUTTON_ICON));
        endButton.setPressedIcon(Images.getIcon(Images.PRESSED_END_TURN_BUTTON_ICON));
        quitButton.setIcon(Images.getIcon(Images.QUIT_BUTTON_ICON));
        quitButton.setPressedIcon(Images.getIcon(Images.PRESSED_QUIT_BUTTON_ICON));
    }


    private void addListeners(){

        moveButton.addMouseListener(new ChoiceListener());
        buildButton.addMouseListener(new ChoiceListener());
        endButton.addMouseListener(new ChoiceListener());
        quitButton.addMouseListener(new ChoiceListener());
    }

    private GridBagConstraints setButtonConstraints(int gridy) {

        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        //x position of component
        gridBagConstraints.gridx = 0;
        //y position of the component
        gridBagConstraints.gridy = gridy;
        //rows used by the component
        gridBagConstraints.gridheight = 1;
        // Number of columns the component takes up
        gridBagConstraints.gridwidth = 1;
        // Gives the layout manager a hint on how to adjust component width (0 equals fixed)
        gridBagConstraints.weightx = 1;

        // Gives the layout manager a hint on how to adjust component height (0 equals fixed)
        gridBagConstraints.weighty = 1;

        gridBagConstraints.ipadx = 0;
        gridBagConstraints.ipady = 0;

        // Defines padding top, left, bottom, right
        gridBagConstraints.insets = new Insets(10,30,10,30);

        // Defines where to place components if they don't
        // fill the space: CENTER, NORTH, SOUTH, EAST, WEST
        // NORTHEAST, etc.
        gridBagConstraints.anchor = GridBagConstraints.CENTER;

        // How should the component be stretched to fill the
        // space: NONE, HORIZONTAL, VERTICAL, BOTH
        gridBagConstraints.fill = GridBagConstraints.BOTH;

        return gridBagConstraints;
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
