package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.PlayerInfo;
import it.polimi.ingsw.messages.PlayerToGameMessages.CompleteMessages.PlayerCardChoice;
import it.polimi.ingsw.messages.PlayerToGameMessages.CompleteMessages.PlayerMessage;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.CardChoice;
import it.polimi.ingsw.model.GodCard;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.ServerSideConnection;
import it.polimi.ingsw.supportClasses.FakeConnection;
import it.polimi.ingsw.view.VirtualView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    Server server;
    ServerSideConnection c1;
    ServerSideConnection c2;
    ServerSideConnection c3;

    Player testPlayer;
    Player enemy1Player;
    Player enemy2Player;
    PlayerInfo testPlayerInfo;
    PlayerInfo enemy1Info;
    PlayerInfo enemy2Info;

    Controller controller;
    Model model;
    ArrayList<VirtualView> virtualViews;


    @AfterEach
    void end() {
        //closing serverSocket
        try {
            server.closeServerSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @BeforeEach
    void init() {

        try {
            server = new Server();
            c1 = new FakeConnection(new Socket(), server, "c1");
            c2 = new FakeConnection(new Socket(), server, "c2");
            c3 = new FakeConnection(new Socket(), server, "c3");
        } catch (IOException e) {
            e.printStackTrace();
        }

        testPlayerInfo = new PlayerInfo("xXoliTheQueenXx", new GregorianCalendar(1998, Calendar.SEPTEMBER, 9), 3);
        testPlayer = new Player(testPlayerInfo);

        enemy1Info = new PlayerInfo("enemy1", new GregorianCalendar(1990, Calendar.NOVEMBER, 30), 3);
        enemy1Player = new Player(enemy1Info);

        enemy2Info = new PlayerInfo("enemy2", new GregorianCalendar(1995, Calendar.DECEMBER, 7), 3);
        enemy2Player = new Player(enemy2Info);

        ArrayList<Player> players = new ArrayList<>();
        players.add(testPlayer);
        players.add(enemy1Player);
        players.add(enemy2Player);
        ArrayList<ServerSideConnection> connections = new ArrayList<>();
        connections.add(c1);
        connections.add(c2);
        connections.add(c3);

        controller = new Controller(players, connections);

        virtualViews = controller.getVirtualViews();
        model = controller.getModel();
    }

    @Test
    void cardAssignment(){

        String[] names={"Athena","Prometheus","Artemis"};
        PlayerMessage message0 = new PlayerCardChoice(virtualViews.get(0),testPlayer,new CardChoice(names));

        assertTrue(model.getGameDeck().getDeck().isEmpty());

        controller.update(message0);

        //cards to use selected correctly
        assertEquals(names[0], model.getGameDeck().getDeck().get(0).getGodName());
        assertThrows(IllegalArgumentException.class, ()->model.getSelectionDeck().findCard("Athena"));
        assertEquals(names[1], model.getGameDeck().getDeck().get(1).getGodName());
        assertThrows(IllegalArgumentException.class, ()->model.getSelectionDeck().findCard("Prometheus"));
        assertEquals(names[2], model.getGameDeck().getDeck().get(2).getGodName());
        assertThrows(IllegalArgumentException.class, ()->model.getSelectionDeck().findCard("Artemis"));
        assertEquals(3,model.getGameDeck().getDeck().size());

        //first player choice
        String[] name1={"Prometheus"};
        PlayerMessage message1 = new PlayerCardChoice(virtualViews.get(1),enemy1Player,new CardChoice(name1));

        //take Prometheus card from deck
        GodCard card1=model.getGameDeck().getDeck().get(1);
        controller.update(message1);
        //size decreased by one
        assertEquals(2,model.getGameDeck().getDeck().size());
        //athena and artemis still present, prometheus isn't
        assertEquals("Athena", model.getGameDeck().getDeck().get(0).getGodName());
        assertEquals("Artemis", model.getGameDeck().getDeck().get(1).getGodName());
        assertThrows(IllegalArgumentException.class, ()->model.getGameDeck().findCard("Prometheus"));
        //player assigned correct card object
        assertEquals(card1,virtualViews.get(1).getPlayer().getGodCard());


        //second player choice
        String[] name2={"Athena"};
        PlayerMessage message2 = new PlayerCardChoice(virtualViews.get(2),enemy2Player,new CardChoice(name2));
        //take Athena and Artemis card from deck
        GodCard card2=model.getGameDeck().getDeck().get(0);
        GodCard card3=model.getGameDeck().getDeck().get(1);
        controller.update(message2);
        //size decreased
        assertEquals(0,model.getGameDeck().getDeck().size());
        assertThrows(IllegalArgumentException.class, ()->model.getGameDeck().findCard("Athena"));
        assertThrows(IllegalArgumentException.class, ()->model.getGameDeck().findCard("Artemis"));
        //players assigned correct card
        assertEquals(card2,virtualViews.get(2).getPlayer().getGodCard());
        assertEquals(card3,virtualViews.get(0).getPlayer().getGodCard());


    }

}