package it.polimi.ingsw.messages;

import java.io.Serializable;
import java.util.Calendar;

public class PlayerInfo implements Serializable {

    private final String playerNickname;
    private final Calendar birthday;
    private final int numberOfPlayers;


    /**
     * Class Constructor
     *
     * @param playerNickname
     * @param birthday must be a valid date
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
