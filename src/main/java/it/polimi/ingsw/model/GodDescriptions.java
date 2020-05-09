package it.polimi.ingsw.model;

import java.util.ArrayList;

public class GodDescriptions {

    private final ArrayList<String[]> descriptions=new ArrayList<>();

    public GodDescriptions() {

        String[] Apollo = {"Apollo", "God of music", "simple", "true", "Your Worker may move into an opponent Worker’s space by forcing their Worker to the space yours just vacated."};
        descriptions.add(Apollo);

        String[] Artemis = {"Artemis", "Goddes of the Hunt", "simple", "true", "Your Worker may move one additional time, but not back to its initial space."};
        descriptions.add(Artemis);

        String[] Athena = {"Athena", "Goddes of Wisdom", "simple", "true", "If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn."};
        descriptions.add(Athena);

        String[] Atlas = {"Atlas", "Titan Shouldering the Heavens", "simple", "true", "Your Worker may build a dome at any level."};
        descriptions.add(Atlas);

        String[] Demeter = {"Demeter", "Goddes of the Harvest", "simple", "true", "Your Worker may build one additional time, but not on the same space."};
        descriptions.add(Demeter);

        String[] Hephaestus = {"Hephaestus", "God of Blacksmiths", "simple", "true", "Your Worker may build one additional block (not dome) on top of your first block."};
        descriptions.add(Hephaestus);

        String[] Minotaur = {"Minotaur", "Bull-headed Monster", "simple", "true", "Your Worker may move into an opponent Worker’s space, if their Worker can be forced one space straight backwards to an unoccupied space at any level."};
        descriptions.add(Minotaur);

        String[] Pan = {"Pan", "God of the Wild", "simple", "true", "You also win if your Worker moves down two or more levels."};
        descriptions.add(Pan);

        String[] Prometheus = {"Prometheus", "Titan Benefactor of Mankind", "simple", "true", "If your Worker does not move up, it may build both before and after moving."};
        descriptions.add(Prometheus);

    }

    public ArrayList<String[]> getDescriptions() {
        return descriptions;
    }

}


