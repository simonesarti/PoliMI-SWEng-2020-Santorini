package it.polimi.ingsw.view;

import it.polimi.ingsw.client.ClientSideConnection;
import it.polimi.ingsw.messages.GameToPlayerMessages.Notify.GameStartMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.Notify.NewBoardStateMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.Others.*;
import it.polimi.ingsw.messages.PlayerInfo;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.CardChoice;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.StartingPositionChoice;
import it.polimi.ingsw.observe.Observer;

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

    abstract public void handlePlayerInfoRequest(PlayerInfoRequest message);
    abstract public void handleCardMessageRequest(PossibleCardsMessage message);
    abstract public void handleStartingPositionRequest();

    @Override
    public void update(Object message){

        if(message instanceof NewBoardStateMessage){ handleNewBoardStateMessage((NewBoardStateMessage) message);}

        else if (message instanceof InfoMessage){ handleInfoMessage((InfoMessage) message);}

        else if (message instanceof ErrorMessage){ handleErrorMessage((ErrorMessage) message); }

        else if(message instanceof PlayerInfoRequest){ handlePlayerInfoRequest((PlayerInfoRequest) message); }

        else if(message instanceof PossibleCardsMessage) { handleCardMessageRequest((PossibleCardsMessage) message); }

        else if(message instanceof StartingPositionRequestMessage){ handleStartingPositionRequest();}

        else if(message instanceof GameStartMessage) {

            setCanStart(true);
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
