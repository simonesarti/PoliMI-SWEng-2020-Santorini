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
     * Si assicura che sia il turno del player, e chiama la move() giusta in base alla
     * GodCard del player giocante
     *
     * @param message oggetto-messaggio contentente le informazioni riguardanti lo spostamento
     */
    private void performMove(PlayerMovementChoice message) {

        if(!match.isPlayerTurn(message.getPlayer())){
            //message.getView().reportError(gameMessage.wrongTurnMessage); //TODO il messaggio dovrà includere anche la view per mostrare il messaggio di errore in queste occasioni
            return;
        }

        //TODO DA AGGIUSTARE LA CHIAMATA DELLA STRATEGY DALLA GODCARD (bisogna ancora collegare le strategy alle carte)

        match.getCurrentPlayer().getGodCard().movestrategy.move(match.getGameBoard(), match.getCurrentPlayer().getWorker(message.getChosenWorker()), message.getMovingTo()[0], message.getMovingTo()[1]);

        match.updateTurn();

    }


    //TODO QUANDO VENGONO CHIAMATE LE DETERMINATE UPDATE? DIPENDE DAL MESSAGGIO
    /**
     * Si occupa di chiamare la performMove() del controller in seguito ad una notify(PlayerMovementChoice message) della view
     * @param message oggetto-messaggio contentente le informazioni riguardanti lo spostamento
     */
    @Override
    public void update(PlayerMovementChoice message) {performMove(message);}


}
