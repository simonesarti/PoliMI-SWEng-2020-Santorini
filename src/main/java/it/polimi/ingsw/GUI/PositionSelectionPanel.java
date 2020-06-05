package it.polimi.ingsw.GUI;

import it.polimi.ingsw.model.BoardState;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PositionSelectionPanel extends JPanel {

    private final Image gameBoard;
    private final JButton[][] buttons=new JButton[5][5];
    private final ArrayList<int[]> coordinates=new ArrayList<>();
    private final JDialog dialog;

    public PositionSelectionPanel(StartingPositionRequestDialog dialog, BoardState boardState) {
        this.dialog=dialog;
        setPreferredSize(new Dimension(500,500));
        gameBoard=Images.getImage(Images.GAMEBOARD);
        setLayout(new GridLayout(5,5));
        setButtons(boardState);
    }

    private void setButtons(BoardState boardState){

        for(int j=0;j<5;j++){
            for(int i=0;i<5;i++){
                buttons[i][j]=new JButton();
                setButtonVisibility(i,j,boardState,buttons[i][j]);
                this.add(buttons[i][j]);
                buttons[i][j].addActionListener(new PositionListener());
            }
        }
    }

    private void setButtonVisibility(int x,int y, BoardState boardState, JButton button){

        if(boardState.getTowerState(x,y).getWorkerColour()!=null){
            setOpaque(true);
            button.setContentAreaFilled(true);
            button.setBackground(Color.RED);
        }else{
            setOpaque(false);
            button.setContentAreaFilled(false);
        }
    }

    private int[] getButtonCoordinates(JButton button){

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

    private class PositionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton pressed=(JButton)e.getSource();
            int[] selected=getButtonCoordinates(pressed);
            coordinates.add(selected);
            System.out.println(""+selected[0]+","+selected[1]);
            if(coordinates.size()==2){
                //TODO
                dialog.dispose();
            }
        }
    }
}


