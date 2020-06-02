package it.polimi.ingsw.GUI;

import it.polimi.ingsw.messages.GameToPlayerMessages.Notify.NewBoardStateMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GamePanel extends JPanel{

    private final Image gameBoard;

    private final ImageButton[][] towerButtons =new ImageButton[5][5];

    public GamePanel() {
        gameBoard=Images.getImage(Images.GAMEBOARD);
        setLayout(new GridBagLayout());
        setTowerButtons();
        setVisible(true);

    }

    public void setTowerButtons() {

        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 5; i++) {
                towerButtons[i][j] = new ImageButton();
                setButtonVisibility(towerButtons[i][j]);
                add(towerButtons[i][j],setButtonConstraints(i,j));
                towerButtons[i][j].addMouseListener(new towerSelectionListener());
            }
        }
    }

    private void setButtonVisibility(JButton button){
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(true);
    }

    private GridBagConstraints setButtonConstraints(int gridx, int gridy) {

        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        //x position of component
        gridBagConstraints.gridx = gridx;
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
        gridBagConstraints.insets = new Insets(5,5,5,5);

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
    //update icona pulsanti con nuova

    private class towerSelectionListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        //TODO
        @Override
        public void mousePressed(MouseEvent e) {
            //do nothing
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            //do nothing
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

    //TODO
    public void updateBoard(NewBoardStateMessage message){

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(gameBoard, 0, 0, this.getWidth(), this.getHeight(), this);

    }

}

