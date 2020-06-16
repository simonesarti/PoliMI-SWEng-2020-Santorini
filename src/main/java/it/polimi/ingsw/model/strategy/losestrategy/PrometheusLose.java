package it.polimi.ingsw.model.strategy.losestrategy;

import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TurnInfo;

public class PrometheusLose implements LoseStrategy {

    /**
     * If the player has already moved, checks that the previously chosen worker can still move,
     * otherwise tha player loses. If he has not moved yet, the basic movement lose strategy is used
     * @param turnInfo containing information about current turn
     * @param gameBoard object representing the gameboard
     * @param player player whose losing condition has to be checked
     * @param chosenWorker is the number of the worker to check
     * @return a boolean which says if the player lost or not
     */
    @Override
    public boolean movementLoss(TurnInfo turnInfo, GameBoard gameBoard, Player player, int chosenWorker) {

        //if player is trying to move again this function ends and the movement check will reject it
        //same goes for an invalid selection of chosenWorker
        if(turnInfo.getHasAlreadyMoved() || (chosenWorker!=0 && chosenWorker!=1)){
            return false;
        }

        if(turnInfo.getHasAlreadyBuilt()){

            int possibility = 8;
            int x = player.getWorker(turnInfo.getChosenWorker()).getCurrentPosition().getX();
            int y = player.getWorker(turnInfo.getChosenWorker()).getCurrentPosition().getY();
            int z = player.getWorker(turnInfo.getChosenWorker()).getCurrentPosition().getZ();

            for(int j=y-1;j<=y+1;j++) {
                for (int i = x - 1; i <= x + 1; i++) {

                    if (!(j == y && i == x)) {
                        if (j < 0 || j > 4 || i < 0 || i > 4 ||
                                gameBoard.getTowerCell(i, j).isTowerCompleted() ||
                                gameBoard.getTowerCell(i, j).hasWorkerOnTop() ||
                                gameBoard.getTowerCell(i, j).getTowerHeight() > z) {

                            possibility--;
                        }
                    }
                }
            }

            return possibility==0;

        }else{

            return (new BasicLose()).movementLoss(turnInfo,gameBoard,player,chosenWorker);

        }

    }

    /**
     * If the player has already moved and not built, checks that the previously chosen worker can still build,
     * otherwise tha player loses. If he has not moved yet, it checks that at least one of the player's worker
     * can build
     * @param turnInfo containing information about current turn
     * @param gameBoard object representing the gameboard
     * @param player player whose losing condition has to be checked
     * @param chosenWorker is the number of the worker to check
     * @return a boolean which says if the player lost or not
     */
    @Override
    public boolean buildingLoss(TurnInfo turnInfo, GameBoard gameBoard, Player player, int chosenWorker) {

        //lose check must be bypassed if the player tries to build more than twice or tries to build twice before moving
        //OR if the chosenWorker is invalid
        if(turnInfo.getNumberOfBuilds()>=2 || (turnInfo.getNumberOfBuilds()==1 && !turnInfo.getHasAlreadyMoved()) || (chosenWorker!=0 && chosenWorker!=1)){
            return false;
        }

        int possibility;
        int x;
        int y;

        //if the player has already moved,
        if(turnInfo.getHasAlreadyMoved()){

            possibility = 8;

            //only the worker that moved must be checked
            x = player.getWorker(turnInfo.getChosenWorker()).getCurrentPosition().getX();
            y = player.getWorker(turnInfo.getChosenWorker()).getCurrentPosition().getY();

            for(int j=y-1;j<=y+1;j++){
                for(int i=x-1; i<=x+1;i++){

                    if(!(j==y && i==x)){

                        if( j<0 || j>4 || i<0 || i>4 ||
                                gameBoard.getTowerCell(i,j).isTowerCompleted() ||
                                gameBoard.getTowerCell(i,j).hasWorkerOnTop()){

                            possibility--;
                        }
                    }
                }
            }
            return possibility == 0;

        }else{

            possibility = 16;

            for(int w=0;w<2;w++) {
                x = player.getWorker(w).getCurrentPosition().getX();
                y = player.getWorker(w).getCurrentPosition().getY();

                for (int j = y - 1; j <= y + 1; j++) {
                    for (int i = x - 1; i <= x + 1; i++) {

                        if (!(j == y && i == x)) {

                            if (j < 0 || j > 4 || i < 0 || i > 4 ||
                                    gameBoard.getTowerCell(i, j).isTowerCompleted() ||
                                    gameBoard.getTowerCell(i, j).hasWorkerOnTop()) {

                                possibility--;
                            }
                        }
                    }
                }
            }

            return possibility == 0;

        }

    }

}
