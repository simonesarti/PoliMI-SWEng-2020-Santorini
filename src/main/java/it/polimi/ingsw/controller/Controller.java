package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.observe.Observer;

public class Controller implements Observer<Message>{

    private final Model model;

    /**
     * Costruttore della classe Controller
     * @param model
     */
    public Controller(Model model){
        super();
        this.model = model;
    }

    /**
     * checks if it's the player's turn and calls the player's GodCard's MoveStrategy methods;
     *
     * @param message oggetto-messaggio contentente le informazioni riguardanti lo spostamento
     */
    private synchronized void performMove(PlayerMovementChoice message) {

        String checkResult;

        //TODO implementare reportError
        if(!model.isPlayerTurn(message.getPlayer())){
            //message.getView().reportError(gameMessage.wrongTurn);
            return;
        }
        //TODO fare ritornare a check una stringa di GameMessage
        checkResult=message.getPlayer().getGodCard().getMoveStrategy().checkMove(model.getGameBoard(), message);

        if(checkResult.equals(GameMessage.moveOK)){
            message.getPlayer().getGodCard().getMoveStrategy().move(model.getGameBoard(), message);
        }
        else{
            //TODO impementare reportError
            //message.getView().reportError(checkResult);
        }
    }

    /**
     *
     * checks if it's the player's turn and calls the player's GodCard's BuildStrategy
     *
     * @param message messaggio di tipo PlayerBuildChoice
     */
    private synchronized void performBuild(PlayerBuildChoice message){

        String checkResult;

        //TODO implementare reporError
        if(!model.isPlayerTurn(message.getPlayer())){
            //message.getView().reportError(gameMessage.wrongTurn);
            return;
        }

        checkResult=message.getPlayer().getGodCard().getBuildStrategy().checkBuild(model.getGameBoard(), message);
        if(checkResult.equals(GameMessage.buildOK) ) {
            message.getPlayer().getGodCard().getBuildStrategy().build(model.getGameBoard(), message);
        }
        else{
            //TODO impementare reportError
            //message.getView().reportError(checkResult);
        }
    }

    private synchronized void endTurn(PlayerEndOfTurnChoice message){
        //TODO implementare reporError
        if(!model.isPlayerTurn(message.getPlayer())){

            //message.getView().reportError(gameMessage.wrongTurn);
            return;

            //TODO check vittoria giocatore e check sconfitta giocatore successivo
         }
    }

    /**
     * Invokes Controller's methods on the basis of message's subclass
     *
     * @param message oggetto-messaggio contentente le informazioni riguardanti lo spostamento
     */
    @Override
    public void update(Message message) {

        if(message instanceof PlayerMovementChoice){
            performMove((PlayerMovementChoice)message);
        }

        if (message instanceof PlayerBuildChoice){
            performBuild((PlayerBuildChoice)message);
        }

        if(message instanceof PlayerEndOfTurnChoice){
            endTurn((PlayerEndOfTurnChoice)message);
        }

    }


}
