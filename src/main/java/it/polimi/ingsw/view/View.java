package it.polimi.ingsw.view;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.messages.GameToPlayerMessages.NewBoardStateMessage;
import it.polimi.ingsw.messages.InfoMessage;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.DataMessage;
import it.polimi.ingsw.observe.Observer;

//TODO Cli e Gui nei loro metodi, quando viene fatta una mossa, creano il dataMessage e chiamano client.writeToSocket(dataMessage)
//TODO La View non deve avere un attributo Player vero? Sarebbe un problema perché voglio inizializzare la view separatamente senza avere bisogno di oggetto Player
public abstract class View implements Observer<Object> {


    private Client client;

    public View(Client client){

        this.client = client;
    }

    abstract public void showNewBoard(NewBoardStateMessage message);



    @Override
    public void update(Object message) {

        if(message instanceof InfoMessage){
            //do something
        }

        else if (message instanceof NewBoardStateMessage){
            showNewBoard((NewBoardStateMessage)message);
        }
        else{
            throw new IllegalArgumentException();
        }

    }
}
