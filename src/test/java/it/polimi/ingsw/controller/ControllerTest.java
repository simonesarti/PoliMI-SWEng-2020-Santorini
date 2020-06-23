package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.PlayerToGameMessages.CompleteMessages.*;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.BuildData;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.CardChoice;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.PlayerInfo;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.StartingPositionChoice;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.ServerSideConnection;
import it.polimi.ingsw.supportClasses.FakeConnection;
import it.polimi.ingsw.view.VirtualView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
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
    GameBoard gameboard;


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
        gameboard=model.getGameBoard();
    }

    @Test
    void cardAssignment(){

        String[] names={"Athena","Prometheus","Artemis"};
        PlayerMessage message0 = new PlayerCardChoice(virtualViews.get(0),testPlayer,new CardChoice(names));

        assertTrue(model.getGameDeck().getDeck().isEmpty());

        controller.update(message0);

        //cards to use selected correctly
        assertEquals(names[0], model.getGameDeck().getDeck().get(0).getGodName());
        assertThrows(IllegalArgumentException.class, ()->model.getCompleteDeck().findCard("Athena"));
        assertEquals(names[1], model.getGameDeck().getDeck().get(1).getGodName());
        assertThrows(IllegalArgumentException.class, ()->model.getCompleteDeck().findCard("Prometheus"));
        assertEquals(names[2], model.getGameDeck().getDeck().get(2).getGodName());
        assertThrows(IllegalArgumentException.class, ()->model.getCompleteDeck().findCard("Artemis"));
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


    @Nested
    class statingPositions{

        @Test
        void onEachOther() {

            PlayerMessage wm1 = new PlayerStartingPositionChoice(virtualViews.get(0), testPlayer, new StartingPositionChoice(0, 0, 0, 0));
            controller.update(wm1);

            assertNull(gameboard.getTowerCell(0, 0).getFirstNotPieceLevel().getWorker());
        }

        @Test
        void outOfGameboard(){

            PlayerMessage wm1 = new PlayerStartingPositionChoice(virtualViews.get(0), testPlayer, new StartingPositionChoice(0, 0, 5, 0));
            controller.update(wm1);

            //not placed first
            assertNull(gameboard.getTowerCell(0, 0).getFirstNotPieceLevel().getWorker());

        }

        @Test
        void occupied(){
            PlayerMessage message = new PlayerStartingPositionChoice(virtualViews.get(0), testPlayer, new StartingPositionChoice(0, 0, 0, 1));
            controller.update(message);

            assertEquals(testPlayer.getWorker(0),gameboard.getTowerCell(0, 0).getFirstNotPieceLevel().getWorker());
            assertEquals(testPlayer.getWorker(1),gameboard.getTowerCell(0, 1).getFirstNotPieceLevel().getWorker());

            PlayerMessage wm2 = new PlayerStartingPositionChoice(virtualViews.get(2), enemy2Player, new StartingPositionChoice(2, 0, 0, 1));
            controller.update(wm2);
            assertNull(gameboard.getTowerCell(2, 0).getFirstNotPieceLevel().getWorker());
        }

        @Test
        void ok(){
            //setting Godcard to be sent
            GodDescriptions descriptions=new GodDescriptions();
            ArrayList<String[]> data=descriptions.getDescriptions();
            testPlayer.setGodCard(new GodCard(data.get(0)));
            enemy1Player.setGodCard(new GodCard(data.get(1)));
            enemy2Player.setGodCard(new GodCard(data.get(2)));

            PlayerMessage message1 = new PlayerStartingPositionChoice(virtualViews.get(0), testPlayer, new StartingPositionChoice(0, 0, 0, 1));
            controller.update(message1);

            assertEquals(testPlayer.getWorker(0),gameboard.getTowerCell(0, 0).getFirstNotPieceLevel().getWorker());
            assertEquals(testPlayer.getWorker(1),gameboard.getTowerCell(0, 1).getFirstNotPieceLevel().getWorker());
            correctInitialization(testPlayer,0,0,0,1);

            PlayerMessage message3 = new PlayerStartingPositionChoice(virtualViews.get(2), enemy2Player, new StartingPositionChoice(0, 2, 0, 3));
            controller.update(message3);

            assertEquals(enemy2Player.getWorker(0),gameboard.getTowerCell(0, 2).getFirstNotPieceLevel().getWorker());
            assertEquals(enemy2Player.getWorker(1),gameboard.getTowerCell(0, 3).getFirstNotPieceLevel().getWorker());
            correctInitialization(enemy2Player,0,2,0,3);

            PlayerMessage message2 = new PlayerStartingPositionChoice(virtualViews.get(1), enemy1Player, new StartingPositionChoice(1, 2, 1, 3));
            controller.update(message2);

            assertEquals(enemy1Player.getWorker(0),gameboard.getTowerCell(1, 2).getFirstNotPieceLevel().getWorker());
            assertEquals(enemy1Player.getWorker(1),gameboard.getTowerCell(1, 3).getFirstNotPieceLevel().getWorker());
            correctInitialization(enemy1Player,1,2,1,3);
        }

        private void correctInitialization(Player player,int x1, int y1, int x2, int y2){

            assertAll(
                    ()->assertEquals(x1,player.getWorker(0).getCurrentPosition().getX()),
                    ()->assertEquals(y1,player.getWorker(0).getCurrentPosition().getY()),
                    ()->assertEquals(x2,player.getWorker(1).getCurrentPosition().getX()),
                    ()->assertEquals(y2,player.getWorker(1).getCurrentPosition().getY())

            );

        }







    }


    @Nested
    class turnEnd{

        @Test
        void isEliminated(){
            model.setEliminated(enemy1Player);
            PlayerMessage message=new PlayerEndOfTurnChoice(virtualViews.get(1),enemy1Player);
            controller.update(message);
            //return an error message to the vv, eliminated
        }

        @Test
        void isNotHisTurn(){
            PlayerMessage message=new PlayerEndOfTurnChoice(virtualViews.get(1),enemy1Player);
            controller.update(message);
            //sends an error message to the vv, incorrect turn
        }

        @Test
        void turnCannotEnd() {
            PlayerMessage message = new PlayerEndOfTurnChoice(virtualViews.get(0), testPlayer);
            controller.update(message);
            //sends error message to the vv, turn not ended
        }


    }

    @Test
    void quitTest(){
        model.setEliminated(enemy1Player);
        PlayerMessage message=new PlayerQuitChoice(virtualViews.get(1),enemy1Player);
        controller.update(message);
        assertFalse(virtualViews.get(1).isObservingModel());
    }

    @Test
    void buildTestGameEnd(){
        GodDescriptions descriptions=new GodDescriptions();
        ArrayList<String[]> data=descriptions.getDescriptions();
        //Apollo
        testPlayer.setGodCard(new GodCard(data.get(0)));
        //Artemis
        enemy1Player.setGodCard(new GodCard(data.get(1)));
        //Athena
        enemy2Player.setGodCard(new GodCard(data.get(2)));

        int[][] towers =
                {
                        {0, 0, 4, 2, 2},
                        {4, 4, 4, 1, 4},
                        {4, 1, 0, 3, 4},
                        {0, 2, 2, 4, 4},
                        {3, 2, 1, 4, 0}
                };

        gameboard.generateBoard(towers);

        model.setEliminated(enemy1Player);
        //->2 players left

        testPlayer.getWorker(0).setStartingPosition(0, 0);
        testPlayer.getWorker(1).setStartingPosition(1, 0);

        //worker 1 moved
        model.getTurnInfo().setHasMoved();
        model.getTurnInfo().addMove();
        model.getTurnInfo().setChosenWorker(1);

        //POSITIONING TEST WORKERS
        gameboard.getTowerCell(0, 0).getFirstNotPieceLevel().setWorker(testPlayer.getWorker(0));
        testPlayer.getWorker(0).movedToPosition(0, 0, 0);

        gameboard.getTowerCell(1, 0).getFirstNotPieceLevel().setWorker(testPlayer.getWorker(1));
        testPlayer.getWorker(1).movedToPosition(1, 0, 0);

        PlayerMessage message = new PlayerBuildChoice(virtualViews.get(0), testPlayer, new BuildData(0, 1, 1, "Block"));
        controller.update(message);

        //enemy 2 wins because of Oli build Loss

    }


}