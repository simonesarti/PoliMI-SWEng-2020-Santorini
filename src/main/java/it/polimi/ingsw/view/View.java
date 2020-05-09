package it.polimi.ingsw.view;

import it.polimi.ingsw.client.ClientSideConnection;
import it.polimi.ingsw.messages.ErrorMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.NewBoardStateMessage;
import it.polimi.ingsw.messages.InfoMessage;
import it.polimi.ingsw.messages.PossibleCardsMessage;
import it.polimi.ingsw.observe.Observer;

//TODO Cli e Gui nei loro metodi, quando viene fatta una mossa, creano il dataMessage e chiamano client.writeToSocket(dataMessage)
//TODO La View non deve avere un attributo Player vero? Sarebbe un problema perché voglio inizializzare la view separatamente senza avere bisogno di oggetto Player
public abstract class View implements Observer<Object> {


    private ClientSideConnection clientSideConnection;

    public View(ClientSideConnection clientSideConnection){

        this.clientSideConnection = clientSideConnection;
    }

    abstract public void showGameBoard(NewBoardStateMessage message);

    abstract public void showInfo(InfoMessage message);

    abstract public void showError(ErrorMessage message);



    @Override
    public void update(Object message) {

        if(message instanceof NewBoardStateMessage){
            System.out.println("NewBoardStateMessage message arrived to client!");
            showGameBoard((NewBoardStateMessage) message);
        } else if (message instanceof InfoMessage) {
            System.out.println("Infomessage arrived to view, here it is: " + ((InfoMessage) message).getInfo());
        }else if(message instanceof ErrorMessage) {
            System.out.println("errorMessage arrived to view, here it is: " + ((ErrorMessage) message).getError());
        }else if(message instanceof PossibleCardsMessage){
            System.out.println("PossibleCardsMessage received");
        }else {
            throw new IllegalArgumentException();
        }

    }
}
