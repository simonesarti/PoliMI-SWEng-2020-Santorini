package it.polimi.ingsw.model;

public class Match {
    //TODO bisogna stare attenti alla gestione dei turni (i turni sono di tipo Colour). Come partire? forse mettere nel costruttore turn = coloreChepartePerPrimo?
    private Colour turn;
    private GameBoard gameboard;
    private Player bluePlayer;
    private Player greyPlayer;
    private Player whitePlayer;

    public Match(){

    }

    /**
     * Controlla che il Colour del turno corrente corrisponda al Colour del player
     * @param player
     * @return boolean
     */
    public boolean isPlayerTurn(Player player){ return player.getColour()== turn;}


    /**
     * Ritorna il Colour del turno
     * @return turn
     */
    public Colour getTurn(){ return turn;}

    public GameBoard getGameBoard(){return gameboard;}

    /**
     * Passa al turno successivo cambiando la variabile turn
     */
    public void updateTurn(){
        if(turn == Colour.BLUE){
            turn = Colour.GREY;
        }
        else if(turn == Colour.GREY){
            turn = Colour.WHITE;
        }
        else if(turn == Colour.WHITE){
            turn = Colour.BLUE;
        }
        else{
            //ERRORE, NON DOVREBBE ARRIVARE MAI QUI
        }

        return;
    }

    /**
     * Ritorna il player giocante durante il turno corrente
     *
     * @return player che sta giocando durante questo turno
     */

    public Player getCurrentPlayer() {

        if (getTurn() == Colour.BLUE) {
            return bluePlayer;
        } else if (getTurn() == Colour.GREY) {
            return greyPlayer;
        } else return whitePlayer;
    }


}
