package it.polimi.ingsw.model.strategy.winstrategy;

import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

public class PanWin implements WinStrategy {

    @Override
    public boolean checkWin(Player player, int chosenWorker) {

        Worker worker=player.getWorker(chosenWorker);
        return (
                (worker.getCurrentPosition().getZ()==3 && worker.getPreviousPosition().getZ()==2) ||
                (worker.getPreviousPosition().getZ() - worker.getCurrentPosition().getZ()>=2)
        );

    }
}
