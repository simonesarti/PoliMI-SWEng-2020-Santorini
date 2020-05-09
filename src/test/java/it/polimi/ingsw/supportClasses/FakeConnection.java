package it.polimi.ingsw.supportClasses;

import it.polimi.ingsw.messages.ErrorMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.NewBoardStateMessage;
import it.polimi.ingsw.messages.InfoMessage;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.ServerSideConnection;

import java.net.Socket;

public class FakeConnection extends ServerSideConnection {

    private String name;

    public FakeConnection(Socket socket, Server server,String c) {
        super(socket, server);
        name=c;
    }

    @Override
    public void asyncSend(Object message) {

        if(message instanceof NewBoardStateMessage){
            System.out.println(name+" received new board state");
        }else if(message instanceof InfoMessage){
            System.out.println("message to "+name+": "+((InfoMessage)message).getInfo());
        }else if(message instanceof ErrorMessage){
            System.out.println("error to "+name+": "+((ErrorMessage)message).getError());
        }else{
            System.out.println("this message is neither a board, a InfoMessage or a ErrorMessage");
            System.out.println(message.toString());
        }
    }
}
