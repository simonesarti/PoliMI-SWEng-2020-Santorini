package it.polimi.ingsw.view;

import it.polimi.ingsw.client.ClientSideConnection;
import it.polimi.ingsw.messages.GameToPlayerMessages.Notify.NewBoardStateMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.Others.PossibleCardsMessage;
import it.polimi.ingsw.messages.PlayerInfo;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.CardChoice;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.StartingPositionChoice;
import it.polimi.ingsw.view.cli.Cli;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ViewTest {

    View cli = new Cli(new ClientSideConnection("127.0.0.1",12345));

    @Test
    void isPositionValid() {

        String pos = "x, m2";
        assertFalse(cli.isPositionValid(pos));
        String pos1 = "3,5";
        assertFalse(cli.isPositionValid(pos1));
    }

    @Test
    void isDateValid() {
    }

    @Test
    void isChosenGodsValid() {
        ArrayList<String> listOfGods = new ArrayList<String>();
        listOfGods.add("Athena");
        listOfGods.add("Demeter");
        listOfGods.add("Minotaur");
        listOfGods.add("Prometheus");
        listOfGods.add("Artemis");

        //creating message
        PossibleCardsMessage message = new PossibleCardsMessage(listOfGods,3);

        //creating chosenGods array
        String[] chosenGods1 = {"Athena","Artemis","Minotaur"};

        assertTrue(cli.isChosenGodsValid(chosenGods1,3,message));

        String[] chosenGods2 = {"Athena","Minotaur","Ciao"};
        assertFalse(cli.isChosenGodsValid(chosenGods2,3,message));

        String[] chosenGods3 = {"Prometheus"};
        assertTrue(cli.isChosenGodsValid(chosenGods3,1,message));


    }
}