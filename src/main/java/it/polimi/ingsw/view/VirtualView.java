package it.polimi.ingsw.view;

import it.polimi.ingsw.messages.GameToPlayerMessages.Others.GameMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.Notify.*;
import it.polimi.ingsw.messages.GameToPlayerMessages.Others.InfoMessage;
import it.polimi.ingsw.messages.PlayerToGameMessages.CompleteMessages.*;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.*;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.observe.Observable;
import it.polimi.ingsw.observe.Observer;
import it.polimi.ingsw.server.ServerSideConnection;

public class VirtualView extends Observable<PlayerMessage> implements Observer<NotifyMessages> {

    //ATTRIBUTES

    private final Player player;

    private final ServerSideConnection connectionToClient;

    private boolean observingModel;

    //this class's update is triggered by ServerSideConnection reading a player messages and notifies the virtual view itself
    private class PlayerMessageReceiver implements Observer<DataMessage> {

        @Override
        public void update(DataMessage message) {
            notifyController(message);
        }

    }

    private final PlayerMessageReceiver playerMessageReceiver = new PlayerMessageReceiver();


    public VirtualView(Player player, ServerSideConnection c){
        this.player=player;
        this.connectionToClient =c;
        observingModel=true;
        //attach DataMessage Observer to ServerSideConnection object
        c.addObserver(playerMessageReceiver);
    }

    public Player getPlayer(){
        return player;
    }

    public boolean isObservingModel() {
        return observingModel;
    }

    //TODO
    private void notifyController(DataMessage message){

        if(message instanceof MoveData){
            notify(new PlayerMovementChoice(this,player,(MoveData)message));
        }else if(message instanceof BuildData){
            notify(new PlayerBuildChoice(this,player,(BuildData)message));
        }else if(message instanceof EndChoice){
            notify(new PlayerEndOfTurnChoice(this,player));
        }else if(message instanceof QuitChoice){
            notify(new PlayerQuitChoice(this,player));
        }else if(message instanceof CardChoice){
            notify(new PlayerCardChoice(this,player,(CardChoice)message));
        }else if(message instanceof StartingPositionChoice){
            notify(new PlayerStartingPositionChoice(this,player,(StartingPositionChoice)message));
        }
    }

    public void reportToClient(Object message){
        connectionToClient.send(message);
    }

    public void leave(){
        //the controller removes this virtualView from being model's observer
        observingModel=false;
        //this virtualView stops listening to new inputs
        connectionToClient.removeObserver(playerMessageReceiver);
        //connection thread terminates
        connectionToClient.notInUse();
    }

    @Override
    public void update(NotifyMessages message){

        if(message instanceof NewBoardStateMessage){
            reportToClient((NewBoardStateMessage)message);

        }

        else if(message instanceof LoseMessage){

            if(((LoseMessage) message).getPlayer() == this.getPlayer()) {
                reportToClient(new InfoMessage(GameMessage.lose));
            }else{
                reportToClient(new InfoMessage("Player " + ((LoseMessage) message).getPlayer().getNickname() + " lost\n"));
            }

        }

        else if(message instanceof NewTurnMessage){

            String s="The previous turn ended. ";

            if(((NewTurnMessage)message).getPlayer()==this.getPlayer()){
                reportToClient(new InfoMessage(s+"It's your turn now"));
            }else{
                reportToClient(new InfoMessage(s+"It's "+((NewTurnMessage) message).getPlayer().getNickname()+ "'s turn now"));
            }

        }

        else if(message instanceof WinMessage){

            if(((WinMessage)message).getPlayer()==this.getPlayer()){
                reportToClient(new InfoMessage(GameMessage.win));

            }else{
                reportToClient(new InfoMessage("Player "+((WinMessage) message).getPlayer().getNickname()+ " won"));
            }
            connectionToClient.removeObserver(playerMessageReceiver);


        }else if(message instanceof EndOfGameMessage) {

            if(((EndOfGameMessage)message).getPlayer()==this.getPlayer()){
                connectionToClient.deactivate();
                //deactivates winner connection, as a result every in-game player's connection is terminated
                //and the match ends
            }
        }


    }


}
