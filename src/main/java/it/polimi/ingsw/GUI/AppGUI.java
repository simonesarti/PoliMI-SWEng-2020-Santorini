package it.polimi.ingsw.GUI;

import it.polimi.ingsw.client.ClientSideConnection;

public class AppGUI {

    public static void main(String[] args){

        //new GUI(new ClientSideConnection("127.0.0.1",12345));
        new PlayerInfoRequestScreen(true);
    }
}
