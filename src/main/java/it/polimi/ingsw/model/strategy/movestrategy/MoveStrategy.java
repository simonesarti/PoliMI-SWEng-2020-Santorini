package it.polimi.ingsw.model.strategy.movestrategy;

import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Player;

/**
 * Move strategies interface
 */
public interface MoveStrategy {

    /**
     *
     * @param gameboard
     */
    void move(GameBoard gameboard, Player player, int chosenWorker, int[] movingTo);

    /**
     *  @param gameboard
     * @return
     */
    String checkMove(GameBoard gameboard, Player player, int chosenWorker, int[] movingTo);

    boolean getAlreadyMoved();

    void setAlreadyMoved(boolean x);
}
