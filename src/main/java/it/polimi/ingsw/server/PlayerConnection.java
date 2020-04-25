package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.PlayerInfo;

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
