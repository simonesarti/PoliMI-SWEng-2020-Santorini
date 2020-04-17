package it.polimi.ingsw.model.strategy.movestrategy;

import it.polimi.ingsw.messages.GameMessage;
import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TurnInfo;
import it.polimi.ingsw.model.Worker;

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

        Worker worker = player.getWorker(chosenWorker);
        int x = movingTo[0];
        int y = movingTo[1];


        //x and y must be inside the board
        if (x < 0 || x > 4 || y < 0 || y > 4) {
            return GameMessage.notInGameboard;
        }

        int z = gameboard.getTowerCell(x, y).getTowerHeight();

        //alreadyMoved must be false
        if(turnInfo.getHasAlreadyMoved()){
            return GameMessage.alreadyMoved;
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

        //towercell must not be occupied from your other worker
        if(gameboard.getTowerCell(x,y).hasWorkerOnTop()){
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
        //TODO notify()-> spedire messaggio con copia delle informazioni utili dello stato della board

        return GameMessage.buildRequest;

    }

}
