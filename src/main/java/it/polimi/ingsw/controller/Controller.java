package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.PlayerMovementChoice;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.observe.Observer;

public class Controller implements Observer<PlayerMovementChoice> {

    private Match match;

    public Controller(Match match){

        super();
        this.match = match;
    }

    private void performMove(PlayerMovementChoice message) {

        if(!match.isPlayerTurn(message.getPlayer())){
            //message.getView().reportError(gameMessage.wrongTurnMessage); //TODO il messaggio dovrà includere anche la view per mostrare il messaggio di errore in queste occasioni
            return;
        }
        if(!match.isFeasibleBasicMove( message.getMovingTo(), match.getCurrentPlayer().getWorker(message.getChosenWorker()) )  ){
            //message.getView().reportError(gameMessage.CannotMoveHereMessage);
            return;
        }

        //manca chiamare effettivamente la move (la cosa del to do scritto qua sotto)
        // TODO match.getPlayer().GodCard.move(message.getMovingTo(), message.getPlayer());
        match.updateTurn();


    }

    @Override
    public void update(PlayerMovementChoice message) {performMove(message);}


}
