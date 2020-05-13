package it.polimi.ingsw.view;

import it.polimi.ingsw.messages.GameToPlayerMessages.Others.PossibleCardsMessage;
import it.polimi.ingsw.view.ClientViewSupportFunctions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ClientViewSupportFunctionsTest {

    ClientViewSupportFunctions support;

    @BeforeEach
    void init(){
        support=new ClientViewSupportFunctions();
    }


    @Test
    void nameToCorrectFormat() {
        String s="hEllOMYnameIsSimone";
        assertEquals("Hellomynameissimone",support.nameToCorrectFormat(s));
    }

    @Test
    void isChosenGodsValid(){
        ArrayList<String> listOfGods = new ArrayList<String>();

        listOfGods.add("Demeter");
        listOfGods.add("Athena");
        listOfGods.add("Minotaur");
        listOfGods.add("Prometheus");
        listOfGods.add("Artemis");

        //creating message
        PossibleCardsMessage message = new PossibleCardsMessage(listOfGods,3);

        //creating chosenGods array
        String[] chosenGods1 = {"Athena","Artemis","Minotaur"};

        assertTrue(support.isChosenGodsValid(chosenGods1,3,message));

        String[] chosenGods2 = {"Athena","Minotaur","Ciao"};
        assertFalse(support.isChosenGodsValid(chosenGods2,3,message));

        String[] chosenGods3 = {"Prometheus"};
        assertTrue(support.isChosenGodsValid(chosenGods3,1,message));
    }

    @Test
    void isPositionValid() {

        String pos = "x, m2";
        assertFalse(support.isPositionValid(pos));
        String pos1 = "3,5";
        assertFalse(support.isPositionValid(pos1));
    }

    @Test
    void isDateValid() {

        assertTrue(support.isDateValid("11-11-1990","11","11","1990"));
        assertFalse(support.isDateValid("11-13-1990","11","11","1990"));
        assertFalse(support.isDateValid("11-11-1990","11","x1","1990"));

    }

    @Test
    void isValidNumberOfPlayers() {

        assertTrue(support.isValidNumberOfPlayers("2"));
        assertTrue(support.isValidNumberOfPlayers("3"));
        assertFalse(support.isValidNumberOfPlayers("x"));
        assertFalse(support.isValidNumberOfPlayers("1"));


    }
}