package it.polimi.ingsw.messages.GameToPlayerMessages.Others;

import java.io.Serializable;

/**
 * this message is sent to the client so that the player's info can be requested. It is sent again with the parameter
 * nicknameTaken=true if the nickname is already being used by someone else
 */
public class PlayerInfoRequest implements Serializable {

    private final boolean nicknameTaken;

    public PlayerInfoRequest(boolean nicknameTaken) {
        this.nicknameTaken = nicknameTaken;
    }

    public boolean isNicknameTaken() {
        return nicknameTaken;
    }
}
