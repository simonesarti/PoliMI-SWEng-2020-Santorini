package it.polimi.ingsw.view;

import it.polimi.ingsw.client.ClientSideConnection;
import it.polimi.ingsw.messages.GameToPlayerMessages.Others.GameMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.Notify.NewBoardStateMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.Others.InfoMessage;
import it.polimi.ingsw.messages.PlayerInfo;
import it.polimi.ingsw.observe.Observer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

//TODO Cli e Gui nei loro metodi, quando viene fatta una mossa, creano il dataMessage e chiamano client.writeToSocket(dataMessage)
//TODO La View non deve avere un attributo Player vero? Sarebbe un problema perché voglio inizializzare la view separatamente senza avere bisogno di oggetto Player
public abstract class View implements Observer<Object>,Runnable {




    private final ClientSideConnection clientSideConnection;
    private boolean canStart;

    public View(ClientSideConnection clientSideConnection){

        this.setCanStart(false);
        this.clientSideConnection = clientSideConnection;
    }

    abstract public void showNewBoard(NewBoardStateMessage message);
    abstract public PlayerInfo createPlayerInfo();



    @Override
    public void update(Object message){

        if(message instanceof NewBoardStateMessage){
            System.out.println("NewBoardStateMessage message arrived to client!");
            showNewBoard((NewBoardStateMessage) message);
        } else if (message instanceof InfoMessage){

            System.out.println("Infomessage arrived to view, here it is: "+((InfoMessage) message).getInfo());
            if(((InfoMessage) message).getInfo().equals(GameMessage.welcome)){
                clientSideConnection.asyncSend(createPlayerInfo());
                //TODO PER ADESSO LO METTO QUI SOTTO, MA VA MESSO A FINE FASE PREPARAZIONE
                setCanStart(true);
                System.out.println("Ora canStart: "+getCanStart());


            }

        } /*else {
            throw new IllegalArgumentException();
        }
        */

    }



    public boolean isDateValid(String date){

        String DATE_FORMAT = "dd-MM-yyyy";

        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

    }

    public synchronized boolean getCanStart() {
        return canStart;
    }

    public synchronized void setCanStart(boolean canStart) {
        this.canStart = canStart;
    }

    public ClientSideConnection getClientSideConnection() {
        return clientSideConnection;
    }


}
