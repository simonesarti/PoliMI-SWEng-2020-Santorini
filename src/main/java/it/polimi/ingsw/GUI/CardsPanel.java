package it.polimi.ingsw.GUI;

import it.polimi.ingsw.messages.GameToPlayerMessages.Notify.GameStartMessage;
import it.polimi.ingsw.model.Colour;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CardsPanel extends JPanel {

    PlayerPanel player1;
    PlayerPanel player2;
    PlayerPanel player3;

    public CardsPanel(GameStartMessage message){

        setLayout(new GridLayout(3,1,0,0));
        setPlayerPanels(message.getDescriptions(),message.getNicknames(),message.getColours());
    }

    private void setPlayerPanels(ArrayList<String> cards, ArrayList<String> nicknames, ArrayList<Colour> colours){
        player1=new PlayerPanel(cards.get(0),nicknames.get(0),convertToColor(colours.get(0)));
        player2=new PlayerPanel(cards.get(1),nicknames.get(1),convertToColor(colours.get(1)));
        if(cards.size()==3){
            player3=new PlayerPanel(cards.get(2),nicknames.get(2),convertToColor(colours.get(2)));
        }else{
            player3=new PlayerPanel("card not used in this match","not present in match",Color.DARK_GRAY);
        }

        add(player1);
        add(player2);
        add(player3);

    }

    private Color convertToColor(Colour colour){

        return switch (colour) {
            case RED -> Color.RED;
            case BLUE -> Color.BLUE;
            case PURPLE -> new Color(255, 0, 255);
        };
    }
}
