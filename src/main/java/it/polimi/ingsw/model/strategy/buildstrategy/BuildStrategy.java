package it.polimi.ingsw.model.strategy.buildstrategy;

import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Worker;

public interface BuildStrategy {

    /**
     * @param gameboard
     * @param worker worker scelto per il turno
     * @param x x della towerCell in cui il player ha deciso di costruire
     * @param y y della towerCell in cui il player ha deciso di costruire
     */
    void build(GameBoard gameboard, Worker worker, int x, int y);
}
