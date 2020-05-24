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
        
        matrix.getCell(0,0).assignVoid();

        //coordinate X
        for(int i=1; i<6;i++){
            matrix.getCell(i,0).assignCoordinate("X",i-1);
        }

        //coordinate Y
        for(int i=1; i<6;i++){
            matrix.getCell(0,i).assignCoordinate("Y",i-1);
        }


        for (int j=1; j<6; j++){
            for (int i=1; i<6; i++){

                //LEVEL 0
                if (message.getBoardState().getTowerState(i-1,j-1).getTowerHeight()==0){

                    //no worker
                    if(message.getBoardState().getTowerState(i-1, j-1).getWorkerColour() == null) {
                        matrix.getCell(i, j).assignGreen();
                    }
                    //with worker
                    else{
                        matrix.getCell(i,j).assignOnlyWorker(message.getBoardState().getTowerState(i-1,j-1).getWorkerNumber(), message.getBoardState().getTowerState(i-1,j-1).getWorkerColour());
                    }

                }

                //DOME
                else if(message.getBoardState().getTowerState(i-1, j-1).isCompleted()){

                    //level 1
                    if(message.getBoardState().getTowerState(i-1,j-1).getTowerHeight()==1){
                        matrix.getCell(i,j).assignDome(AnsiCode.BACKGROUND_GREEN);
                    }
                    //level 2/3
                    else{
                        matrix.getCell(i,j).assignDome(AnsiCode.BACKGROUND_WHITE);

                    }
                }

                //TOWER 1/2/3
                else if(message.getBoardState().getTowerState(i-1,j-1).getTowerHeight()>0){
                    //no worker
                    if(message.getBoardState().getTowerState(i-1, j-1).getWorkerColour() == null){
                        matrix.getCell(i,j).assignOnlyLevel(message.getBoardState().getTowerState(i-1,j-1).getTowerHeight());
                    }
                    //with worker
                    else {
                        matrix.getCell(i,j).assignLevelAndWorker(message.getBoardState().getTowerState(i-1,j-1).getTowerHeight(),message.getBoardState().getTowerState(i-1,j-1).getWorkerNumber(), message.getBoardState().getTowerState(i-1,j-1).getWorkerColour());
                    }
                }

            }
        }

        return matrix;

    }

    public void printMatrix(Matrix matrix){
        System.out.println(AnsiCode.CLEAR_SCREEN);

        for (int y=0; y<6; y++){
            System.out.print("\n");
            for (int s=0; s<5; s++){
                System.out.print("\n");
                for (int x=0; x<6; x++){
                    System.out.print(matrix.getCell(x,y).getStripe(s)+" ");
                }
            }
        }
        System.out.print("\n");
    }
}
