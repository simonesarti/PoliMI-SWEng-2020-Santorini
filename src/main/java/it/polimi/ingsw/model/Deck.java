package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Deck{

    private final ArrayList<GodCard> deck = new ArrayList<>();

    public ArrayList<GodCard> getDeck() {
        return deck;
    }

    public void fill(){

        GodDescriptions godDescriptions=new GodDescriptions();
        ArrayList<String[]> descriptions=godDescriptions.getDescriptions();

        for(String[] description : descriptions){
            deck.add(new GodCard(description));
        }
    }

    /**
     * returns the chosen GodCard
     * @param divinityName God's name
     * @return returns GodCard object
     */
    public GodCard chooseCard(String divinityName){

        for (GodCard godCard : deck) {
            if (divinityName.equals(godCard.getGodName())) {
                return godCard;
            }
        }
        throw new IllegalArgumentException("GodCard not found in deck");
    }

    public ArrayList<String> getPresentGods(int numberOfPlayers){

        ArrayList<String> names=new ArrayList<>();

        for(GodCard godCard : deck){

            if(!(numberOfPlayers==3 && !godCard.canBeUsedIn3())){
                String name=godCard.getGodName();
                names.add(name);
            }

        }
        return names;
    }


}
