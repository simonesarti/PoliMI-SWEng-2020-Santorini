package it.polimi.ingsw.GUI.messages;

import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.PlayerInfo;

import java.util.Calendar;

/**
 * message containing the personal information requested to the player by the first dialog
 */
public class PlayerPersonalData extends ActionMessage{

    private final PlayerInfo playerInfo;

    public PlayerPersonalData(String nickname, Calendar birthday, int numberOfPlayers) {

        playerInfo=new PlayerInfo(nickname,birthday,numberOfPlayers);
    }

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }
}
