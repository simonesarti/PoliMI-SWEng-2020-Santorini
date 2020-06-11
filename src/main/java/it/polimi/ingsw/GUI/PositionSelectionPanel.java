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


