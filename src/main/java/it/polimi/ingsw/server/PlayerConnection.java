package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.PlayerInfo;

/**
 * class that contains the couple player-connection
 */
public class PlayerConnection {

    private final PlayerInfo playerInfo;
    private final ServerSideConnection serverSideConnection;

    public PlayerConnection(PlayerInfo playerInfo, ServerSideConnection serverSideConnection) {
        this.playerInfo = playerInfo;
        this.serverSideConnection = serverSideConnection;
    }

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    public ServerSideConnection getServerSideConnection() {
        return serverSideConnection;
    }
}
