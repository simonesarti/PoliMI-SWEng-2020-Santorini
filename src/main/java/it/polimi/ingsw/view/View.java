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

public abstract class View implements Observer<Object>,Runnable {




    private final ClientSideConnection clientSideConnection;
    private boolean canStart;

    public View(ClientSideConnection clientSideConnection){

        this.setCanStart(false);
        this.clientSideConnection = clientSideConnection;
    }


    abstract public void handleNewBoardStateMessage(NewBoardStateMessage message);
    abstract public void handleInfoMessage(InfoMessage message);
    abstract public void handleErrorMessage(ErrorMessage message);

    abstract public PlayerInfo createPlayerInfo();
    abstract public StartingPositionChoice createStartingPositionChoice();
    abstract public CardChoice createCardChoice(PossibleCardsMessage message);



    @Override
    public void update(Object message){

        if(message instanceof NewBoardStateMessage){ handleNewBoardStateMessage((NewBoardStateMessage) message);}

        else if (message instanceof InfoMessage){

            handleInfoMessage((InfoMessage) message);

            if(((InfoMessage) message).getInfo().equals(GameMessage.welcome)){

                clientSideConnection.asyncSend(createPlayerInfo());
            }
        }

        else if (message instanceof ErrorMessage){ handleErrorMessage((ErrorMessage) message); }

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
