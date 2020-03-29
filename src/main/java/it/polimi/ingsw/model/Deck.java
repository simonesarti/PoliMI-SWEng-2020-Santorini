package it.polimi.ingsw.model;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Deck{

    private List<GodCard> deck = new ArrayList<>();

    public Deck(){

        try{
            File godsDescriptionsFile=new File("C:\\Users\\simon\\Desktop\\godS.txt");
            Scanner fileLineReader=new Scanner(godsDescriptionsFile);

            while(fileLineReader.hasNextLine()){
                String fileLine = fileLineReader.nextLine();
                String[] godsData = fileLine.split(";");
                deck.add(new GodCard(godsData));
            }
        }catch(FileNotFoundException e){
            System.err.println("MESSAGE: An error occurred while opening the file");
            e.printStackTrace();
        }

    }


    public GodCard chooseCardFromDeck(String divinityName){

        int i;

        for (i = 0; i < deck.size(); i++) {
            if (divinityName.equals(deck.get(i).getGodName())) {
                return deck.get(i);
            }
        }
        throw new IllegalArgumentException("MESSAGE: No divinity with such a name was found in the deck");
    }

}
