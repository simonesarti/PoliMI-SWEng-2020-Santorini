package it.polimi.ingsw.GUI;

import javax.swing.*;
import java.awt.*;

public enum Images {

    SANTORINI_LOGO("./src/main/java/it/polimi/ingsw/GUI/graphicResources/backgounds/santorini-logo.png"),
    INITIAL_BACKGROUND("./src/main/java/it/polimi/ingsw/GUI/graphicResources/backgounds/Odyssey-Olympus.png"),
    GAMEBOARD("././src/main/java/it/polimi/ingsw/GUI/graphicResources/backgounds/SantoriniBoard.png"),
    GAME_ICON("./src/main/java/it/polimi/ingsw/GUI/graphicResources/backgounds/om_gloryIcon.png"),
    MOVE_BUTTON_ICON("./src/main/java/it/polimi/ingsw/GUI/graphicResources/sprite/move_button.png"),
    BUILD_BUTTON_ICON("./src/main/java/it/polimi/ingsw/GUI/graphicResources/sprite/build_button.png"),
    QUIT_BUTTON_ICON("./src/main/java/it/polimi/ingsw/GUI/graphicResources/sprite/quit_button.png"),
    PRESSED_QUIT_BUTTON_ICON("./src/main/java/it/polimi/ingsw/GUI/graphicResources/sprite/pressed_quit_button.png"),
    END_TURN_BUTTON_ICON("./src/main/java/it/polimi/ingsw/GUI/graphicResources/sprite/end_turn_button.png"),
    PRESSED_END_TURN_BUTTON_ICON("./src/main/java/it/polimi/ingsw/GUI/graphicResources/sprite/pressed_end_turn_button.png"),

    APOLLO("./src/main/java/it/polimi/ingsw/GUI/graphicResources/godCards/Apollo"),
    ARTEMIS("./src/main/java/it/polimi/ingsw/GUI/graphicResources/godCards/Artemis"),
    ATHENA("./src/main/java/it/polimi/ingsw/GUI/graphicResources/godCards/Athena"),
    ATLAS("./src/main/java/it/polimi/ingsw/GUI/graphicResources/godCards/Atlas"),
    DEMETER("./src/main/java/it/polimi/ingsw/GUI/graphicResources/godCards/Demeter"),
    HEPHAESTUS("./src/main/java/it/polimi/ingsw/GUI/graphicResources/godCards/Hephaestus"),
    MINOTAUR("./src/main/java/it/polimi/ingsw/GUI/graphicResources/godCards/Minotaur"),
    PAN("./src/main/java/it/polimi/ingsw/GUI/graphicResources/godCards/Pan"),
    PROMETHEUS("./src/main/java/it/polimi/ingsw/GUI/graphicResources/godCards/Prometheus"),
    EMPTY_CARD("./src/main/java/it/polimi/ingsw/GUI/graphicResources/godCards/EmptyCard");




    private final String path;

    Images(String path){
        this.path=path;
    }

    public String getPath(){return path;}

    public static Image getImage(Images resource){
        return new ImageIcon(resource.getPath()).getImage();
    }

    public static ImageIcon getIcon(Images resource){
        return new ImageIcon(resource.getPath());
    }


}
