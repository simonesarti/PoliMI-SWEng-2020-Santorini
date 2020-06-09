package it.polimi.ingsw.GUI.messages;

import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.PlayerInfo;

import java.util.Calendar;

public class PlayerPersonalData extends ActionMessage{

    private final PlayerInfo playerInfo;

    public PlayerPersonalData(String nickname, Calendar birthday, int numberOfPlayers) {

        playerInfo=new PlayerInfo(nickname,birthday,numberOfPlayers);
    }

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }
}
