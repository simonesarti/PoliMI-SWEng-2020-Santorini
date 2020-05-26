package it.polimi.ingsw.supportClasses;

import it.polimi.ingsw.messages.GameToPlayerMessages.Notify.GameStartMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.Others.*;
import it.polimi.ingsw.messages.GameToPlayerMessages.Notify.NewBoardStateMessage;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.ServerSideConnection;

import java.net.Socket;

public class FakeConnection extends ServerSideConnection {

    private final String name;

    public FakeConnection(Socket socket, Server server,String c) {
        super(socket, server);
        name=c;
    }

    @Override
    public void send(Object message) {

        if (message instanceof NewBoardStateMessage) {
            System.out.println(name + " received new board state");
        } else if (message instanceof InfoMessage) {
            System.out.println("message to " + name + ": " + ((InfoMessage) message).getInfo());
        } else if (message instanceof ErrorMessage) {
            System.out.println("error to " + name + ": " + ((ErrorMessage) message).getError());
        }else if(message instanceof PlayerInfoRequest){
            System.out.println("Player info requested to "+name);
        }else if(message instanceof PossibleCardsMessage) {
            System.out.println("god list sent to"+name);
        }else if(message instanceof StartingPositionRequestMessage) {
            System.out.println("starting position requested to " + name);
        }else if(message instanceof GameStartMessage){
            System.out.println(name+", The game is starting!");
        }else{
            System.out.println("this message is neither a board, a InfoMessage, a ErrorMessage, PossibleCardsMessage or StartingPostion message");
            System.out.println(message.toString());
        }
    }

    @Override
    public synchronized void closeMatch(){
        System.out.println("victory");
    }

    @Override
    public synchronized void closeConnection(){
        System.out.println("connection closed");
    }
}
