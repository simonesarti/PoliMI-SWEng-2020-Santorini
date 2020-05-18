package it.polimi.ingsw.model.strategy.movestrategy;

import it.polimi.ingsw.messages.GameToPlayerMessages.Others.GameMessage;
import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TurnInfo;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.strategy.CheckSupportFunctions;

public class ApolloMove implements MoveStrategy {

    /**
     * Just like BasicMove's checkMove method, but the destination cell can be occupied by an enemy worker
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

        //alreadyMoved must be false
        if(turnInfo.getHasAlreadyMoved()){
            return GameMessage.alreadyMoved;
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

        //towercell must not be occupied from your other worker
        if(support.occupiedTower(gameboard,x,y)){
            if(gameboard.getTowerCell(x,y).getFirstNotPieceLevel().getWorker().getColour() == player.getColour()){
                return GameMessage.noMovedToOccupiedTowerApollo;
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

    /**
     * Just like BasicMove's checkMove method, but if the destination cell is occupied by an enemy worker it swaps workers
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


        //in realtà basterebbe verificare che è presente un worker, perché la checkMove è già stata superata
        if(gameboard.getTowerCell(x,y).hasWorkerOnTop() && (gameboard.getTowerCell(x,y).getFirstNotPieceLevel().getWorker().getColour() != player.getColour()) ){


            //mette worker avversario nella cella di partenza
            gameboard.getTowerCell(worker.getCurrentPosition().getX(), worker.getCurrentPosition().getY()).getFirstNotPieceLevel().setWorker(gameboard.getTowerCell(x,y).getFirstNotPieceLevel().getWorker());
            //modifying enemy's worker's associated position
            gameboard.getTowerCell(x,y).getFirstNotPieceLevel().getWorker().movedToPosition(worker.getCurrentPosition().getX(),worker.getCurrentPosition().getY(),worker.getCurrentPosition().getZ());

            gameboard.getTowerCell(x, y).getFirstNotPieceLevel().setWorker(worker);
            //modifying worker's associated position
            worker.movedToPosition(x,y,z);


        }else{

            //getting selected worker to the new towerCell
            gameboard.getTowerCell(worker.getCurrentPosition().getX(), worker.getCurrentPosition().getY()).getFirstNotPieceLevel().workerMoved();
            gameboard.getTowerCell(x, y).getFirstNotPieceLevel().setWorker(worker);

            //modifying worker's associated position
            worker.movedToPosition(x,y,z);

        }


        turnInfo.setHasMoved();
        turnInfo.setChosenWorker(chosenWorker);
        turnInfo.addMove();

        return GameMessage.buildRequest;

    }

}
