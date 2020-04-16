package it.polimi.ingsw.model.strategy.movestrategy;

import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TurnInfo;

/**
 * Move strategies interface
 */
public interface MoveStrategy {

    /**
     * Performes the move
     *
     * @param turnInfo object containing information about current turn
     * @param gameboard object representing the gameboard
     * @param player player performing the move
     * @param chosenWorker chosen worker (0 or 1)
     * @param movingTo array containing destination towercell x,y
     * @return buildRequest message
     */
    String move(TurnInfo turnInfo, GameBoard gameboard, Player player, int chosenWorker, int[] movingTo);

    /**
     * Checks if the move is feasible
     *
     * @param turnInfo object containing information about current turn
     * @param gameboard object representing the gameboard
     * @param player player performing the move
     * @param chosenWorker chosen worker (0 or 1)
     * @param movingTo array containing destination towercell x,y
     * @return one of error messages or moveOk message
     */
    String checkMove(TurnInfo turnInfo, GameBoard gameboard, Player player, int chosenWorker, int[] movingTo);

}
