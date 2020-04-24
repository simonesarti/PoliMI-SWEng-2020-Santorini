package it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages;

import java.util.Calendar;

public class PlayerInfo extends DataMessage{

    private final String playerNickname;
    private final Calendar birthday;


    /**
     * Class Constructor
     *
     * @param playerNickname
     * @param birthday must be a valid date
     */
    public PlayerInfo(String playerNickname, Calendar birthday){
        this.playerNickname = playerNickname;
        this.birthday=birthday;
    }

    public String getPlayerNickname() {
        return playerNickname;
    }

    public Calendar getBirthday() {
        return birthday;
    }
}
