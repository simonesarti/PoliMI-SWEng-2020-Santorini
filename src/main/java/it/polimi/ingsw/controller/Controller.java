package it.polimi.ingsw.controller;


import it.polimi.ingsw.messages.PlayerMovementChoice;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.observe.Observer;


public class Controller implements Observer<PlayerMovementChoice>{

    private Match match;

    /**
     * Costruttore della classe Controller
     * @param match
     */
    public Controller(Match match){

        super();
        this.match = match;
    }

    /**
     * Si assicura che sia il turno del player, che la mossa sia possibile e infine chiama la move() giusta in base alla
     * GodCard del player giocante
     *
     * @param message oggetto-messaggio contentente le informazioni riguardanti lo spostamento
     */
    private void performMove(PlayerMovementChoice message) {

        if(!match.isPlayerTurn(message.getPlayer())){
            //message.getView().reportError(gameMessage.wrongTurnMessage); //TODO il messaggio dovrà includere anche la view per mostrare il messaggio di errore in queste occasioni
            return;
        }

        //TODO qui fa un controllo per la basicmove e poi chiama la move generica della godcard. DA AGGIUSTARE
        if(!match.isFeasibleBasicMove( message.getMovingTo(), match.getCurrentPlayer().getWorker(message.getChosenWorker()) )  ){
            //message.getView().reportError(gameMessage.CannotMoveHereMessage);
            return;
        }

        //manca chiamare effettivamente la move (la cosa del to do scritto qua sotto)
        // TODO match.getPlayer().GodCard.move(message.getMovingTo(), message.getPlayer());
        match.updateTurn();


    }

    /**
     * Si occupa di chiamare la performMove() del controller in seguito ad una notify(PlayerMovementChoice message) della view
     * @param message oggetto-messaggio contentente le informazioni riguardanti lo spostamento
     */
    @Override
    public void update(PlayerMovementChoice message) {performMove(message);}


}
