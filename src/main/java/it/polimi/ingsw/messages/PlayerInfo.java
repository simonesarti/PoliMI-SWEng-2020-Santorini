package it.polimi.ingsw.messages;

import java.util.Calendar;

public class PlayerInfo extends Message{

    private final String playerNickname;
    private final Calendar birthday;

    //@deve essere passata data valida
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
