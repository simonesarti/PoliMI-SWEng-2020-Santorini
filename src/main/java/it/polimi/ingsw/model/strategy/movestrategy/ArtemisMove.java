package it.polimi.ingsw.model.strategy.movestrategy;

import it.polimi.ingsw.messages.GameMessage;
import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TurnInfo;
import it.polimi.ingsw.model.Worker;

public class ArtemisMove implements MoveStrategy {

    /**
     * Just like basicmove's checkmove, but it adds checks to manage multiple moves (must be the same worker)
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

        //number of move must be < 2
        if(turnInfo.getNumberOfMoves()>1){
            return GameMessage.alreadyMoved;
        }

        //x and y must be inside the board
        if (x < 0 || x > 4 || y < 0 || y > 4) {
            return GameMessage.notInGameboard;
        }
        int z = gameboard.getTowerCell(x, y).getTowerHeight();

        //chosenWorker must be a valid number
        if(chosenWorker!=0 && chosenWorker!=1){
            return GameMessage.invalidWorkerNumber;
        }
        Worker worker = player.getWorker(chosenWorker);

        //if artemis has already moved one time, the destination must not be "previous position" and the worker used must be the same
        if(turnInfo.getNumberOfMoves() == 1){
            if(chosenWorker!=turnInfo.getChosenWorker()){
                return GameMessage.NotSameWorker;
            }
            if(player.getWorker(chosenWorker).getPreviousPosition().getX() == x && player.getWorker(chosenWorker).getPreviousPosition().getY() == y){
                return GameMessage.ArtemisFirstPosition;
            }
        }

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

        //if Athena's power is active, worker can not move up
        if(turnInfo.getAthenaPowerActive()){
            //if worker moves up return error, else do nothing
            if(worker.getCurrentPosition().getZ() < z){
                return GameMessage.athenaNoMoveUp;
            }
        }

        return GameMessage.moveOK;
    }

    /**
     * Just like BasicMove's move() but it also triggers turninfo.addMove()
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

        //getting selected worker to the new towerCell
        gameboard.getTowerCell(worker.getCurrentPosition().getX(), worker.getCurrentPosition().getY()).getFirstNotPieceLevel().workerMoved();
        gameboard.getTowerCell(x, y).getFirstNotPieceLevel().setWorker(worker);

        //modifying worker's associated position
        worker.movedToPosition(x,y,z);

        //TODO notify()-> spedire messaggio con copia delle informazioni utili dello stato della board

        if(!turnInfo.getHasAlreadyMoved()){
            turnInfo.setHasMoved();
            turnInfo.setChosenWorker(chosenWorker);
            turnInfo.addMove();
            return GameMessage.moveAgainOrBuild;
        }else{
            turnInfo.addMove();
            return GameMessage.buildRequest;
        }
    }

}
