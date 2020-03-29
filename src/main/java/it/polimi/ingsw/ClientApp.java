package it.polimi.ingsw;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientApp {

    private String ip = "127.0.0.1";
    private int port = 12345;

    Socket socket = new Socket(ip, port);

    {
        try {
            socket = new Socket(ip, port);
            System.out.println("Connection established");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ClientApp() throws IOException {
    }


    public void main(String[] args) throws IOException{



        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
        socketOut.println("Voglio mandare questa frase");
        socketOut.flush();






    }


}
