package it.polimi.ingsw.view;

import it.polimi.ingsw.client.ClientSideConnection;
import it.polimi.ingsw.messages.GameToPlayerMessages.NewBoardStateMessage;
import it.polimi.ingsw.messages.InfoMessage;
import it.polimi.ingsw.observe.Observer;

//TODO Cli e Gui nei loro metodi, quando viene fatta una mossa, creano il dataMessage e chiamano client.writeToSocket(dataMessage)
//TODO La View non deve avere un attributo Player vero? Sarebbe un problema perché voglio inizializzare la view separatamente senza avere bisogno di oggetto Player
public abstract class View implements Observer<Object> {


    private ClientSideConnection clientSideConnection;

    public View(ClientSideConnection clientSideConnection){

        this.clientSideConnection = clientSideConnection;
    }

    abstract public void showNewBoard(NewBoardStateMessage message);



    @Override
    public void update(Object message) {

        if(message instanceof NewBoardStateMessage){
            System.out.println("NewBoardStateMessage message arrived to client!");
        } else if (message instanceof InfoMessage){
            System.out.println("InfoMessage arrived to client!");
        } else {
            throw new IllegalArgumentException();
        }

    }
}
