package it.polimi.ingsw;

public enum GodClassification {

    SIMPLE, ADVANCED;

    //converte stringa con la classificazione del dio nel cosrrispondente valore enumerativo
    public static GodClassification parseInput(String classification){
        return Enum.valueOf(GodClassification.class, classification);
    }

}
