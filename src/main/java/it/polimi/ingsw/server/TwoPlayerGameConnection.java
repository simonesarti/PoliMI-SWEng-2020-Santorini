package it.polimi.ingsw.server;

/**
 * class used to save the associations between the connections of players playing in the same match
 */
public class TwoPlayerGameConnection {

    private final ServerSideConnection[] connections = new ServerSideConnection[2];

    public TwoPlayerGameConnection(ServerSideConnection connection1, ServerSideConnection connection2){
        this.connections[0]=connection1;
        this.connections[1]=connection2;
    }

    public ServerSideConnection getConnection(int n){
        return connections[n];
    }
}

