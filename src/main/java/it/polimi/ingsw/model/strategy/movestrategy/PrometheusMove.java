package it.polimi.ingsw.model.strategy.movestrategy;

import it.polimi.ingsw.messages.GameMessage;
import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TurnInfo;
import it.polimi.ingsw.model.Worker;

public class PrometheusMove implements MoveStrategy {

    @Override
    public String checkMove(TurnInfo turnInfo, GameBoard gameboard, Player player, int chosenWorker, int[] movingTo) {

        Worker worker = player.getWorker(chosenWorker);
        int x = movingTo[0];
        int y = movingTo[1];

        //alreadyMoved must be false
        if(turnInfo.getHasAlreadyMoved()){
            return GameMessage.alreadyMoved;
        }

        //x and y must be inside the board
        if (x < 0 || x > 4 || y < 0 || y > 4) {
            return GameMessage.notInGameboard;
        }

        //if player had already built, worker must be the same
        if(turnInfo.getHasAlreadyBuilt() && chosenWorker!=turnInfo.getChosenWorker()){
            return GameMessage.NotSameWorker;
        }

        int z = gameboard.getTowerCell(x, y).getTowerHeight();

        //workerPosition must not be the destination position
        if (worker.getCurrentPosition().getX()==x && worker.getCurrentPosition().getY()==y){
            return GameMessage.notOwnPosition;
        }
        //workerPosition must be adjacent to destination position
        if (!worker.getCurrentPosition().adjacent(x,y)){
            return GameMessage.notInSurroundings;
        }

        //towerCell must not be completed by a dome
        if (gameboard.getTowerCell(x,y).isTowerCompleted()){
            return GameMessage.noMoveToCompleteTower;
        }

        //towercell height must be <= (worker height +1)
        if(z > (worker.getCurrentPosition().getZ() +1)) {
            return GameMessage.noHighJump;
        }

        //towercell must be empty
        if(gameboard.getTowerCell(x,y).hasWorkerOnTop()){
            return GameMessage.noMovedToOccupiedTower;
        }

        //you can not move up if you decided to use Prometheus power
        if(turnInfo.getHasAlreadyBuilt()){
            if(worker.getCurrentPosition().getZ()<z){
                return GameMessage.prometheusNoMoveUp;
            }
        }

        //if Athena's power is active, worker can not move up
        if(turnInfo.getAthenaPowerActive()){
            //if worker moves up return error, else do nothing
            if(worker.getCurrentPosition().getZ() < z){
                return GameMessage.athenaNoMoveUp;
            }
        }

        return GameMessage.moveOK;
    }

    @Override
    public String move(TurnInfo turnInfo, GameBoard gameboard, Player player, int chosenWorker, int[] movingTo) {

        Worker worker = player.getWorker(chosenWorker);
        int x = movingTo[0];
        int y = movingTo[1];
        int z = gameboard.getTowerCell(x, y).getTowerHeight();

        //getting selected worker to the new towerCell
        gameboard.getTowerCell(worker.getCurrentPosition().getX(), worker.getCurrentPosition().getY()).getFirstNotPieceLevel().workerMoved();
        gameboard.getTowerCell(x, y).getFirstNotPieceLevel().setWorker(worker);

        //modifying worker's associated position
        worker.movedToPosition(x,y,z);

        //TODO notify()-> spedire messaggio con copia delle informazioni utili dello stato della board

        turnInfo.setHasMoved();
        turnInfo.addMove();

       if(!turnInfo.getHasAlreadyBuilt()) {
           turnInfo.setChosenWorker(chosenWorker);
       }

        return GameMessage.buildRequest;

    }


}
