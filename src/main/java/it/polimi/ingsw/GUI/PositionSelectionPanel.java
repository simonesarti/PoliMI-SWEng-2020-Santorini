package it.polimi.ingsw.GUI;

import it.polimi.ingsw.GUI.messages.ActionMessage;
import it.polimi.ingsw.GUI.messages.StartingPlacement;
import it.polimi.ingsw.model.BoardState;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.observe.Observable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * panel used to ask the user for the positions in which he wants to place his workers at
 * the beginning of the game
 */
public class PositionSelectionPanel extends JPanel {

    private final GuiController guiController;
    private final Image gameBoard;
    private final ImageButton[][] buttons=new ImageButton[5][5];
    private final ArrayList<int[]> coordinates=new ArrayList<>();
    private final JDialog dialog;

    public PositionSelectionPanel(StartingPositionRequestDialog dialog,GuiController guiController) {

        this.guiController=guiController;

        this.dialog=dialog;
        setPreferredSize(new Dimension(500,500));
        gameBoard=Images.getImage(Images.GAMEBOARD);
        setLayout(new GridLayout(5,5));
        setButtons(guiController.getCurrentBoardState());
    }

    private void setButtons(BoardState boardState){

        for(int j=0;j<5;j++){
            for(int i=0;i<5;i++){
                buttons[i][j]=new ImageButton();
                setButtonVisibility(i,j,boardState,buttons[i][j]);
                this.add(buttons[i][j]);
                buttons[i][j].addActionListener(new PositionListener());
            }
        }
    }

    /**
     * sets the image shown on the button
     * @param x coordinate
     * @param y coordinate
     * @param boardState is the state of the gameboard
     * @param button is the button whose image has to be updated
     */
    private void setButtonVisibility(int x,int y, BoardState boardState, ImageButton button){

        if(boardState.getTowerState(x,y).getWorkerColour()== Colour.RED){
            button.setButtonImage(Images.getImage(Images.L0R));
        }else if(boardState.getTowerState(x,y).getWorkerColour()== Colour.BLUE){
            button.setButtonImage(Images.getImage(Images.L0B));
        }else if(boardState.getTowerState(x,y).getWorkerColour()== Colour.PURPLE){
            button.setButtonImage(Images.getImage(Images.L0P));
        }else{
            setOpaque(false);
            button.setContentAreaFilled(false);
        }
    }

    /**
     * @param button is the button pressed
     * @return the coordinates of the button pressed
     */
    private int[] getButtonCoordinates(ImageButton button){

        int[] res=new int[2];

        for(int j=0;j<5;j++){
           for(int i=0;i<5;i++){
               if(button.equals(buttons[i][j])){
                   res[0]=i;
                   res[1]=j;
               }
           }
        }
        return res;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(gameBoard, 0, 0, 500, 500, this);
    }

    /**
     * this private class implements ActionListener and its objects are Observed by GuiController.
     * When a position is selected (button pressed), its coordinates are added to a list. When two positions
     * have been selected, the guiController is notified and the panel+dialog gets closed
     */
    private class PositionListener extends Observable<ActionMessage> implements ActionListener{

        public PositionListener() {
            this.addObserver(guiController);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ImageButton pressed=(ImageButton) e.getSource();
            int[] selected=getButtonCoordinates(pressed);
            coordinates.add(selected);
            if(coordinates.size()==2){

                int[] p1=coordinates.get(0);
                int[] p2=coordinates.get(1);

                dialog.dispose();
                notify(new StartingPlacement(p1[0],p1[1],p2[0],p2[1]));
            }
        }
    }
}


