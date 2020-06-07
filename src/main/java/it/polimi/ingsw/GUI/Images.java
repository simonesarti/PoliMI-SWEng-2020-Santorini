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

    L0D("./src/main/java/it/polimi/ingsw/GUI/graphicResources/board/level0withdome.png"),
    L1D("./src/main/java/it/polimi/ingsw/GUI/graphicResources/board/level1withdome.png"),
    L12D("./src/main/java/it/polimi/ingsw/GUI/graphicResources/board/level2withdome.png"),
    L123D("./src/main/java/it/polimi/ingsw/GUI/graphicResources/board/level3withdome.png"),
    L0("./src/main/java/it/polimi/ingsw/GUI/graphicResources/board/"),
    L1("./src/main/java/it/polimi/ingsw/GUI/graphicResources/board/level1.png"),
    L12("./src/main/java/it/polimi/ingsw/GUI/graphicResources/board/level2.png"),
    L123("./src/main/java/it/polimi/ingsw/GUI/graphicResources/board/level3.png"),
    L0R("./src/main/java/it/polimi/ingsw/GUI/graphicResources/board/level0redworker.png"),
    L0B("./src/main/java/it/polimi/ingsw/GUI/graphicResources/board/level0blueworker.png"),
    L0P("./src/main/java/it/polimi/ingsw/GUI/graphicResources/board/level0purpleworker.png"),
    L1R("./src/main/java/it/polimi/ingsw/GUI/graphicResources/board/level1redworker.png"),
    L1B("./src/main/java/it/polimi/ingsw/GUI/graphicResources/board/level1blueworker.png"),
    L1P("./src/main/java/it/polimi/ingsw/GUI/graphicResources/board/level1purpleworker.png"),
    L12R("./src/main/java/it/polimi/ingsw/GUI/graphicResources/board/level2redworker.png"),
    L12B("./src/main/java/it/polimi/ingsw/GUI/graphicResources/board/level2blueworker.png"),
    L12P("./src/main/java/it/polimi/ingsw/GUI/graphicResources/board/level2purpleworker.png"),
    L123R("./src/main/java/it/polimi/ingsw/GUI/graphicResources/board/level3redworker.png"),
    L123B("./src/main/java/it/polimi/ingsw/GUI/graphicResources/board/level3blueworker.png"),
    L123P("./src/main/java/it/polimi/ingsw/GUI/graphicResources/board/level3purpleworker.png");
    




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
