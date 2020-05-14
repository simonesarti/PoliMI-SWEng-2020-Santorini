package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.messages.GameToPlayerMessages.Notify.NewBoardStateMessage;

public class Matrix {

    private Cell[][] board = new Cell[6][6];

    public Matrix(){
        for (int i=0; i<6; i++){
            for (int j=0; j<6; j++){
                board[i][j]= new Cell();
            }
        }
    }

    public Matrix convertToMatrix(NewBoardStateMessage message){

        Matrix matrix = new Matrix();
        Cell cell = new Cell();

        for (int i=1; i<6; i++){
            for (int j=1; j<6; j++){
                if (message.getBoardState().getTowerState(i,j).getTowerHeight()==0 && message.getBoardState().getTowerState(i,j).getWorkerColour().equals(null)){



                }
            }
        }

        return matrix;

    }
}
