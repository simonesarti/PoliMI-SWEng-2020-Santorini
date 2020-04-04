package it.polimi.ingsw.model.strategy.movestrategy;

import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Worker;

public interface MoveStrategy {

    /**
     * @param gameboard
     * @param worker worker scelto per il turno
     * @param x x della towerCell in cui il player ha deciso di muoversi
     * @param y y della towerCell in cui il player ha deciso di muoversi
     */
    boolean move(GameBoard gameboard, Worker worker, int x, int y);
}
