package it.polimi.ingsw.messages.GameToPlayerMessages.Others;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * this message is sent to the client so that the player will choose numberOfChoice cards between the ones present in the list
 */
public class PossibleCardsMessage implements Serializable {

    private final ArrayList<String> gods;
    private final int numberOfChoices;

    public PossibleCardsMessage(ArrayList<String> gods, int numberOfChoices) {
        this.gods = gods;
        this.numberOfChoices=numberOfChoices;
    }

    public ArrayList<String> getGods() {
        return gods;
    }

    public int getNumberOfChoices() {
        return numberOfChoices;
    }
}
