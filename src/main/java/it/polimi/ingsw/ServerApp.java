package it.polimi.ingsw;

import it.polimi.ingsw.observe.Observer;
import it.polimi.ingsw.server.Connection;
import it.polimi.ingsw.server.Server;

import java.io.IOException;

public class ServerApp implements Observer<String>
{
    public static void main( String[] args )
    {

        Server server;



        try {
            server = new Server();
            server.run();

        } catch (IOException e) {
            System.err.println("Impossible to initialize the server: " + e.getMessage() + "!");
        }








    }

    @Override
    public void update(String message) {

    }
}
