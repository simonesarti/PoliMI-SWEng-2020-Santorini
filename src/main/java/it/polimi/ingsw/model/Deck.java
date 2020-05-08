package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * GodCards Deck
 */
public class Deck{

    private ArrayList<GodCard> deck = new ArrayList<>();

    /**
     * Constructor. It scans Godcards' descriptions from a file
     */
    public void fill(){

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
        throw new IllegalArgumentException("GodCard not found in deck");
    }

    public void removeCard(GodCard godCard){
        deck.remove(godCard);
    }

    public int size(){
        return deck.size();
    }

    public GodCard get(int index){
        return deck.get(index);
    }

    public void remove(int index){
        deck.remove(index);
    }

    public void add(GodCard godCard){
        deck.add(godCard);
    }

}
