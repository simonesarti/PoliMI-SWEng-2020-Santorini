package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.messages.PlayerToGameMessages.*;
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

        //eliminated player can't execute this command
        if(model.isEliminated(message.getPlayer().getColour())){
            message.getVirtualView().reportInfo(new InfoMessage(GameMessage.eliminated));
            return;
        }

        if(!model.isPlayerTurn(message.getPlayer())){
            message.getVirtualView().reportInfo(new InfoMessage(GameMessage.wrongTurn));
            return;
        }

        if(model.getTurnInfo().getTurnHasEnded()){
            message.getVirtualView().reportInfo(new InfoMessage(GameMessage.turnAlreadyEnded));
            //DEBUG
            System.out.println("turno già completato, non puoi");
            return;
        }

        //CHECK LOSE
        if(message.getPlayer().getGodCard().getLoseStrategy().movementLoss(model.getTurnInfo(), model.getGameBoard(), message.getPlayer(), message.getChosenWorker())) {
            model.notifyNewBoardState();
            model.notifyLoss(message.getPlayer());
            
            //TODO altro?

            //DEBUG:
            System.out.println("sconfitta");
            return;

        //if the player hasn't lost
        }else {
            //EXECUTE MOVE CHECK
            checkResult = message.getPlayer().getGodCard().getMoveStrategy().checkMove(model.getTurnInfo(), model.getGameBoard(), message.getPlayer(), message.getChosenWorker(), message.getMovingTo());

            //if check ok, execute move and win
            if (checkResult.equals(GameMessage.moveOK)){

                //EXECUTE MOVE
                nextStep = message.getPlayer().getGodCard().getMoveStrategy().move(model.getTurnInfo(), model.getGameBoard(), message.getPlayer(), message.getChosenWorker(), message.getMovingTo());
                model.notifyNewBoardState();

                //EXECUTE WIN CHECK
                if (message.getPlayer().getGodCard().getWinStrategy().checkWin(message.getPlayer(), message.getChosenWorker())) {
                    model.notifyNewBoardState();
                    model.notifyVictory(message.getPlayer());
                    //TODO altro?

                    //DEBUG:
                    System.out.println("vittoria");

                    endMatch();
                    return;
                }else{
                    message.getVirtualView().reportInfo(new InfoMessage(nextStep));

                    //DEBUG
                    System.out.println(nextStep);
                }

            //if check NOT ok, report error
            }else{

                message.getVirtualView().reportInfo(new InfoMessage(checkResult));

                //DEBUG
                System.out.println("errore nella check move "+checkResult);

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

        //eliminated player can't execute this command
        if(model.isEliminated(message.getPlayer().getColour())){
            message.getVirtualView().reportInfo(new InfoMessage(GameMessage.eliminated));
            return;
        }

        if (!model.isPlayerTurn(message.getPlayer())) {
            message.getVirtualView().reportInfo(new InfoMessage(GameMessage.wrongTurn));
            return;
        }

        if (model.getTurnInfo().getTurnHasEnded()) {
            message.getVirtualView().reportInfo(new InfoMessage(GameMessage.turnAlreadyEnded));

            //DEBUG
            System.out.println("turno già finito, non puoi");
            return;
        }

        //CHECK LOSE
        if(message.getPlayer().getGodCard().getLoseStrategy().buildingLoss(model.getTurnInfo(), model.getGameBoard(), message.getPlayer(), message.getChosenWorker())){
            model.notifyNewBoardState();
            model.notifyLoss(message.getPlayer());
            //TODO altro?

            //DEBUG:
            System.out.println("sconfitta");

            return;

        //if player hasn't lost'
        }else{
            //EXECUTE BUILD CHECK
            checkResult = message.getPlayer().getGodCard().getBuildStrategy().checkBuild(model.getTurnInfo(), model.getGameBoard(), message.getPlayer(), message.getChosenWorker(), message.getBuildingInto(), message.getPieceType());

            //if build check ok
            if (checkResult.equals(GameMessage.buildOK)) {

                //EXECUTE BUILD
                nextStep = message.getPlayer().getGodCard().getBuildStrategy().build(model.getTurnInfo(), model.getGameBoard(), message.getPlayer(), message.getChosenWorker(), message.getBuildingInto(), message.getPieceType());
                model.notifyNewBoardState();
                message.getVirtualView().reportInfo(new InfoMessage(nextStep));
                //TODO elimina questa println sotto

                //DEBUG:
                System.out.println("Ho eseguito una build correttamente");

            //if NOT build check ok
            } else {

                message.getVirtualView().reportInfo(new InfoMessage(checkResult));

                //DEBUG
                System.out.println("errore nella check build "+checkResult);
            }
        }
    }

    //TODO
    /**
     * Checks if it's player's turn, checks this player win conditions and next player lose conditions. Updates turn
     * @param message PlayerEndOfTurnChoice message
     */
    private synchronized void endTurn(PlayerEndOfTurnChoice message){

        //eliminated player can't execute this command
        if(model.isEliminated(message.getPlayer().getColour())){
            message.getVirtualView().reportInfo(new InfoMessage(GameMessage.eliminated));
            return;
        }

        if(!model.isPlayerTurn(message.getPlayer())){
            message.getVirtualView().reportInfo(new InfoMessage(GameMessage.wrongTurn));
            return;
        }

        if(!model.getTurnInfo().getTurnCanEnd()){

            //DEBUG
            System.out.println("turno incompleto");

            message.getVirtualView().reportInfo(new InfoMessage(GameMessage.turnNotEnded));
            return;
        }

        //DEBUG
        System.out.println("turno completato con successo");

        model.updateTurn();
        //TODO altro?

    }

    //TODO
    private synchronized void quitGame(PlayerQuitChoice message){

        //player can't quit if he isn't eliminated
        if(!model.isEliminated(message.getPlayer().getColour())){
            message.getVirtualView().reportInfo(new InfoMessage(GameMessage.notEliminated));
            return;
        }

        model.notifyQuit(message.getPlayer());
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

        else if(message instanceof PlayerQuitChoice){
            quitGame((PlayerQuitChoice)message);

        }

    }

    private void startMatch(){}

    private void endMatch(){}

}
