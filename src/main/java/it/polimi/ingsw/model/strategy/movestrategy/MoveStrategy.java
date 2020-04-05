package it.polimi.ingsw.model.strategy.movestrategy;

import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Worker;

/**
 * Interface used to manage different move-algorithms
 */
public interface MoveStrategy {

    /**
     * @param gameboard
     * @param worker chosen worker
     * @param x  destination-towercell's x
     * @param y destination-towercell's y
     */
    void move(GameBoard gameboard, Worker worker, int x, int y);
}
