package it.polimi.ingsw.model;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * GodCards Deck
 */
public class Deck{

    private List<GodCard> deck = new ArrayList<>();

    /**
     * Constructor. It scans Godcards' descriptions from a file
     */
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

    /**
     * returns the chosen GodCard
     * @param divinityName God's name
     * @return returns GodCard object
     */
    public GodCard chooseCardFromDeck(String divinityName){

        for (GodCard godCard : deck) {
            if (divinityName.equals(godCard.getGodName())) {
                return godCard;
            }
        }
        throw new IllegalArgumentException("MESSAGE: No divinity with such a name was found in the deck");
    }

}
