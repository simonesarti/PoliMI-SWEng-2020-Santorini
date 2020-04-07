package it.polimi.ingsw.controller;


import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.PlayerBuildChoice;
import it.polimi.ingsw.messages.PlayerMovementChoice;
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
    private void performMove(PlayerMovementChoice message) {

        if(!model.isPlayerTurn(message.getPlayer())){
            //message.getView().reportError(gameMessage.wrongTurnMessage); //TODO il messaggio dovrà includere anche la view per mostrare il messaggio di errore in queste occasioni
            return;
        }

        if( model.getCurrentPlayer().getGodCard().getMoveStrategy().checkMove(model.getGameBoard(), message) ) {

            model.getCurrentPlayer().getGodCard().getMoveStrategy().move(model.getGameBoard(), message);
        }
        else{
            //TODO manda messaggio di errore a view?
        }

        return;
    }


    /**
     *
     * checks if it's the player's turn and calls the player's GodCard's BuildStrategy
     *
     * @param message messaggio di tipo PlayerBuildChoice
     */
    public void  performBuild(PlayerBuildChoice message){

        if(!model.isPlayerTurn(message.getPlayer())){
            //message.getView().reportError(gameMessage.wrongTurnMessage); //TODO il messaggio dovrà includere anche la view per mostrare il messaggio di errore in queste occasioni
            return;
        }

        if( model.getCurrentPlayer().getGodCard().getBuildStrategy().checkBuild(model.getGameBoard(), message) ) {
            model.getCurrentPlayer().getGodCard().getBuildStrategy().build(model.getGameBoard(), message);
        }
        else{
            //TODO manda messaggio di errore a view?
        }

        model.updateTurn();
        return;
    }


    /**
     * Si occupa di chiamare i metodi del controller discriminando in base al sottotipo di Message messaggio
     * in seguito ad una notify(Message message) della view
     * @param message oggetto-messaggio contentente le informazioni riguardanti lo spostamento
     */
    @Override
    public void update(Message message) {

        if(message instanceof PlayerMovementChoice){
            performMove((PlayerMovementChoice)message);
        }

        if (message instanceof PlayerBuildChoice){
            performBuild( (PlayerBuildChoice)message );
        }

    }


}
