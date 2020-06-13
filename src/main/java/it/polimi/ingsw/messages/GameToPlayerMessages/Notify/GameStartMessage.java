package it.polimi.ingsw.messages.GameToPlayerMessages.Notify;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.Player;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This type of message is used by the model to communicate to the virtualView, which then forwards it to client, that the game
 * can start. It contains the list of players' nicknames and cards
 */
public class GameStartMessage extends NotifyMessages implements Serializable {

    private final ArrayList<String> nicknames;
    private final ArrayList<Colour> colours;
    private final ArrayList<String> descriptions;

    public GameStartMessage(ArrayList<Player> players){

        nicknames=new ArrayList<>();
        colours=new ArrayList<>();
        descriptions=new ArrayList<>();

        for(Player player: players){
            nicknames.add(player.getNickname());
            colours.add(player.getColour());
            descriptions.add(player.getGodCard().cardDeclaration());
        }
    }

    public ArrayList<String> getNicknames() {
        return nicknames;
    }
    public ArrayList<Colour> getColours() {
        return colours;
    }
    public ArrayList<String> getDescriptions() {
        return descriptions;
    }

    //TEST CONSTRUCTOR
    public GameStartMessage(ArrayList<String> nicknames, ArrayList<Colour> colours, ArrayList<String> descriptions){
        this.nicknames=nicknames;
        this.colours=colours;
        this.descriptions=descriptions;
    }
}
