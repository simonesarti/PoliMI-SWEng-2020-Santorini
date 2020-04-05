package it.polimi.ingsw.model.strategy.buildstrategy;

import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.Position;
import it.polimi.ingsw.model.piece.Piece;

public interface BuildStrategy {

    /**
     * @param gameboard
     * @param worker chosen worker
     * @param x destination-towercell's x
     * @param y destination-towercell's y
     */
    void build(GameBoard gameboard, Worker worker, Piece piece, int x, int y);
}
