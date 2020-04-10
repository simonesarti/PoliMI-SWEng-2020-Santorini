package it.polimi.ingsw.model.strategy.winstrategy;

import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Player;

public interface WinStrategy {

    boolean checkWin(GameBoard gameBoard, Player player, int chosenWorker);
}
