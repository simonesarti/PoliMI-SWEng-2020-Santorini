package it.polimi.ingsw.model.strategy.movestrategy;

import it.polimi.ingsw.messages.GameMessage;
import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TurnInfo;
import it.polimi.ingsw.model.Worker;

public class AthenaMove implements MoveStrategy {

    /**
     * Just like basicmove's checkmove, but it skips the Athena's power check
     *
     * @param turnInfo object containing information about current turn
     * @param gameboard object representing the gameboard
     * @param player player performing the move
     * @param chosenWorker chosen worker (0 or 1)
     * @param movingTo array containing destination towercell x,y
     * @return one of error messages or moveOk message
     */
    @Override
    public String checkMove(TurnInfo turnInfo, GameBoard gameboard, Player player, int chosenWorker, int[] movingTo) {

        int x = movingTo[0];
        int y = movingTo[1];

        //alreadyMoved must be false
        if(turnInfo.getHasAlreadyMoved()){
            return GameMessage.alreadyMoved;
        }

        //x and y must be inside the board
        if (x < 0 || x > 4 || y < 0 || y > 4) {
            return GameMessage.notInGameBoard;
        }
        int z = gameboard.getTowerCell(x, y).getTowerHeight();

        //chosenWorker must be a valid number
        if(chosenWorker!=0 && chosenWorker!=1){
            return GameMessage.invalidWorkerNumber;
        }
        Worker worker = player.getWorker(chosenWorker);

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

        return GameMessage.moveOK;

    }

    /**
     * Just like BasicMove but it activates AthenaPower if it moved up
     *
     * @param turnInfo object containing information about current turn
     * @param gameboard object representing the gameboard
     * @param player player performing the move
     * @param chosenWorker chosen worker (0 or 1)
     * @param movingTo array containing destination towercell x,y
     * @return buildRequest message
     */
    @Override
    public String move(TurnInfo turnInfo, GameBoard gameboard, Player player, int chosenWorker, int[] movingTo) {

        Worker worker = player.getWorker(chosenWorker);
        int x = movingTo[0];
        int y = movingTo[1];
        int z = gameboard.getTowerCell(x, y).getTowerHeight();


        //activating/deactivating Athena's power
        if(worker.getCurrentPosition().getZ()<z){
            turnInfo.activateAthenaPower();
        }else{
            turnInfo.deactivateAthenaPower();
        }

        //getting selected worker to the new towerCell
        gameboard.getTowerCell(worker.getCurrentPosition().getX(), worker.getCurrentPosition().getY()).getFirstNotPieceLevel().workerMoved();
        gameboard.getTowerCell(x, y).getFirstNotPieceLevel().setWorker(worker);

        //modifying worker's associated position
        worker.movedToPosition(x,y,z);

        turnInfo.setHasMoved();
        turnInfo.setChosenWorker(chosenWorker);
        turnInfo.addMove();

        return GameMessage.buildRequest;

    }

}
