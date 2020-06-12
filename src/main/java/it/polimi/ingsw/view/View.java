package it.polimi.ingsw.view;

import it.polimi.ingsw.client.ClientSideConnection;
import it.polimi.ingsw.messages.GameToPlayerMessages.Notify.GameStartMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.Notify.NewBoardStateMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.Others.*;
import it.polimi.ingsw.observe.Observer;

public abstract class View implements Observer<Object>{

    private final ClientSideConnection clientSideConnection;

    public View(ClientSideConnection clientSideConnection){
        this.clientSideConnection = clientSideConnection;
    }


    abstract public void handleNewBoardStateMessage(NewBoardStateMessage message);
    abstract public void handleInfoMessage(InfoMessage message);
    abstract public void handleErrorMessage(ErrorMessage message);

    abstract public void handlePlayerInfoRequest(PlayerInfoRequest message);
    abstract public void handleCardMessageRequest(PossibleCardsMessage message);
    abstract public void handleStartingPositionRequest();

    abstract public void handleGameStartMessage(GameStartMessage message);
    abstract  public void handleCloseConnectionMessage();

    @Override
    public void update(Object message){

        if(message instanceof NewBoardStateMessage){ handleNewBoardStateMessage((NewBoardStateMessage) message);}

        else if (message instanceof InfoMessage){ handleInfoMessage((InfoMessage) message);}

        else if (message instanceof ErrorMessage){ handleErrorMessage((ErrorMessage) message); }

        else if(message instanceof PlayerInfoRequest){ handlePlayerInfoRequest((PlayerInfoRequest) message); }

        else if(message instanceof PossibleCardsMessage) { handleCardMessageRequest((PossibleCardsMessage) message); }

        else if(message instanceof StartingPositionRequestMessage){ handleStartingPositionRequest();}

        else if(message instanceof GameStartMessage) { handleGameStartMessage((GameStartMessage)message);}

        else if(message instanceof CloseConnectionMessage){ handleCloseConnectionMessage();}

        else { throw new IllegalArgumentException(); }

    }

    public ClientSideConnection getClientSideConnection() {
        return clientSideConnection;
    }


}
