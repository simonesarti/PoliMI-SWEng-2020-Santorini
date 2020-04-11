package it.polimi.ingsw.model.strategy.buildstrategy;

import it.polimi.ingsw.messages.PlayerBuildChoice;
import it.polimi.ingsw.messages.PlayerMovementChoice;
import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.Position;
import it.polimi.ingsw.model.piece.Piece;
import it.polimi.ingsw.messages.PlayerBuildChoice;

/**
 * Build strategies interface
 */
public interface BuildStrategy {


    //TODO Se vogliamo mettere attributo hasMoved, allora bisogna rimetterlo a false alla fine delle fasi di gioco dal controller.
    //TODO qesto vuol dire che getter e setter devono essere già presenti nell'interfaccia


    void build(GameBoard gameboard, Player player, int chosenWorker, int[] buildingInto, String pieceType);

    String checkBuild(GameBoard gameboard, Player player, int chosenWorker, int[] buildingInto, String pieceType);

    boolean getAlreadyBuilt();

    void setAlreadyBuilt(boolean x);

}
