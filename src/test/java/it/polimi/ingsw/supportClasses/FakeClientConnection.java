package it.polimi.ingsw.supportClasses;

import it.polimi.ingsw.client.ClientSideConnection;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.*;

public class FakeClientConnection extends ClientSideConnection {

    public FakeClientConnection(String ip, int port) {
        super(ip, port);
    }

    @Override
    public synchronized void send(Object message) {
        if (message instanceof BuildData) {
            //System.out.println("sent BuildData");
        } else if (message instanceof CardChoice) {
            //System.out.println("sent CardChoice");
        } else if (message instanceof EndChoice) {
            //System.out.println("sent EndChoice");
        } else if (message instanceof QuitChoice) {
            //System.out.println("sent QuitChoice");
        } else if (message instanceof StartingPositionChoice) {
            //System.out.println("sent StartingPositionChoice");
        } else if (message instanceof MoveData) {
            //System.out.println("sent MoveData");
        }
    }
}
