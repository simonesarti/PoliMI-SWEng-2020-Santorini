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
     * Checks that current player's colour is equals to current turn's colour
     * @param player
     * @return boolean
     */
    public boolean isPlayerTurn(Player player){ return player.getColour()== turn;}


    /**
     * returns current turn's colour
     * @return turn
     */
    public Colour getTurn(){ return turn;}

    public GameBoard getGameBoard(){return gameboard;}

    /**
     * updates turn (blue->grey, grey->white, white->blue)
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
     * returns current player
     *
     * @return current player
     */

    public Player getCurrentPlayer() {

        if (getTurn() == Colour.BLUE) {
            return bluePlayer;
        } else if (getTurn() == Colour.GREY) {
            return greyPlayer;
        } else return whitePlayer;
    }


}
