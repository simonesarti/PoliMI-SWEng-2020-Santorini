package it.polimi.ingsw.view;

import it.polimi.ingsw.client.ClientSideConnection;
import it.polimi.ingsw.messages.GameToPlayerMessages.Others.*;
import it.polimi.ingsw.messages.GameToPlayerMessages.Notify.NewBoardStateMessage;
import it.polimi.ingsw.messages.PlayerInfo;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.CardChoice;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.StartingPositionChoice;
import it.polimi.ingsw.observe.Observer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public abstract class View implements Observer<Object>,Runnable {




    private final ClientSideConnection clientSideConnection;
    private boolean canStart;

    public View(ClientSideConnection clientSideConnection){

        this.setCanStart(false);
        this.clientSideConnection = clientSideConnection;
    }

    abstract public void showNewBoard(NewBoardStateMessage message);
    abstract public PlayerInfo createPlayerInfo();
    abstract public StartingPositionChoice createStartingPositionChoice();
    abstract public CardChoice createCardChoice(PossibleCardsMessage message);



    @Override
    public void update(Object message){

        if(message instanceof NewBoardStateMessage){
            System.out.println("NewBoardStateMessage message arrived to client!");
            showNewBoard((NewBoardStateMessage) message);
        }

        else if (message instanceof InfoMessage){

            System.out.println("Infomessage arrived to view, here it is: "+((InfoMessage) message).getInfo());

            if(((InfoMessage) message).getInfo().equals(GameMessage.welcome)){

                clientSideConnection.asyncSend(createPlayerInfo());
            }
        }
        else if(message instanceof StartingPositionRequestMessage){

            clientSideConnection.asyncSend(createStartingPositionChoice());
        }

        else if(message instanceof PossibleCardsMessage) {

            clientSideConnection.asyncSend(createCardChoice((PossibleCardsMessage) message));


        }
        else if(message instanceof GameStartMessage) {

            //TODO VA MESSO A FINE FASE PREPARAZIONE, PER ORA LO METTO QUI, forse è giusto
            setCanStart(true);
            System.out.println("Ora ho messo canStart a true, sono nella update della View");
        }

        else {

            throw new IllegalArgumentException();
        }


    }






    /**
     * checks if position is inside of the borders
     * @param pos
     * @return
     */
    public boolean isPositionValid(String pos){

        String delims = ",";
        String[] tokens = pos.split(delims);

        for(String s : tokens){
            try{
                Integer.parseInt(s);
            }catch(NumberFormatException e) {
                return false;
            }
        }


        return (Integer.parseInt(tokens[0]) >= 0) && (Integer.parseInt(tokens[0]) <= 4) && (Integer.parseInt(tokens[1]) >= 0) && (Integer.parseInt(tokens[1]) <= 4);

    }

    /**
     * Checks if date is a valid date format and if integers are numbers
     * @param date
     * @param dayString
     * @param monthString
     * @param yearString
     * @return
     */
    public boolean isDateValid(String date, String dayString, String monthString, String yearString){

        String DATE_FORMAT = "dd-MM-yyyy";
        int day=0;
        int month=0;
        int year=0;

        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        try{

            day = Integer.parseInt(dayString);
            month = Integer.parseInt(monthString);
            year = Integer.parseInt(yearString);

        }catch(NumberFormatException e){
            return false;
        }
        return true;

    }

    //TODO BISOGNA TESTARE ISVALIDNUMBEROFPLAYERS()
    /**
     * Checks if String is a number and if it's 2||3
     * @param string
     * @return
     */
    public boolean isValidNumberOfPlayers(String string){

        int number;
        try{

           number = Integer.parseInt(string);
        }
        catch(NumberFormatException e){

            return false;

        }
        if(number==2 || number ==3)return true;

        else return false;

    }

    /**
     * all chosen gods must be different and every god must be in the message's gods-arraylist
     * @param chosenGods Godcards chosen by the user
     * @param numberOfChoices number of godcards
     * @return boolean
     */
    public boolean isChosenGodsValid(String[] chosenGods, int numberOfChoices, PossibleCardsMessage message){

        //all gods must be different
        for(int i=0; i<numberOfChoices-1; i++){
            for(int k=i+1; k<numberOfChoices; k++){
                if(chosenGods[i].equals(chosenGods[k])){
                    return false;
                }
            }

        }


        //every god must be in the message's arraylist
        boolean trovato = false;

        for(int i=0; i<numberOfChoices; i++){

            for(String god : message.getGods()){
                if(chosenGods[i].equals(god)){

                    trovato=true;
                    break;
                }
            }
            if(!trovato) return false;

            trovato = false;

        }


        return true;
    }


    public synchronized boolean getCanStart() {
        return canStart;
    }

    public synchronized void setCanStart(boolean canStart) {
        this.canStart = canStart;
    }

    public ClientSideConnection getClientSideConnection() {
        return clientSideConnection;
    }


}
