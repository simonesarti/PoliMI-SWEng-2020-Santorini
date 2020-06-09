package it.polimi.ingsw.GUI;

import it.polimi.ingsw.GUI.messages.ActionMessage;
import it.polimi.ingsw.GUI.messages.BoardSelection;
import it.polimi.ingsw.model.BoardState;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.TowerState;
import it.polimi.ingsw.observe.Observable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow{

    private class GamePanel extends JPanel{

        private final Image gameBoard;

        private GamePanel() {
            gameBoard=Images.getImage(Images.GAMEBOARD);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.drawImage(gameBoard, 0, 0, this.getWidth(), this.getHeight(), this);

        }

    }

    private final GuiController guiController;
    private final JPanel gamePanel;
    private final ImageButton[][] towerButtons =new ImageButton[5][5];

    public GameWindow(GuiController guiController) {

        this.guiController=guiController;
        gamePanel=new GamePanel();
        gamePanel.setLayout(new GridBagLayout());
        setTowerButtons(guiController.getCurrentBoardState());
        gamePanel.setVisible(true);

    }

    public void setTowerButtons(BoardState boardState) {

        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 5; i++) {
                towerButtons[i][j] = new ImageButton();
                updateButton(towerButtons[i][j],boardState.getTowerState(i,j));
                gamePanel.add(towerButtons[i][j],setButtonConstraints(i,j));
                towerButtons[i][j].addActionListener(new CellSelectedListener());
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

    private int[] getButtonCoordinates(JButton button){

        int[] res=new int[2];

        for(int j=0;j<5;j++){
            for(int i=0;i<5;i++){
                if(button.equals(towerButtons[i][j])){
                    res[0]=i;
                    res[1]=j;
                }
            }
        }
        return res;
    }

    public void updateBoard(BoardState boardState){

        for(int y=0;y<5;y++){
            for(int x=0;x<5;x++){
                updateButton(towerButtons[x][y],boardState.getTowerState(x,y));
            }
        }
    }

    private void updateButton(ImageButton imageButton, TowerState towerState){

        int height=towerState.getTowerHeight();

        if(towerState.getWorkerColour()==null){
            if(towerState.isCompleted()){
                switch (height) {
                    case 1 -> imageButton.setButtonImage(Images.getImage(Images.L0D));
                    case 2 -> imageButton.setButtonImage(Images.getImage(Images.L1D));
                    case 3 -> imageButton.setButtonImage(Images.getImage(Images.L12D));
                    case 4 -> imageButton.setButtonImage(Images.getImage(Images.L123D));
                }
            }else{
                switch (height) {
                    case 0 -> setButtonVisibility(imageButton);
                    case 1 -> imageButton.setButtonImage(Images.getImage(Images.L1));
                    case 2 -> imageButton.setButtonImage(Images.getImage(Images.L12));
                    case 3 -> imageButton.setButtonImage(Images.getImage(Images.L123));
                }
            }

        }else{

            if (towerState.getWorkerColour() == Colour.RED) {
                switch (height) {
                    case 0 -> imageButton.setButtonImage(Images.getImage(Images.L0R));
                    case 1 -> imageButton.setButtonImage(Images.getImage(Images.L1R));
                    case 2 -> imageButton.setButtonImage(Images.getImage(Images.L12R));
                    case 3 -> imageButton.setButtonImage(Images.getImage(Images.L123R));
                }
            } else if (towerState.getWorkerColour() == Colour.BLUE) {
                switch (height) {
                    case 0 -> imageButton.setButtonImage(Images.getImage(Images.L0B));
                    case 1 -> imageButton.setButtonImage(Images.getImage(Images.L1B));
                    case 2 -> imageButton.setButtonImage(Images.getImage(Images.L12B));
                    case 3 -> imageButton.setButtonImage(Images.getImage(Images.L123B));
                }
            } else {
                switch (height) {
                    case 0 ->imageButton.setButtonImage(Images.getImage(Images.L0P));
                    case 1 -> imageButton.setButtonImage(Images.getImage(Images.L1P));
                    case 2 -> imageButton.setButtonImage(Images.getImage(Images.L12P));
                    case 3 -> imageButton.setButtonImage(Images.getImage(Images.L123P));
                }
            }
        }
    }

    public JPanel getGamePanel() {
        return gamePanel;
    }

    //TODO
    private class CellSelectedListener extends Observable<ActionMessage> implements ActionListener {

        public CellSelectedListener() {
            this.addObserver(guiController);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int[] coordinates=getButtonCoordinates((ImageButton) e.getSource());
            notify(new BoardSelection(coordinates[0],coordinates[1]));
        }
    }


}

