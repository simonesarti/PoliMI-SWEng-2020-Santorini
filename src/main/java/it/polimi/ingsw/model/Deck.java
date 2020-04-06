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
            File godsDescriptionsFile=new File("C:\\Users\\simon\\Desktop\\gods.txt");
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

        for (GodCard godCard : deck) {
            if (divinityName.equals(godCard.getGodName())) {
                return godCard;
            }
        }
        throw new IllegalArgumentException("MESSAGE: No divinity with such a name was found in the deck");
    }

}
