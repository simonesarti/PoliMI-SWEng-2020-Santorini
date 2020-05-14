package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.client.ClientSideConnection;
import it.polimi.ingsw.messages.GameToPlayerMessages.Notify.NewBoardStateMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.Others.PossibleCardsMessage;
import it.polimi.ingsw.messages.PlayerInfo;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.CardChoice;
import it.polimi.ingsw.messages.PlayerToGameMessages.DataMessages.StartingPositionChoice;
import it.polimi.ingsw.view.ClientViewSupportFunctions;
import it.polimi.ingsw.view.View;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Cli extends View {


    private Scanner stdin;
    private ClientViewSupportFunctions sf;


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
        //TODO da fare questa funzione

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

            validDate = sf.isDateValid(dateString,dayString,monthString,yearString);

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

            validNumberOfPlayers = sf.isValidNumberOfPlayers(numberOfPlayersString);
            if(!validNumberOfPlayers)System.out.println("Not valid, try again");


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
        ArrayList<Integer> positions = new ArrayList<>();
        boolean validPos = false;


        for(int i=0; i<2;i++){

            do{
                try {
                    System.out.println("Insert worker"+(i+1)+" position x,y :");
                    pos = stdin.nextLine();

                    if(!sf.isPositionValid(pos)){
                        System.out.print("Not valid, try again");
                    }else{
                        validPos=true;
                        tokens = pos.split(delims);
                        positions.add(Integer.parseInt(tokens[0]));
                        positions.add(Integer.parseInt(tokens[1]));
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Not a number, try again");

                }
            }while(!validPos);
            validPos = false;
        }

        return (new StartingPositionChoice(positions.get(0),positions.get(1),positions.get(2),positions.get(3)));


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

        if(message.getNumberOfChoices()>0){

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
                if(sf.isChosenGodsValid(chosenGods, message.getNumberOfChoices(), message)){
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
