package it.polimi.ingsw.model.strategy.buildstrategy;

import it.polimi.ingsw.messages.PlayerMovementChoice;
import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Position;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.piece.Block;
import it.polimi.ingsw.model.piece.Piece;
import it.polimi.ingsw.messages.PlayerBuildChoice;


public class BasicBuild implements BuildStrategy {


    /**
     * changes towercell's height and checks if towercell is complete
     *
     * @param gameboard
     * @param message messaggio PlayerBuildChoice
     */
    @Override
    public void build(GameBoard gameboard, PlayerBuildChoice message) {

        Worker worker = message.getPlayer().getWorker(message.getChosenWorker());
        int x = message.getBuildingInto()[0];
        int y = message.getBuildingInto()[1];
        Position buildingPosition = new Position(x, y, gameboard.getTowerCell(x, y).getTowerHeight());
        Position workerStartingPosition = new Position(worker.getCurrentPosition().getX(), worker.getCurrentPosition().getY(),worker.getCurrentPosition().getZ());



        //controllo che x e y siano all'interno della board
        if (x < 0 || x > 4 || y < 0 || y > 4) {

            //TODO notify() -> spedire messaggio errore alla view
            return ;
        }


        //controllo che la torre non sia completa
        else if(gameboard.getTowerCell(x,y).isTowerCompleted()==true) {
            //TODO notify() -> spedire messaggio errore alla view
            return;
        }

        //workerPosition must be adjacent to buildingPosition
        else if (!workerStartingPosition.adjacent(buildingPosition)){
            //TODO notify() -> spedire messaggio errore alla view
            return;
        }

        //se il pezzo che il giocatore ha scelto è di tipo Block e la torre ha già altezza 3, mando mex di errore
        else if(gameboard.getTowerCell(x,y).getTowerHeight()==3 &&  message.getPieceType().equals("Block")){
            //TODO notify() -> spedire messaggio errore alla view
            return;
        }

        //se il pezzo che il giocatore ha scelto è di tipo Dome e la torre ha altezza < 3, mando mex di errore
        else if(gameboard.getTowerCell(x,y).getTowerHeight()<3 &&  message.getPieceType().equals("Dome")){
            //TODO notify() -> spedire messaggio errore alla view
            return;
        }


        else {
            gameboard.getTowerCell(x, y).increaseTowerHeight();

            //se il piece scelto era di tipo dome, chiamo il metodo checkCompletion() che renderà true l'attributo towerCompleted.
            gameboard.getTowerCell(x,y).checkCompletion();

            return ;
        }



    }
}
