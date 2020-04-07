package it.polimi.ingsw.model;



import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class GodCardTest {
    GodCard godcard = null;

    @BeforeEach
    public void setUp(){
        String[] s = {"Apollo", "GodofMusic", "SIMPLE", "move"};
        godcard = new GodCard(s);
    }

    //quali metodi testare?



}