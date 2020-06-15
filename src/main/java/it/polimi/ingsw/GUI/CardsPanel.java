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
        setPlayerPanels(message.getNicknames(),message.getColours(),message.getGodNames(),message.getDescriptions());
    }

    private void setPlayerPanels(ArrayList<String> nicknames, ArrayList<Colour> colours, ArrayList<String> godNames ,ArrayList<String> descriptions){
        player1=new PlayerPanel(nicknames.get(0),convertToColor(colours.get(0)),godNames.get(0),descriptions.get(0));
        player2=new PlayerPanel(nicknames.get(1),convertToColor(colours.get(1)),godNames.get(1),descriptions.get(1));
        if(nicknames.size()==3){
            player3=new PlayerPanel(nicknames.get(2),convertToColor(colours.get(2)),godNames.get(2),descriptions.get(2));
        }else{
            player3=new PlayerPanel("not present in match",Color.DARK_GRAY,"X","card not used in this match");
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
