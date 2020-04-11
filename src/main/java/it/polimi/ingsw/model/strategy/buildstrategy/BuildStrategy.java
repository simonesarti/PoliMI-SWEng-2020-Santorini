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

    String build(TurnInfo turnInfo, GameBoard gameboard, Player player, int[] buildingInto, String pieceType);

    String checkBuild(TurnInfo turnInfo, GameBoard gameboard, Player player, int[] buildingInto, String pieceType);

}
