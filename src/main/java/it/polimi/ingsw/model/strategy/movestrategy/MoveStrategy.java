package it.polimi.ingsw.model.strategy.movestrategy;

import it.polimi.ingsw.messages.PlayerMovementChoice;
import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Worker;

/**
 * Interface used to manage different move-algorithms
 */
public interface MoveStrategy {

    /**
     *
     * @param gameboard
     * @param message PlayerMovementChoice message
     */
    void move(GameBoard gameboard, PlayerMovementChoice message);
}
