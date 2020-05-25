package it.polimi.ingsw.GUI;

import it.polimi.ingsw.client.ClientSideConnection;

import javax.swing.*;

public class GameButton extends JButton{

    private final ClientSideConnection connection;

    public GameButton(ClientSideConnection connection) {
        this.connection = connection;
    }

    protected void setIcons(Images notPressed, Images pressed){
        setIcon(Images.getIcon(notPressed));
        setIcon(Images.getIcon(pressed));
    }

    protected void setSize(){}

    protected void setPosition(){}

    protected ClientSideConnection getConnection() {
        return connection;
    }
}