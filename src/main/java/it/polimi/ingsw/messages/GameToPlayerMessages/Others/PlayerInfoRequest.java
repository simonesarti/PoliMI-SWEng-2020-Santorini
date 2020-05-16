package it.polimi.ingsw.messages.GameToPlayerMessages.Others;

import java.io.Serializable;

public class PlayerInfoRequest implements Serializable {

    private final boolean nicknameTaken;

    public PlayerInfoRequest(boolean nicknameTaken) {
        this.nicknameTaken = nicknameTaken;
    }

    public boolean isNicknameTaken() {
        return nicknameTaken;
    }
}
