package it.polimi.ingsw.GUI;

import javax.swing.*;
import java.awt.*;

public enum Images {

    SANTORINI_LOGO("src\\main\\java\\it\\polimi\\ingsw\\GUI\\graphicResources\\backgounds\\santorini-logo.png"),
    INITIAL_BACKGROUND("src\\main\\java\\it\\polimi\\ingsw\\GUI\\graphicResources\\backgounds\\Odyssey-Olympus.png"),
    GAMEBOARD("src\\main\\java\\it\\polimi\\ingsw\\GUI\\graphicResources\\backgounds\\SantoriniBoard.png"),
    GAME_ICON("src\\main\\java\\it\\polimi\\ingsw\\GUI\\graphicResources\\backgounds\\om_gloryIcon.png"),
    MOVE_BUTTON_ICON("src\\main\\java\\it\\polimi\\ingsw\\GUI\\graphicResources\\sprite\\move_button.png"),
    BUILD_BUTTON_ICON("src\\main\\java\\it\\polimi\\ingsw\\GUI\\graphicResources\\sprite\\build_button.png"),
    QUIT_BUTTON_ICON("src\\main\\java\\it\\polimi\\ingsw\\GUI\\graphicResources\\sprite\\quit_button.png"),
    PRESSED_QUIT_BUTTON_ICON("src\\main\\java\\it\\polimi\\ingsw\\GUI\\graphicResources\\sprite\\pressed_quit_button.png"),
    END_TURN_BUTTON_ICON("src\\main\\java\\it\\polimi\\ingsw\\GUI\\graphicResources\\sprite\\end_turn_button.png"),
    PRESSED_END_TURN_BUTTON_ICON("src\\main\\java\\it\\polimi\\ingsw\\GUI\\graphicResources\\sprite\\pressed_end_turn_button.png");



    private final String path;

    Images(String path){
        this.path=path;
    }

    public String getPath(){return path;}

    public static Image getImage(Images resource){

        Toolkit t=Toolkit.getDefaultToolkit();
        return t.getImage(resource.getPath());
    }

    public static ImageIcon getIcon(Images resource){
        return new ImageIcon(resource.getPath());
    }


}
