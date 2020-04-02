package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Player;

import java.io.Serializable;

public class PlayerMovementChoice implements Serializable {

    private final Player player;
    private int chosenWorker;
    private final int[] movingTo = new int[2];

    public PlayerMovementChoice(Player player, int chosenWorker, int x, int y) {

        this.player = player;
        this.chosenWorker = chosenWorker;
        this.movingTo[0]=x;
        this.movingTo[1]=y;
    }

    public Player getPlayer() {
        return player;
    }

    public int getChosenWorker() {
        return chosenWorker;
    }

    public int[] getMovingTo() {
        return movingTo;
    }
}
