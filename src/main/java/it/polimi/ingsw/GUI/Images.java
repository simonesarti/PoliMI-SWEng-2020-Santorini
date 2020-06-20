package it.polimi.ingsw.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * this class is used to store the paths to all the images used in the GUI
 */
public enum Images {

    INITIAL_BACKGROUND("./src/main/resources/graphicResources/backgounds/Odyssey-Olympus.png"),
    GAMEBOARD("././src/main/resources/graphicResources/backgounds/SantoriniBoard.png"),
    GAME_ICON("./src/main/resources/graphicResources/backgounds/om_gloryIcon.png"),

    MOVE_BUTTON_ICON("./src/main/resources/graphicResources/sprite/move_button.png"),
    BUILD_BUTTON_ICON("./src/main/resources/graphicResources/sprite/build_button.png"),
    QUIT_BUTTON_ICON("./src/main/resources/graphicResources/sprite/quit_button.png"),
    END_TURN_BUTTON_ICON("./src/main/resources/graphicResources/sprite/end_turn_button.png"),

    APOLLO("./src/main/resources/graphicResources/godCards/Apollo.png"),
    ARTEMIS("./src/main/resources/graphicResources/godCards/Artemis.png"),
    ATHENA("./src/main/resources/graphicResources/godCards/Athena.png"),
    ATLAS("./src/main/resources/graphicResources/godCards/Atlas.png"),
    DEMETER("./src/main/resources/graphicResources/godCards/Demeter.png"),
    HEPHAESTUS("./src/main/resources/graphicResources/godCards/Hephaestus.png"),
    MINOTAUR("./src/main/resources/graphicResources/godCards/Minotaur.png"),
    PAN("./src/main/resources/graphicResources/godCards/Pan.png"),
    PROMETHEUS("./src/main/resources/graphicResources/godCards/Prometheus.png"),
    EMPTY_CARD("./src/main/resources/graphicResources/godCards/EmptyCard.png"),

    L0D("./src/main/resources/graphicResources/board/level0withdome.png"),
    L1D("./src/main/resources/graphicResources/board/level1withdome.png"),
    L12D("./src/main/resources/graphicResources/board/level2withdome.png"),
    L123D("./src/main/resources/graphicResources/board/level3withdome.png"),
    L1("./src/main/resources/graphicResources/board/level1.png"),
    L12("./src/main/resources/graphicResources/board/level2.png"),
    L123("./src/main/resources/graphicResources/board/level3.png"),
    L0R("./src/main/resources/graphicResources/board/level0redworker.png"),
    L0B("./src/main/resources/graphicResources/board/level0blueworker.png"),
    L0P("./src/main/resources/graphicResources/board/level0purpleworker.png"),
    L1R("./src/main/resources/graphicResources/board/level1redworker.png"),
    L1B("./src/main/resources/graphicResources/board/level1blueworker.png"),
    L1P("./src/main/resources/graphicResources/board/level1purpleworker.png"),
    L12R("./src/main/resources/graphicResources/board/level2redworker.png"),
    L12B("./src/main/resources/graphicResources/board/level2blueworker.png"),
    L12P("./src/main/resources/graphicResources/board/level2purpleworker.png"),
    L123R("./src/main/resources/graphicResources/board/level3redworker.png"),
    L123B("./src/main/resources/graphicResources/board/level3blueworker.png"),
    L123P("./src/main/resources/graphicResources/board/level3purpleworker.png");


    private final String path;

    Images(String path){
        this.path=path;
    }

    public String getPath(){return path;}

    /**
     * @param resource is one one the Images values
     * @return the Image object created from the value given
     */
    public static Image getImage(Images resource){
        return new ImageIcon(resource.getPath()).getImage();
    }

    /**
     * @param resource is one one the Images values
     * @return the Icon object created from the value given
     */
    public static ImageIcon getIcon(Images resource){
        return new ImageIcon(resource.getPath());
    }


}
