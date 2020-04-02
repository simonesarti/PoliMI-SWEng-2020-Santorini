package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Player;

import java.io.Serializable;

public class PlayerBuildChoice implements Serializable {

    private final Player player;
    private int chosenWorker;
    private final int[] buildingInto = new int[2];
    private final String pieceType;

    //@pieceType deve già essere "Block" o "Dome"
    public PlayerBuildChoice(Player player, int chosenWorker, int x, int y, String pieceType) {

        this.player = player;
        this.chosenWorker = chosenWorker;
        this.buildingInto[0]=x;
        this.buildingInto[1]=y;
        this.pieceType=pieceType;
    }

    public Player getPlayer() {
        return player;
    }

    public int getChosenWorker() {
        return chosenWorker;
    }

    public int[] getBuildingInto() {
        return buildingInto;
    }

    public String getPieceType(){
        return pieceType;
    }
}

