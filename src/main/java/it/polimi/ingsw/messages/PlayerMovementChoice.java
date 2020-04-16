package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.View;

/**
 * PlayerMovementChoice message contains information about a specific move (which Player, which worker, which Towercell).
 * View is needed to report errors
 */
public class PlayerMovementChoice extends Message{

    private final View view;
    private final Player player;
    private final int chosenWorker;
    private final int[] movingTo = new int[2];

    //chosenWorker must be 0 or 1
    public PlayerMovementChoice(View view, Player player, int chosenWorker, int x, int y) {

        this.view=view;
        this.player = player;
        this.chosenWorker = chosenWorker;
        this.movingTo[0]=x;
        this.movingTo[1]=y;
    }

    public View getView(){ return view;}

    public Player getPlayer() {
        return player;
    }

    public int getChosenWorker() { return chosenWorker;}

    public int[] getMovingTo() {
        return movingTo;
    }
}
