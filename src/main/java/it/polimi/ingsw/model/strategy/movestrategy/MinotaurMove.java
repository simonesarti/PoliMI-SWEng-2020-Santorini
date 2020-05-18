package it.polimi.ingsw.model.strategy.movestrategy;

import it.polimi.ingsw.messages.GameToPlayerMessages.Others.GameMessage;
import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TurnInfo;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.strategy.CheckSupportFunctions;

public class MinotaurMove implements MoveStrategy {

    /**
     * Just like Apollo's checkmove(), but with one more check (CantBeForcedBackwards-check)
     *
     * changes position associated to the workers and sets the workers on the towercell
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
                return GameMessage.noMovedToOccupiedTowerMinotaur;
            }
        }
        //if Athena's power is active, worker can not move up
        if(turnInfo.getAthenaPowerActive()){
            //if worker moves up return error, else do nothing
            if(worker.getCurrentPosition().getZ() < z){
                return GameMessage.athenaNoMoveUp;
            }
        }
        //if towercell is occupied by another player's worker, then if CantBeForcedBackwards is true checkMove returns an error message
        if(gameboard.getTowerCell(x,y).hasWorkerOnTop()){
            if(gameboard.getTowerCell(x,y).getFirstNotPieceLevel().getWorker().getColour() != player.getColour()){
                if(gameboard.cantBeForcedBackwards(worker.getCurrentPosition().getX(),worker.getCurrentPosition().getY(),x,y)){
                    return GameMessage.CannotForceWorker;
                }
            }
        }

        return GameMessage.moveOK;
    }

    /**
     * Just like BasicMove's move(), but it forces the other worker if the destination towercell has worker on top
     * changes position associated to the workers and sets the workers on the towercell
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


            //forces enemy worker backwards
            gameboard.getTowerCell(((x-worker.getCurrentPosition().getX())+x),((y-worker.getCurrentPosition().getY())+y)).getFirstNotPieceLevel().setWorker(gameboard.getTowerCell(x,y).getFirstNotPieceLevel().getWorker());
            //modifying enemy's worker's associated position
            gameboard.getTowerCell(x,y).getFirstNotPieceLevel().getWorker().movedToPosition(((x-worker.getCurrentPosition().getX())+x),((y-worker.getCurrentPosition().getY())+y),gameboard.getTowerCell(((x-worker.getCurrentPosition().getX())+x),((y-worker.getCurrentPosition().getY())+y)).getTowerHeight());

            //starting towercell now has no worker
            gameboard.getTowerCell(worker.getCurrentPosition().getX(), worker.getCurrentPosition().getY()).getFirstNotPieceLevel().workerMoved();
            //setting player in the destination towercell
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
