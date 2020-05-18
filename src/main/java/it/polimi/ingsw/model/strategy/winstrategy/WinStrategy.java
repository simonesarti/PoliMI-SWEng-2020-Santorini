package it.polimi.ingsw.model.strategy.winstrategy;

import it.polimi.ingsw.model.Player;

public interface WinStrategy {

    boolean checkWin(Player player, int chosenWorker);
}
