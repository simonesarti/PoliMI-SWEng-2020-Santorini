package it.polimi.ingsw.GUI;

import it.polimi.ingsw.messages.GameToPlayerMessages.Notify.NewBoardStateMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GamePanel extends JPanel{

    private final Image gameBoard;

    private final JButton[][] towerButtons =new JButton[5][5];

    public GamePanel() {
        gameBoard=Images.getImage(Images.GAMEBOARD);
        setLayout(new GridLayout(5,5,30,30));
        setTowerButtons();
        setVisible(true);

    }

    public void setTowerButtons() {

        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 5; i++) {
                towerButtons[i][j] = new JButton();
                setButtonVisibility(towerButtons[i][j]);
                add(towerButtons[i][j]);
                towerButtons[i][j].addMouseListener(new towerSelectionListener());
            }
        }
    }

    private void setButtonVisibility(JButton button){
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
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

