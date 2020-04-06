package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.View;

public class PlayerBuildChoice extends Message{

    private final View view;
    private final Player player;
    private final int chosenWorker;
    private final int[] buildingInto = new int[2];
    private final String pieceType;

    //@pieceType deve già essere "Block" o "Dome"
    public PlayerBuildChoice(View view, Player player, int chosenWorker, int x, int y, String pieceType) {

        this.view = view;
        this.player = player;
        this.chosenWorker = chosenWorker;
        this.buildingInto[0]=x;
        this.buildingInto[1]=y;
        this.pieceType=pieceType;
    }

    public View getView(){ return view;}

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

