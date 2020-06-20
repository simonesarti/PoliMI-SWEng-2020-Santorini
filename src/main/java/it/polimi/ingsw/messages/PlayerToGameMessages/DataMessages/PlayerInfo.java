package it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages;

import java.io.Serializable;
import java.util.Calendar;

/**
 * contains the player's information requested at the start of the game
 */
public class PlayerInfo implements Serializable {

    private final String playerNickname;
    private final Calendar birthday;
    private final int numberOfPlayers;


    /**
     * Class Constructor
     * @param playerNickname is the player's nickname
     * @param birthday must be a valid date
     * @param numberOfPlayers is the number of players in the game
     */
    public PlayerInfo(String playerNickname, Calendar birthday, int numberOfPlayers){
        this.playerNickname = playerNickname;
        this.birthday=birthday;
        this.numberOfPlayers=numberOfPlayers;
    }

    public String getPlayerNickname() {
        return playerNickname;
    }

    public Calendar getBirthday() {
        return birthday;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
}
