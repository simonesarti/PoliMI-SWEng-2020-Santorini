package it.polimi.ingsw.model.strategy.buildstrategy;

import it.polimi.ingsw.messages.PlayerBuildChoice;
import it.polimi.ingsw.messages.PlayerMovementChoice;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.piece.Piece;
import it.polimi.ingsw.messages.PlayerBuildChoice;

/**
 * Build strategies interface
 */
public interface BuildStrategy {

    //TODO forse non serve passare chosenWorker anche alla build(). Alla checkBuild sicuramente sì per colpa di alcuni dei
    String build(TurnInfo turnInfo, GameBoard gameboard, Player player,int chosenWorker, int[] buildingInto, String pieceType);


    String checkBuild(TurnInfo turnInfo, GameBoard gameboard, Player player,int chosenWorker, int[] buildingInto, String pieceType);

}
