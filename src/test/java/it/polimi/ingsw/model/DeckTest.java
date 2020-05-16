package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    Deck deck=new Deck();
    GodDescriptions godDescriptions=new GodDescriptions();
    ArrayList<String[]> descriptions=godDescriptions.getDescriptions();

    @Test
    void fill(){

        deck.fill();

        for(int i=0;i<deck.getDeck().size();i++){
            assertEquals(descriptions.get(i)[0],deck.getDeck().get(i).getGodName());
        }
    }

    @Test
    void FindCard(){
        deck.fill();

        for(int i=0;i<deck.getDeck().size();i++){
            assertEquals(deck.getDeck().get(i),deck.findCard(descriptions.get(i)[0]));
        }

        assertThrows(IllegalArgumentException.class,()->deck.findCard("Others"));
    }

    @Test
    void getAllNames(){
        deck.fill();
        ArrayList<String> names=new ArrayList<>();
        for (String[] description : descriptions) {
            names.add(description[0]);
        }

        assertArrayEquals(names.toArray(),deck.getGameGods().toArray());
        assertArrayEquals(names.toArray(),deck.getPresentGods(3).toArray());
    }


}