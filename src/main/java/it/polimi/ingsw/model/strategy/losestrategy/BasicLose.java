package it.polimi.ingsw.model.strategy.losestrategy;

import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TurnInfo;
import it.polimi.ingsw.model.Worker;

public class BasicLose implements LoseStrategy {


    @Override
    public boolean movementLoss(TurnInfo turnInfo, GameBoard gameBoard, Player player) {

        int possibility=14;
        Worker worker;
        int x;
        int y;
        int z;

        for(int w=0;w<2;w++){
            worker=player.getWorker(w);
            x=worker.getCurrentPosition().getX();
            y=worker.getCurrentPosition().getY();
            z=worker.getCurrentPosition().getZ();

            for(int j=x-1;j<x+2;j++){
                for(int i=y-1; i<y+2;i++){

                    if(j!=x && i!=y){

                        if( j<0 || j>4 || i<0 || i>4 ||
                            gameBoard.getTowerCell(j,i).hasWorkerOnTop() ||
                            gameBoard.getTowerCell(j,i).isTowerCompleted() ||
                            gameBoard.getTowerCell(j,i).getTowerHeight()> z+1 ||
                            (turnInfo.getAthenaPowerActive() && gameBoard.getTowerCell(j,i).getTowerHeight()>z)){

                            possibility--;
                        }
                    }
                }
            }
        }

        return possibility == 0;
    }

    @Override
    public boolean buildingLoss(TurnInfo turnInfo, GameBoard gameBoard, Player player) {

        int possibility = 14;
        Worker worker;
        int x;
        int y;

        for(int w=0;w<2;w++){
            worker=player.getWorker(w);
            x=worker.getCurrentPosition().getX();
            y=worker.getCurrentPosition().getY();

            for(int j=x-1;j<x+2;j++){
                for(int i=y-1; i<y+2;i++){

                    if(j!=x && i!=y){

                        if( j<0 || j>4 || i<0 || i>4 ||
                                gameBoard.getTowerCell(j,i).hasWorkerOnTop() ||
                                gameBoard.getTowerCell(j,i).isTowerCompleted()){

                            possibility--;
                        }
                    }
                }
            }
        }
        return possibility==0;
    }
}
