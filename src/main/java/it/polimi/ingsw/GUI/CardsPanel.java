package it.polimi.ingsw.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CardsPanel extends JPanel {

    PlayerPanel player1;
    PlayerPanel player2;
    PlayerPanel player3;

    public CardsPanel(ArrayList<String> cards, ArrayList<String> nicknames){

        setLayout(new GridLayout(3,1));
        setCardPanels(cards,nicknames);
        setVisible(true);
    }

    private void setCardPanels(ArrayList<String> cards, ArrayList<String> nicknames){
        player1=new PlayerPanel(cards.get(0),nicknames.get(0));
        player2=new PlayerPanel(cards.get(1),nicknames.get(1));
        if(cards.size()==3){
            player3=new PlayerPanel(cards.get(2),nicknames.get(2));
        }else{
            player3=new PlayerPanel("card not used in this match","nickname not used");
        }

        add(player1);
        add(player2);
        add(player3);

    }
}
