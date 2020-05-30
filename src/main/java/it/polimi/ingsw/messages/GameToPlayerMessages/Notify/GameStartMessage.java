package it.polimi.ingsw.messages.GameToPlayerMessages.Notify;

import it.polimi.ingsw.model.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class GameStartMessage extends NotifyMessages implements Serializable {

    private final ArrayList<String> nicknames=new ArrayList<>();
    private final ArrayList<String> descriptions=new ArrayList<>();

    public GameStartMessage(ArrayList<Player> players){

        for(Player player: players){
            nicknames.add(player.getNickname());
            descriptions.add(player.getGodCard().cardDeclaration());
        }
    }

    public ArrayList<String> getNicknames() {
        return nicknames;
    }

    public ArrayList<String> getDescriptions() {
        return descriptions;
    }
}
