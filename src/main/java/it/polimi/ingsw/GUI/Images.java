package it.polimi.ingsw.GUI;

import javax.swing.*;
import java.awt.*;

public enum Images {

    INITIAL_BACKGROUND("./src/main/java/it/polimi/ingsw/GUI/graphicResources/backgounds/Odyssey-Olympus.png"),
    GAMEBOARD("././src/main/java/it/polimi/ingsw/GUI/graphicResources/backgounds/SantoriniBoard.png"),
    GAME_ICON("./src/main/java/it/polimi/ingsw/GUI/graphicResources/backgounds/om_gloryIcon.png"),
    MOVE_BUTTON_ICON("./src/main/java/it/polimi/ingsw/GUI/graphicResources/sprite/move_button.png"),
    BUILD_BUTTON_ICON("./src/main/java/it/polimi/ingsw/GUI/graphicResources/sprite/build_button.png"),
    QUIT_BUTTON_ICON("./src/main/java/it/polimi/ingsw/GUI/graphicResources/sprite/quit_button.png"),
    END_TURN_BUTTON_ICON("./src/main/java/it/polimi/ingsw/GUI/graphicResources/sprite/end_turn_button.png"),

    APOLLO("./src/main/java/it/polimi/ingsw/GUI/graphicResources/godCards/Apollo.png"),
    ARTEMIS("./src/main/java/it/polimi/ingsw/GUI/graphicResources/godCards/Artemis.png"),
    ATHENA("./src/main/java/it/polimi/ingsw/GUI/graphicResources/godCards/Athena.png"),
    ATLAS("./src/main/java/it/polimi/ingsw/GUI/graphicResources/godCards/Atlas.png"),
    DEMETER("./src/main/java/it/polimi/ingsw/GUI/graphicResources/godCards/Demeter.png"),
    HEPHAESTUS("./src/main/java/it/polimi/ingsw/GUI/graphicResources/godCards/Hephaestus.png"),
    MINOTAUR("./src/main/java/it/polimi/ingsw/GUI/graphicResources/godCards/Minotaur.png"),
    PAN("./src/main/java/it/polimi/ingsw/GUI/graphicResources/godCards/Pan.png"),
    PROMETHEUS("./src/main/java/it/polimi/ingsw/GUI/graphicResources/godCards/Prometheus.png"),
    EMPTY_CARD("./src/main/java/it/polimi/ingsw/GUI/graphicResources/godCards/EmptyCard.png"),

    L0D("./src/main/java/it/polimi/ingsw/GUI/graphicResources/backgounds/"),
    L1D("./src/main/java/it/polimi/ingsw/GUI/graphicResources/backgounds/"),
    L12D("./src/main/java/it/polimi/ingsw/GUI/graphicResources/backgounds/"),
    L123D("./src/main/java/it/polimi/ingsw/GUI/graphicResources/backgounds/"),
    L0("./src/main/java/it/polimi/ingsw/GUI/graphicResources/backgounds/"),
    L1("./src/main/java/it/polimi/ingsw/GUI/graphicResources/backgounds/"),
    L12("./src/main/java/it/polimi/ingsw/GUI/graphicResources/backgounds/"),
    L123("./src/main/java/it/polimi/ingsw/GUI/graphicResources/backgounds/"),
    L0R("./src/main/java/it/polimi/ingsw/GUI/graphicResources/backgounds/"),
    L0B("./src/main/java/it/polimi/ingsw/GUI/graphicResources/backgounds/"),
    L0P("./src/main/java/it/polimi/ingsw/GUI/graphicResources/backgounds/"),
    L1R("./src/main/java/it/polimi/ingsw/GUI/graphicResources/backgounds/"),
    L1B("./src/main/java/it/polimi/ingsw/GUI/graphicResources/backgounds/"),
    L1P("./src/main/java/it/polimi/ingsw/GUI/graphicResources/backgounds/"),
    L12R("./src/main/java/it/polimi/ingsw/GUI/graphicResources/backgounds/"),
    L12B("./src/main/java/it/polimi/ingsw/GUI/graphicResources/backgounds/"),
    L12P("./src/main/java/it/polimi/ingsw/GUI/graphicResources/backgounds/"),
    L123R("./src/main/java/it/polimi/ingsw/GUI/graphicResources/backgounds/"),
    L123B("./src/main/java/it/polimi/ingsw/GUI/graphicResources/backgounds/"),
    L123P("./src/main/java/it/polimi/ingsw/GUI/graphicResources/backgounds/");
    




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
