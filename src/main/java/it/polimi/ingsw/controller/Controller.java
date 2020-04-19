package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.messages.PlayerToGameMessages.PlayerBuildChoice;
import it.polimi.ingsw.messages.PlayerToGameMessages.PlayerEndOfTurnChoice;
import it.polimi.ingsw.messages.PlayerToGameMessages.PlayerMessage;
import it.polimi.ingsw.messages.PlayerToGameMessages.PlayerMovementChoice;
import it.polimi.ingsw.model.Model;
import it.polimi.ingsw.observe.Observer;

public class Controller implements Observer<PlayerMessage>{

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
     * Checks if it's the player's turn and calls the player's GodCard's MoveStrategy methods;
     *
     * @param message oggetto-messaggio contentente le informazioni riguardanti lo spostamento
     */
    private synchronized void performMove(PlayerMovementChoice message) {

        String checkResult;
        String nextStep;

        //TODO implementare reportError
        if(!model.isPlayerTurn(message.getPlayer())){
            //message.getView().reportError(gameMessage.wrongTurn);
            return;
        }

        if(model.getTurnInfo().getTurnHasEnded()){
            //message.getView().reportError(gameMessage.turnAlreadyEnded);
            return;
        }

        //CHECK LOSE
        if(message.getPlayer().getGodCard().getLoseStrategy().movementLoss(model.getTurnInfo(), model.getGameBoard(), message.getPlayer(), message.getChosenWorker())) {
            //TODO cosa fare se perde
            return;

        //if the player hasn't lost
        }else {
            //EXECUTE MOVE CHECK
            checkResult = message.getPlayer().getGodCard().getMoveStrategy().checkMove(model.getTurnInfo(), model.getGameBoard(), message.getPlayer(), message.getChosenWorker(), message.getMovingTo());

            //if check ok, execute move and win
            if (checkResult.equals(GameMessage.moveOK)){

                //EXECUTE MOVE
                nextStep = message.getPlayer().getGodCard().getMoveStrategy().move(model.getTurnInfo(), model.getGameBoard(), message.getPlayer(), message.getChosenWorker(), message.getMovingTo());

                //EXECUTE WIN CHECK
                if (message.getPlayer().getGodCard().getWinStrategy().checkWin(message.getPlayer(), message.getChosenWorker())) {
                    endMatch();
                }
            //if check NOT ok, report error
            }else{
                //TODO implementare reportError
                //message.getView().reportError(checkResult);
                return;
            }
        }
    }

    /**
     *
     * Checks if it's the player's turn and calls the player's GodCard's BuildStrategy
     *
     * @param message messaggio di tipo PlayerBuildChoice
     */
    private synchronized void performBuild(PlayerBuildChoice message) {

        String checkResult;
        String nextStep;

        //TODO implementare reporError
        if (!model.isPlayerTurn(message.getPlayer())) {
            //message.getView().reportError(gameMessage.wrongTurn);
            return;
        }

        if (model.getTurnInfo().getTurnHasEnded()) {
            //message.getView().reportError(gameMessage.turnAlreadyEnded);
            return;
        }

        //CHECK LOSE
        if(message.getPlayer().getGodCard().getLoseStrategy().buildingLoss(model.getTurnInfo(), model.getGameBoard(), message.getPlayer(), message.getChosenWorker())){
        //TODO cosa fare se perde
            return;

        //if player hasn't lost'
        }else{
            //EXECUTE BUILD CHECK
            checkResult = message.getPlayer().getGodCard().getBuildStrategy().checkBuild(model.getTurnInfo(), model.getGameBoard(), message.getPlayer(), message.getChosenWorker(), message.getBuildingInto(), message.getPieceType());

            //if build check ok
            if (checkResult.equals(GameMessage.buildOK)) {

                //EXECUTE BUILD
                nextStep = message.getPlayer().getGodCard().getBuildStrategy().build(model.getTurnInfo(), model.getGameBoard(), message.getPlayer(), message.getChosenWorker(), message.getBuildingInto(), message.getPieceType());

                //if NOT build check ok
            } else {
                //TODO impementare reportError
                //message.getView().reportError(checkResult);
                return;
            }
        }
    }

    /**
     * Checks if it's player's turn, checks this player win conditions and next player lose conditions. Updates turn
     * @param message PlayerEndOfTurnChoice message
     */
    private synchronized void endTurn(PlayerEndOfTurnChoice message){
        //TODO implementare reporError
        if(!model.isPlayerTurn(message.getPlayer())){
            //message.getView().reportError(gameMessage.wrongTurn);
            return;
        }

        if(!model.getTurnInfo().getTurnCanEnd()){
            //message.getView().reportError(gameMessage.turnNotEnded);
            return;
        }

        //TODO stabilire cosa va qui
        model.updateTurn();

        // TODO check sconfitta giocatore successivo con eventule rimozione dal gioco

    }

    /**
     * Invokes Controller's methods on the basis of message's subclass
     *
     * @param message PlayerMessage
     */
    @Override
    public void update(PlayerMessage message) {

        if(message instanceof PlayerMovementChoice){
            performMove((PlayerMovementChoice) message);
        }

        if (message instanceof PlayerBuildChoice){
            performBuild((PlayerBuildChoice) message);
        }

        if(message instanceof PlayerEndOfTurnChoice){
            endTurn((PlayerEndOfTurnChoice) message);
        }

    }


    private void startMatch(){}

    private void endMatch(){}

}
