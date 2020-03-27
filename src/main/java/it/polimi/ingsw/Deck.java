package it.polimi.ingsw;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Deck{

    private List<GodCard> deck = new ArrayList<GodCard>();

    public Deck(){

        try{
            File godsDescriptionsFile=new File("gods.txt");
            Scanner fileReader=new Scanner(godsDescriptionsFile);

            while(fileReader.hasNextLine()){
                String fileLine=fileReader.nextLine();
                String[] godsData = fileLine.split(",");
                deck.add(new GodCard(godsData));
            }

        }catch(FileNotFoundException e){
            System.out.println("errore nell'apertura del file");
            e.printStackTrace();
        }

    }

    public GodCard chooseCardFromDeck(String divinityName) {

        int i;

        for (i=0; i<deck.size(); i++) {

            if (divinityName.equals(deck.get(i).getGodName())) {
                return deck.get(i);
            } else {
                throw new IllegalArgumentException("no divinity with such a name was found");
            }
        }
    }
}
