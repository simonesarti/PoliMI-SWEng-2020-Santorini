package it.polimi.ingsw.messages;

import java.io.Serializable;
import java.util.ArrayList;

public class PossibleCardsMessage implements Serializable {

    private ArrayList<String> gods;

    public PossibleCardsMessage(ArrayList<String> gods) {
        this.gods = gods;
    }

    public ArrayList<String> getGods() {
        return gods;
    }
}
