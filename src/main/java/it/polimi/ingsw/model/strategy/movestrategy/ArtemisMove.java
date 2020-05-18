package it.polimi.ingsw.model.strategy.movestrategy;

import it.polimi.ingsw.messages.GameToPlayerMessages.Others.GameMessage;
import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TurnInfo;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.strategy.CheckSupportFunctions;

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
        CheckSupportFunctions support=new CheckSupportFunctions();
        int x = movingTo[0];
        int y = movingTo[1];

        //number of move must be < 2
        if(turnInfo.getNumberOfMoves()>1){
            return GameMessage.alreadyMovedTwice;
        }

        //x and y must be inside the board
        if (support.notInGameBoard(x,y)) {
            return GameMessage.notInGameBoard;
        }
        int z = gameboard.getTowerCell(x, y).getTowerHeight();

        //chosenWorker must be a valid number
        if(support.invalidWorkerNumber(chosenWorker)){
            return GameMessage.invalidWorkerNumber;
        }
        Worker worker = player.getWorker(chosenWorker);

        //if artemis has already moved one time, the destination must not be "previous position" and the worker used must be the same
        if(turnInfo.getNumberOfMoves() == 1){
            if(chosenWorker!=turnInfo.getChosenWorker()){
                return GameMessage.NotSameWorker;
            }
            if(worker.getPreviousPosition().getX() == x && worker.getPreviousPosition().getY() == y){
                return GameMessage.ArtemisFirstPosition;
            }
        }

        //workerPosition must not be the destination position
        if (support.notOwnPosition(worker,x,y)){
            return GameMessage.notOwnPosition;
        }
        //workerPosition must be adjacent to destination position
        if (support.notInSurroundings(worker,x,y)){
            return GameMessage.notInSurroundings;
        }

        //towerCell must not be completed by a dome
        if (support.completeTower(gameboard,x,y)){
            return GameMessage.noMoveToCompleteTower;
        }

        //towercell height must be <= (worker height +1)
        if(support.highJump(z,worker)) {
            return GameMessage.noHighJump;
        }

        //towercell must be empty
        if(support.occupiedTower(gameboard,x,y)){
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
