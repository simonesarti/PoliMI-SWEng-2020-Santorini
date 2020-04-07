package it.polimi.ingsw.model.strategy.buildstrategy;

import it.polimi.ingsw.messages.PlayerBuildChoice;
import it.polimi.ingsw.messages.PlayerMovementChoice;
import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.Position;
import it.polimi.ingsw.model.piece.Piece;
import it.polimi.ingsw.messages.PlayerBuildChoice;

public interface BuildStrategy {

    /**
     * @param gameboard
     * @param  message PlayerBuildChoice message
     */
    public void build(GameBoard gameboard, PlayerBuildChoice message);


    /**
     * @param gameboard
     * @param message PlayerBuildChoice message
     * @return
     */
    boolean checkBuild(GameBoard gameboard, PlayerBuildChoice message);
}
