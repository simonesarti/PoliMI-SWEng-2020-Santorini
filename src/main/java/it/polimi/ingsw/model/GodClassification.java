package it.polimi.ingsw.model;

public enum GodClassification {

    SIMPLE, ADVANCED;

    //converts the string containing the gods' classification into the corresponding enumeration value
    public static GodClassification parseInput(String classification){
        return Enum.valueOf(GodClassification.class, classification.toUpperCase());
    }

}
