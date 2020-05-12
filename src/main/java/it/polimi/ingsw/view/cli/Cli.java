package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.client.ClientSideConnection;
import it.polimi.ingsw.messages.GameToPlayerMessages.Notify.NewBoardStateMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.Others.PossibleCardsMessage;
import it.polimi.ingsw.messages.PlayerInfo;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.CardChoice;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.StartingPositionChoice;
import it.polimi.ingsw.view.View;

import java.util.GregorianCalendar;
import java.util.Scanner;

public class Cli extends View {


    private Scanner stdin;


    public Cli(ClientSideConnection clientSideConnection) {

        super(clientSideConnection);
        stdin = new Scanner(System.in);


    }

    /**
     * shows new board-state on command line
     * @param message
     */
    public void showNewBoard(NewBoardStateMessage message){

        System.out.println("Mostro la board sulla Command Line");

    }

    /**
     * Creates a PlayerInfo message based on user's inputs
     * @return
     */
    @Override
    public PlayerInfo createPlayerInfo(){

        System.out.println("What's your nickname?");
        String nickname = stdin.nextLine();

        int day=0;
        int month=0;
        int year=0;
        String dayString;
        String monthString;
        String yearString;

        boolean validDate=false;
        do{

            System.out.println("Insert birthday day:");
            dayString = stdin.nextLine();

            System.out.println("Insert birthday month:");
            monthString = stdin.nextLine();

            System.out.println("Insert birthday year:");
            yearString = stdin.nextLine();


            String dateString = day +"-"+month+"-"+year;

            validDate=isDateValid(dateString,dayString,monthString,yearString);

            if(!validDate)System.out.println("Not valid, try again");


        }while(!validDate);

        day = Integer.parseInt(dayString);
        month = Integer.parseInt(monthString);
        year = Integer.parseInt(yearString);

        //need to create a new Calendar object with birthdayString data
        int numberOfPlayers = 0;
        String numberOfPlayersString;
        boolean validNumberOfPlayers=false;
        do{

            System.out.println("Insert number of players:");
            numberOfPlayersString = stdin.nextLine();
            if(isValidNumberOfPlayers(numberOfPlayersString)){
                validNumberOfPlayers = true;
            }


        }while(!validNumberOfPlayers);

        numberOfPlayers = Integer.parseInt(numberOfPlayersString);

        return (new PlayerInfo(nickname,new GregorianCalendar(year,month-1,day),numberOfPlayers));
    }

    /**
     * Creates a StartingPositionChoice message based on user's inputs
     * @return StartingPositionChoice message
     */
    @Override
    public StartingPositionChoice createStartingPositionChoice() {

        String pos;
        String delims = ",";
        String[] tokens;
        int pos1x = 0;
        int pos1y = 0;
        int pos2x = 0;
        int pos2y = 0;

        boolean validPos = false;
        do{
            try {
                System.out.println("Insert first worker position x,y :");
                pos = stdin.nextLine();

                if(!isPositionValid(pos)){
                    System.out.print("Not valid, try again");
                }else{
                    validPos=true;
                    tokens = pos.split(delims);
                    pos1x = Integer.parseInt(tokens[0]);
                    pos1y = Integer.parseInt(tokens[1]);
                }

            } catch (NumberFormatException e) {
                System.out.print("Not a number, try again");

            }
        }while(!validPos);

        validPos = false;
        do{
            try {
                System.out.println("Insert second worker position x,y :");
                pos = stdin.nextLine();

                if(!isPositionValid(pos)){
                    System.out.print("Not valid, try again");
                }else{
                    validPos=true;
                    tokens = pos.split(delims);
                    pos2x = Integer.parseInt(tokens[0]);
                    pos2y = Integer.parseInt(tokens[1]);
                }

            } catch (NumberFormatException e) {
                System.out.print("Not a number, try again");
            }
        }while(!validPos);


        return (new StartingPositionChoice(pos1x,pos1y,pos2x,pos2y));


    }

    /**
     * creates a CardChoice message based on user's inputs
     * @param message has a number of choices attribute and a list of possible Gods
     * @return
     */
    @Override
    public CardChoice createCardChoice(PossibleCardsMessage message) {

        ClientViewSupportFunctions cvsf = new ClientViewSupportFunctions();
        String[] chosenGods;
        String choice;
        boolean validChosenGods = false;

        if(message.getNumberOfChoices()>1){

            chosenGods = new String[message.getNumberOfChoices()];
            System.out.println("Scegli "+message.getNumberOfChoices()+" di questi dei: ");
            for(String s : message.getGods()){
                System.out.println(s);
            }


            do{
                for(int n=0 ; n<message.getNumberOfChoices();n++){
                    System.out.println("Choose a God: ");
                    choice = stdin.nextLine();
                    //correct format is first letter uppercase
                    chosenGods[n]= cvsf.nameToCorrectFormat(choice);
                }
                if(isChosenGodsValid(chosenGods, message.getNumberOfChoices(), message)){
                    validChosenGods=true;
                }

            }while(!validChosenGods);

            return(new CardChoice(chosenGods));
        }


        else if(message.getNumberOfChoices()==1){

            chosenGods = new String[1];

            System.out.println("Scegli uno di questi dei: ");
            for(String s : message.getGods()){
                System.out.println(s);
            }

            do{

                System.out.println("Choose a God: ");
                choice = stdin.nextLine();
                //correct format is first letter uppercase
                chosenGods[0]= cvsf.nameToCorrectFormat(choice);

                if(isChosenGodsValid(chosenGods, message.getNumberOfChoices(),message)){
                    validChosenGods=true;
                }

            }while(!validChosenGods);


            return(new CardChoice(chosenGods));
        }


        else{

            throw new IllegalArgumentException();
        }
    }


    @Override
    public void run() {

        try {

            while(getClientSideConnection().isActive()) {

                String inputLine = stdin.nextLine();
                //trasforma la stringa in un oggetto messaggio in base a cosa c'è scritto e poi chiama clientSideConn.asyncsend(messaggio)
                System.out.println("thread lettore cli ha letto una stringa");
            }

        } catch (Exception e) {

            getClientSideConnection().setActive(false);
            e.printStackTrace();

        } finally {

            stdin.close();

        }


    }
}
