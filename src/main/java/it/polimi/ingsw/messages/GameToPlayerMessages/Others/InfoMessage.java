package it.polimi.ingsw.messages.GameToPlayerMessages.Others;

import java.io.Serializable;

public class InfoMessage implements Serializable {

    private final String info;

    public InfoMessage(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
