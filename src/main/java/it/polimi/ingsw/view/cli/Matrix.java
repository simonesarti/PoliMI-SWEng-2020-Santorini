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

    public Cell getCell(int x, int y){ return board[x][y];}

    public Matrix convertToMatrix(NewBoardStateMessage message){

        Matrix matrix = new Matrix();
        Cell cell = new Cell();

        //casella (0,0) tutta vuota
        for (int i=0; i<5; i++){
            matrix.getCell(0,0).getStripe(i).equals(cell.emptyStripe());
        }

        //coordinate X
        for(int i=1; i<6;i++){
            matrix.getCell(i,0).getStripe(0).equals(cell.emptyStripe());
            matrix.getCell(i,0).getStripe(1).equals(cell.emptyStripe());
            matrix.getCell(i,0).getStripe(2).equals(cell.coordinateXStripe(i));
            matrix.getCell(i,0).getStripe(3).equals(cell.emptyStripe());
            matrix.getCell(i,0).getStripe(4).equals(cell.emptyStripe());
        }

        //coordinate Y
        for(int i=1; i<6;i++){
            matrix.getCell(0,i).getStripe(0).equals(cell.emptyStripe());
            matrix.getCell(0,i).getStripe(1).equals(cell.emptyStripe());
            matrix.getCell(0,i).getStripe(2).equals(cell.coordinateYStripe(i));
            matrix.getCell(0,i).getStripe(3).equals(cell.emptyStripe());
            matrix.getCell(0,i).getStripe(4).equals(cell.emptyStripe());
        }



        for (int i=1; i<6; i++){
            for (int j=1; j<6; j++){
                //livello zero vuota
                if (message.getBoardState().getTowerState(i,j).getTowerHeight()==0 && message.getBoardState().getTowerState(i,j).getWorkerColour().equals(null)){
                    for (int k=0; k<5; k++){
                        matrix.getCell(i,j).getStripe(k).equals(cell.uniformStripe(AnsiCode.BACKGROUND_GREEN));
                    }
                }
                //livello zero con wprker
                else if (message.getBoardState().getTowerState(i,j).getTowerHeight()==0 && !(message.getBoardState().getTowerState(i,j).getWorkerColour().equals(null))){
                    matrix.getCell(i,j).getStripe(0).equals(cell.uniformStripe(AnsiCode.BACKGROUND_GREEN));
                    matrix.getCell(i,j).getStripe(1).equals(cell.uniformStripe(AnsiCode.BACKGROUND_GREEN));
                    matrix.getCell(i,j).getStripe(2).equals(cell.workerStripe(AnsiCode.BACKGROUND_GREEN, message.getBoardState().getTowerState(i,j).getWorkerNumber()));
                    matrix.getCell(i,j).getStripe(3).equals(cell.uniformStripe(AnsiCode.BACKGROUND_GREEN));
                    matrix.getCell(i,j).getStripe(4).equals(cell.uniformStripe(AnsiCode.BACKGROUND_GREEN));

                }
                //livello 1/2/3 vuota
                else if (message.getBoardState().getTowerState(i,j).getTowerHeight()>0 && message.getBoardState().getTowerState(i,j).getWorkerColour().equals(null)){
                    matrix.getCell(i,j).getStripe(0).equals(cell.uniformStripe(AnsiCode.BACKGROUND_WHITE));
                    matrix.getCell(i,j).getStripe(1).equals(cell.uniformStripe(AnsiCode.BACKGROUND_WHITE));
                    matrix.getCell(i,j).getStripe(2).equals(cell.levelStripe(message.getBoardState().getTowerState(i,j).getTowerHeight()));
                    matrix.getCell(i,j).getStripe(3).equals(cell.uniformStripe(AnsiCode.BACKGROUND_WHITE));
                    matrix.getCell(i,j).getStripe(4).equals(cell.uniformStripe(AnsiCode.BACKGROUND_WHITE));

                }
                //livello 1/2/3 con worker
                else if (message.getBoardState().getTowerState(i,j).getTowerHeight()>0 && !(message.getBoardState().getTowerState(i,j).getWorkerColour().equals(null))){
                    matrix.getCell(i,j).getStripe(0).equals(cell.uniformStripe(AnsiCode.BACKGROUND_WHITE));
                    matrix.getCell(i,j).getStripe(1).equals(cell.levelStripe(message.getBoardState().getTowerState(i,j).getTowerHeight()));
                    matrix.getCell(i,j).getStripe(2).equals(cell.uniformStripe(AnsiCode.BACKGROUND_WHITE));
                    matrix.getCell(i,j).getStripe(3).equals(cell.workerStripe(AnsiCode.BACKGROUND_WHITE, message.getBoardState().getTowerState(i,j).getWorkerNumber()));
                    matrix.getCell(i,j).getStripe(4).equals(cell.uniformStripe(AnsiCode.BACKGROUND_WHITE));
                }

                //con cupola a livello 0
                else if (message.getBoardState().getTowerState(i,j).isCompleted()==true && message.getBoardState().getTowerState(i,j).getTowerHeight()==0){
                    matrix.getCell(i,j).getStripe(0).equals(cell.uniformStripe(AnsiCode.BACKGROUND_GREEN));
                    matrix.getCell(i,j).getStripe(1).equals(cell.domeStripe(AnsiCode.BACKGROUND_GREEN));
                    matrix.getCell(i,j).getStripe(2).equals(cell.domeStripe(AnsiCode.BACKGROUND_GREEN));
                    matrix.getCell(i,j).getStripe(3).equals(cell.domeStripe(AnsiCode.BACKGROUND_GREEN));
                    matrix.getCell(i,j).getStripe(4).equals(cell.uniformStripe(AnsiCode.BACKGROUND_GREEN));
                }

                //con cupola a livello 1/2/3
                else if (message.getBoardState().getTowerState(i,j).isCompleted()==true && message.getBoardState().getTowerState(i,j).getTowerHeight()>0){
                    matrix.getCell(i,j).getStripe(0).equals(cell.uniformStripe(AnsiCode.BACKGROUND_WHITE));
                matrix.getCell(i,j).getStripe(1).equals(cell.domeStripe(AnsiCode.BACKGROUND_WHITE));
                    matrix.getCell(i,j).getStripe(2).equals(cell.domeStripe(AnsiCode.BACKGROUND_WHITE));
                    matrix.getCell(i,j).getStripe(3).equals(cell.domeStripe(AnsiCode.BACKGROUND_WHITE));
                    matrix.getCell(i,j).getStripe(4).equals(cell.uniformStripe(AnsiCode.BACKGROUND_WHITE));
                }
            }
        }

        return matrix;



    }
}
