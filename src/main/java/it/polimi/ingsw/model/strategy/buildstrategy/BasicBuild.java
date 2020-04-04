package it.polimi.ingsw.model.strategy.buildstrategy;

import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Position;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.piece.Block;
import it.polimi.ingsw.model.piece.Piece;

public class BasicBuild implements BuildStrategy {

    @Override
    public void build(GameBoard gameboard, Worker worker, Piece piece, int x, int y) {

        Position buildingPosition = new Position(x, y, gameboard.getTowerCell(x, y).getTowerHeight()+1);
        Position workerStartingPosition = new Position(worker.getCurrentPosition().getX(), worker.getCurrentPosition().getY(),worker.getCurrentPosition().getZ());

        //controllo che x e y siano all'interno della build
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
        else if(gameboard.getTowerCell(x,y).getTowerHeight()==3 &&  piece instanceof Block){
            //TODO notify() -> spedire messaggio errore alla view
            return;
        }

        else {
            //TODO gameboard.getTowerCell(x, y).getTowerHeight()== vecchia TowerHeight +1

            //se il piece scelto era di tipo dome, chiamo il metodo checkCompletion() che renderà true l'attributo towerCompleted.
            gameboard.getTowerCell(x,y).checkCompletion();

            return ;
        }



    }
}
