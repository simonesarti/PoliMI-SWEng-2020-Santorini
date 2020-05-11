package it.polimi.ingsw.view;

import it.polimi.ingsw.client.ClientSideConnection;
import it.polimi.ingsw.messages.GameToPlayerMessages.Others.*;
import it.polimi.ingsw.messages.GameToPlayerMessages.Notify.NewBoardStateMessage;
import it.polimi.ingsw.messages.PlayerInfo;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.CardChoice;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.StartingPositionChoice;
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
    abstract public StartingPositionChoice createStartingPositionChoice();
    abstract public CardChoice createCardChoice(PossibleCardsMessage message);



    @Override
    public void update(Object message){

        if(message instanceof NewBoardStateMessage){
            System.out.println("NewBoardStateMessage message arrived to client!");
            showNewBoard((NewBoardStateMessage) message);
        }

        else if (message instanceof InfoMessage){

            System.out.println("Infomessage arrived to view, here it is: "+((InfoMessage) message).getInfo());

            if(((InfoMessage) message).getInfo().equals(GameMessage.welcome)){

                clientSideConnection.asyncSend(createPlayerInfo());
            }
        }
        else if(message instanceof StartingPositionRequestMessage){

            clientSideConnection.asyncSend(createStartingPositionChoice());
        }

        else if(message instanceof PossibleCardsMessage) {

            clientSideConnection.asyncSend(createCardChoice((PossibleCardsMessage) message));


        }
        else if(message instanceof GameStartMessage) {

            //TODO VA MESSO A FINE FASE PREPARAZIONE, PER ORA LO METTO QUI, forse è giusto
            setCanStart(true);
            System.out.println("Ora ho messo canStart a true, sono nella update della View");
        }

        else {

            throw new IllegalArgumentException();
        }


    }


    public boolean isPositionValid(String pos){

        String delims = ",";
        String[] tokens = pos.split(delims);
        if(Integer.parseInt(tokens[0])<0 || Integer.parseInt(tokens[0])>4 || Integer.parseInt(tokens[1])<0 || Integer.parseInt(tokens[1])>4){

            return false;
        }
        return true;


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

    //TODO DA TESTARE BENE PERCHE' E' PROBABILE CHE SBUCHINO ERRORI
    public boolean isChosenGodsValid(String[] chosenGods, int numberOfChoices){

        for(int i=0; i<numberOfChoices-1; i++){
            for(int k=i+1; k<numberOfChoices; k++){
                if(chosenGods[i].equals(chosenGods[k])){
                    return false;
                }
            }

        }
        return true;

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
