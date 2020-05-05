package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.PlayerInfo;
import it.polimi.ingsw.model.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;


//TODO ERRORE CHIUSURA CONNESSIONE ISTANTANEA FA ELIMINARE LA PARTITA INDICE -1, NULLPOINTEREXCEPTION SE ESEGUITI TUTTI INSIEME
class ServerTest {

    Server server;
    ServerSideConnection c1;
    ServerSideConnection c2;
    ServerSideConnection c3;
    ServerSideConnection c4;
    PlayerInfo player1Info;
    PlayerInfo player2Info;
    PlayerInfo player3Info;
    PlayerInfo player4Info;

    @AfterEach
    void end(){
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
            c1 = new ServerSideConnection(new Socket("127.0.0.1",12345),server);
            c2 = new ServerSideConnection(new Socket("127.0.0.1",12345),server);
            c3 = new ServerSideConnection(new Socket("127.0.0.1",12345),server);
            c4 = new ServerSideConnection(new Socket("127.0.0.1",12345),server);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO send exception cause by de send i Declaration() because without run the streams are not initialized

    @Test
    void lobby2PlayerGame(){

        player1Info=new PlayerInfo("simone",new GregorianCalendar(1998, Calendar.SEPTEMBER,16),2);
        player2Info=new PlayerInfo("olimpia",new GregorianCalendar(1998, Calendar.SEPTEMBER,9),2);

        PlayerConnection playerConnection1=new PlayerConnection(player1Info,c1);
        PlayerConnection playerConnection2=new PlayerConnection(player2Info,c2);

        server.lobby(playerConnection1);

        checkListsLength(1,0,0,0);

        assertAll(
                ()->assertEquals(player1Info, server.getTwoPlayerWaitingList().get(0).getPlayerInfo()),
                ()->assertEquals(c1,server.getTwoPlayerWaitingList().get(0).getServerSideConnection())
        );

        server.lobby(playerConnection2);

        checkListsLength(0,1,0,0);
        
        assertAll(
                ()->assertEquals(c1, server.getTwoPlayerGames().get(0).getConnection(0)),
                ()->assertEquals(c2, server.getTwoPlayerGames().get(0).getConnection(1))
        );
    }

    @Test
    void lobby3PlayerGame(){

        player1Info=new PlayerInfo("simone",new GregorianCalendar(1998, Calendar.SEPTEMBER,16),3);
        player2Info=new PlayerInfo("olimpia",new GregorianCalendar(1998, Calendar.SEPTEMBER,9),3);
        player3Info=new PlayerInfo("alessandro",new GregorianCalendar(1998, Calendar.SEPTEMBER,2),3);
        player4Info=new PlayerInfo("tizio",new GregorianCalendar(1998, Calendar.SEPTEMBER,2),3);

        PlayerConnection playerConnection1=new PlayerConnection(player1Info,c1);
        PlayerConnection playerConnection2=new PlayerConnection(player2Info,c2);
        PlayerConnection playerConnection3=new PlayerConnection(player3Info,c3);
        PlayerConnection playerConnection4=new PlayerConnection(player4Info,c4);

        server.lobby(playerConnection1);
        checkListsLength(0,0,1,0);
        assertAll(
                ()->assertEquals(player1Info, server.getThreePlayerWaitingList().get(0).getPlayerInfo()),
                ()->assertEquals(c1,server.getThreePlayerWaitingList().get(0).getServerSideConnection())
        );


        server.lobby(playerConnection2);
        checkListsLength(0,0,2,0);
        assertAll(
                ()->assertEquals(player2Info, server.getThreePlayerWaitingList().get(1).getPlayerInfo()),
                ()->assertEquals(c2,server.getThreePlayerWaitingList().get(1).getServerSideConnection())
        );

        server.lobby(playerConnection3);
        checkListsLength(0,0,0,1);
        assertAll(
                ()->assertEquals(c1,server.getThreePlayerGames().get(0).getConnection(0)),
                ()->assertEquals(c2,server.getThreePlayerGames().get(0).getConnection(1)),
                ()->assertEquals(c3,server.getThreePlayerGames().get(0).getConnection(2))
        );

        server.lobby(playerConnection4);

        checkListsLength(0,0,1,1);
        assertAll(
                ()->assertEquals(player4Info, server.getThreePlayerWaitingList().get(0).getPlayerInfo()),
                ()->assertEquals(c4, server.getThreePlayerWaitingList().get(0).getServerSideConnection())
        );
    }

    private void checkListsLength(int w2, int p2, int w3, int p3){

        assertEquals(w2,server.getTwoPlayerWaitingList().size());
        assertEquals(p2,server.getTwoPlayerGames().size());
        assertEquals(w3,server.getThreePlayerWaitingList().size());
        assertEquals(p3,server.getThreePlayerGames().size());
    }

}