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
            //message.getView().reportInfo(gameMessage.wrongTurn);
            return;
        }

        if(model.getTurnInfo().getTurnHasEnded()){
            //message.getView().reportInfo(gameMessage.turnAlreadyEnded);
            return;
        }

        //CHECK LOSE
        if(message.getPlayer().getGodCard().getLoseStrategy().movementLoss(model.getTurnInfo(), model.getGameBoard(), message.getPlayer(), message.getChosenWorker())) {
            model.notifyLoss(message.getPlayer());
            //TODO altro?
            return;

        //if the player hasn't lost
        }else {
            //EXECUTE MOVE CHECK
            checkResult = message.getPlayer().getGodCard().getMoveStrategy().checkMove(model.getTurnInfo(), model.getGameBoard(), message.getPlayer(), message.getChosenWorker(), message.getMovingTo());

            //if check ok, execute move and win
            if (checkResult.equals(GameMessage.moveOK)){

                //EXECUTE MOVE
                nextStep = message.getPlayer().getGodCard().getMoveStrategy().move(model.getTurnInfo(), model.getGameBoard(), message.getPlayer(), message.getChosenWorker(), message.getMovingTo());
                model.notifyNewBoardState(message.getPlayer());

                //EXECUTE WIN CHECK
                if (message.getPlayer().getGodCard().getWinStrategy().checkWin(message.getPlayer(), message.getChosenWorker())) {
                    model.notifyVictory(message.getPlayer());
                    //TODO altro?
                    endMatch();
                    return;
                }else{
                    //message.getView().reportInfo(nextStep);
                }

            //if check NOT ok, report error
            }else{
                //TODO implementare reportInfo
                System.out.println("message-error dalla check alla view");
                //message.getView().reportInfo(checkResult);

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
            //message.getView().reportInfo(gameMessage.wrongTurn);
            return;
        }

        if (model.getTurnInfo().getTurnHasEnded()) {
            //message.getView().reportInfo(gameMessage.turnAlreadyEnded);
            return;
        }

        //CHECK LOSE
        if(message.getPlayer().getGodCard().getLoseStrategy().buildingLoss(model.getTurnInfo(), model.getGameBoard(), message.getPlayer(), message.getChosenWorker())){
            model.notifyLoss(message.getPlayer());
            //TODO altro?
            return;

        //if player hasn't lost'
        }else{
            //EXECUTE BUILD CHECK
            checkResult = message.getPlayer().getGodCard().getBuildStrategy().checkBuild(model.getTurnInfo(), model.getGameBoard(), message.getPlayer(), message.getChosenWorker(), message.getBuildingInto(), message.getPieceType());

            //if build check ok
            if (checkResult.equals(GameMessage.buildOK)) {

                //EXECUTE BUILD
                nextStep = message.getPlayer().getGodCard().getBuildStrategy().build(model.getTurnInfo(), model.getGameBoard(), message.getPlayer(), message.getChosenWorker(), message.getBuildingInto(), message.getPieceType());
                model.notifyNewBoardState(message.getPlayer());
                //message.getView().reportInfo(nextStep);

            //if NOT build check ok
            } else {
                //TODO impementare reportInfo
                //message.getView().reportInfo(checkResult);
            }
        }
    }

    /**
     * Checks if it's player's turn, checks this player win conditions and next player lose conditions. Updates turn
     * @param message PlayerEndOfTurnChoice message
     */
    private synchronized void endTurn(PlayerEndOfTurnChoice message){
        //TODO implementare reportInfo
        if(!model.isPlayerTurn(message.getPlayer())){
            //message.getView().reportInfo(gameMessage.wrongTurn);
            return;
        }

        if(!model.getTurnInfo().getTurnCanEnd()){
            //message.getView().reportInfo(gameMessage.turnNotEnded);
            return;
        }

        //TODO stabilire cosa va qui
        model.updateTurn();
        //TODO altro?

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

        else if (message instanceof PlayerBuildChoice){
            performBuild((PlayerBuildChoice) message);
        }

        else if(message instanceof PlayerEndOfTurnChoice){
            endTurn((PlayerEndOfTurnChoice) message);
        }

    }


    private void startMatch(){}

    private void endMatch(){}

}
