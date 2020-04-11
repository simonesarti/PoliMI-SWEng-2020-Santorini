package it.polimi.ingsw.model.strategy.movestrategy;

import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TurnInfo;

/**
 * Move strategies interface
 */
public interface MoveStrategy {

    /**
     *
     * @param gameboard
     */
    void move(TurnInfo turnInfo, GameBoard gameboard, Player player, int chosenWorker, int[] movingTo);

    /**
     *  @param gameboard
     * @return
     */
    String checkMove(TurnInfo turnInfo, GameBoard gameboard, Player player, int chosenWorker, int[] movingTo);

}
