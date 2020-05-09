package it.polimi.ingsw.messages;

import java.io.Serializable;
import java.util.ArrayList;

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
