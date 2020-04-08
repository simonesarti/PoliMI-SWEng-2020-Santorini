package it.polimi.ingsw.model.strategy.movestrategy;

import it.polimi.ingsw.messages.PlayerMovementChoice;
import it.polimi.ingsw.model.GameBoard;

/**
 * Move strategies interface
 */
public interface MoveStrategy {

    /**
     *
     * @param gameboard
     * @param message PlayerMovementChoice message
     */
    void move(GameBoard gameboard, PlayerMovementChoice message);

    /**
     *  @param gameboard
     * @param message PlayerMovementChoice message
     * @return
     */
    String checkMove(GameBoard gameboard, PlayerMovementChoice message);


    public boolean getAlreadyMoved();
}
