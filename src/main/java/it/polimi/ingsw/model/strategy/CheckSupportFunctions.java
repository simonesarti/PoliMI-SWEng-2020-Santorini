package it.polimi.ingsw.model.strategy;

import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.TurnInfo;
import it.polimi.ingsw.model.Worker;

public class CheckSupportFunctions {



    public boolean alreadyMoved(TurnInfo turnInfo){

        return turnInfo.getHasAlreadyMoved();
    }

    public boolean notAlreadyMoved(TurnInfo turnInfo){

        return !alreadyMoved(turnInfo);
    }

    public boolean alreadyBuilt(TurnInfo turnInfo){

        return turnInfo.getHasAlreadyBuilt();
    }

    public boolean notInGameBoard(int x, int y){

        return (x<0 || x>4 || y<0 || y>4);
    }

    public boolean invalidWorkerNumber(int chosenWorker){

        return (chosenWorker!=0 && chosenWorker!=1);
    }

    public boolean notOwnPosition(Worker worker,int x, int y){

        return (worker.getCurrentPosition().getX()==x && worker.getCurrentPosition().getY()==y);
    }

    public boolean notSameCoordinates(int x1, int y1, int x2, int y2){
        return (x1==x2 && y1==y2);
    }

    public boolean notInSurroundings(Worker worker,int x, int y){

        return (!worker.getCurrentPosition().adjacent(x,y));
    }

    public boolean completeTower(GameBoard gameboard, int x, int y){

        return (gameboard.getTowerCell(x,y).isTowerCompleted());
    }

    public boolean occupiedTower(GameBoard gameboard, int x, int y){

        return gameboard.getTowerCell(x,y).hasWorkerOnTop();
    }

    public boolean highJump(int z, Worker worker){

        return (z > (worker.getCurrentPosition().getZ() +1));
    }
}
