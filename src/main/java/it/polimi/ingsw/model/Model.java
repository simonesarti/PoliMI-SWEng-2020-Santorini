package it.polimi.ingsw.model;

public class Model {
    //TODO bisogna stare attenti alla gestione dei turni (i turni sono di tipo Colour). Come partire? forse mettere nel costruttore turn = coloreChepartePerPrimo?

    private GameBoard gameboard;
    private Colour turn;
    private int numberOfPlayers;
    private Colour eliminated;

    public Model(int numberOfPlayers){
        gameboard = new GameBoard();
        turn=Colour.WHITE;
        this.numberOfPlayers=numberOfPlayers;

        if(this.numberOfPlayers==2){
            eliminated=Colour.GREY;
        }
    }

    public GameBoard getGameBoard(){return gameboard;}

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

    /**
     * updates the turn based on the order of the player
    */
    public void updateTurn() {

        switch (eliminated){
            case GREY:
                if(turn==Colour.WHITE){
                    turn=Colour.BLUE;
                }else{
                    turn=Colour.WHITE;
                }
                break;
            case WHITE:
                if(turn==Colour.BLUE){
                    turn=Colour.GREY;
                }else{
                    turn=Colour.BLUE;
                }
                break;
            case BLUE:
                if(turn==Colour.WHITE){
                    turn=Colour.GREY;
                }else{
                    turn=Colour.WHITE;
                }
                break;
            default:
                if(turn==Colour.WHITE){
                    turn=Colour.BLUE;
                }else if(turn==Colour.BLUE){
                    turn=Colour.GREY;
                }else {
                    turn = Colour.WHITE;
                }
                break;
        }
    }

    public boolean isEliminated(Colour colour){
        return eliminated==colour;
    }
}
