package it.polimi.ingsw.model.strategy.winstrategy;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

public class PanWin implements WinStrategy {

    /**
     * checks it the player won, which happens when the selected worker moves from the second to the
     * third level of a tower or when it jumps down by two or more levels
     * @param player is the player
     * @param chosenWorker is the worker who moved
     * @return a boolean that says if the player won or not
     */
    @Override
    public boolean checkWin(Player player, int chosenWorker) {

        Worker worker=player.getWorker(chosenWorker);
        return (
                (worker.getCurrentPosition().getZ()==3 && worker.getPreviousPosition().getZ()==2) ||
                (worker.getPreviousPosition().getZ() - worker.getCurrentPosition().getZ()>=2)
        );

    }
}
