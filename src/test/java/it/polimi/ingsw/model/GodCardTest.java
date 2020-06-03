package it.polimi.ingsw.model;


import org.junit.jupiter.api.BeforeEach;

public class GodCardTest {
    GodCard godcard = null;

    @BeforeEach
    public void setUp(){
        String[] s = {"Apollo", "GodofMusic", "SIMPLE", "move"};
        godcard = new GodCard(s);
    }

    //quali metodi testare?



}