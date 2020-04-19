package it.polimi.ingsw.messages.PlayerToGameMessages;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.View;

/**
 * PlayerBuildChoice message contains information about a specific build (what player, what type of Piece, which worker and so on...)
 * View is needed to report errors
 */
public class PlayerBuildChoice extends PlayerMessage {

    private final View view;
    private final Player player;
    private final int chosenWorker;
    private final int[] buildingInto = new int[2];
    private final String pieceType;

    /**
     *
     * @param view
     * @param player
     * @param chosenWorker
     * @param x
     * @param y
     * @param pieceType must be "Block" or "Dome"
     */
    public PlayerBuildChoice(View view, Player player,int chosenWorker, int x, int y, String pieceType) {

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

    public int[] getBuildingInto() {
        return buildingInto;
    }

    public int getChosenWorker() { return chosenWorker;}

    public String getPieceType(){
        return pieceType;
    }
}

