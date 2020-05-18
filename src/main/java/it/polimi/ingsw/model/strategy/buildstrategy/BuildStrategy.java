package it.polimi.ingsw.model.strategy.buildstrategy;

import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TurnInfo;

/**
 * Build strategies interface
 */
public interface BuildStrategy {

    //TODO forse non serve passare chosenWorker anche alla build(). Alla checkBuild sicuramente sì per colpa di alcuni dei
    String build(TurnInfo turnInfo, GameBoard gameboard, Player player,int chosenWorker, int[] buildingInto, String pieceType);


    String checkBuild(TurnInfo turnInfo, GameBoard gameboard, Player player,int chosenWorker, int[] buildingInto, String pieceType);

}
