package it.polimi.ingsw.messages.GameToPlayerMessages.Others;

import java.io.Serializable;

/**
 * this message is sent to the client to let the player know the error he made
 */
public class ErrorMessage implements Serializable {

    private final String error;

    public ErrorMessage(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
