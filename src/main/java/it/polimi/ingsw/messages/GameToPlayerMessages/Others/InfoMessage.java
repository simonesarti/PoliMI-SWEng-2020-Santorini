package it.polimi.ingsw.messages.GameToPlayerMessages.Others;

import java.io.Serializable;

/**
 * this message is sent to the client to let the player know some type of information
 */
public class InfoMessage implements Serializable {

    private final String info;

    public InfoMessage(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
