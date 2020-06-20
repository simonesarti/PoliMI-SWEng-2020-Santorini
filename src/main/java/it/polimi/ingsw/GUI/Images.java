package it.polimi.ingsw.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * this class is used to store the paths to all the images used in the GUI
 */
public enum Images {

    INITIAL_BACKGROUND("graphicResources/backgounds/Odyssey-Olympus.png"),
    GAMEBOARD("./graphicResources/backgounds/SantoriniBoard.png"),
    GAME_ICON("graphicResources/backgounds/om_gloryIcon.png"),

    MOVE_BUTTON_ICON("graphicResources/sprite/move_button.png"),
    BUILD_BUTTON_ICON("graphicResources/sprite/build_button.png"),
    QUIT_BUTTON_ICON("graphicResources/sprite/quit_button.png"),
    END_TURN_BUTTON_ICON("graphicResources/sprite/end_turn_button.png"),

    APOLLO("graphicResources/godCards/Apollo.png"),
    ARTEMIS("graphicResources/godCards/Artemis.png"),
    ATHENA("graphicResources/godCards/Athena.png"),
    ATLAS("graphicResources/godCards/Atlas.png"),
    DEMETER("graphicResources/godCards/Demeter.png"),
    HEPHAESTUS("graphicResources/godCards/Hephaestus.png"),
    MINOTAUR("graphicResources/godCards/Minotaur.png"),
    PAN("graphicResources/godCards/Pan.png"),
    PROMETHEUS("graphicResources/godCards/Prometheus.png"),
    EMPTY_CARD("graphicResources/godCards/EmptyCard.png"),

    L0D("graphicResources/board/level0withdome.png"),
    L1D("graphicResources/board/level1withdome.png"),
    L12D("graphicResources/board/level2withdome.png"),
    L123D("graphicResources/board/level3withdome.png"),
    L1("graphicResources/board/level1.png"),
    L12("graphicResources/board/level2.png"),
    L123("graphicResources/board/level3.png"),
    L0R("graphicResources/board/level0redworker.png"),
    L0B("graphicResources/board/level0blueworker.png"),
    L0P("graphicResources/board/level0purpleworker.png"),
    L1R("graphicResources/board/level1redworker.png"),
    L1B("graphicResources/board/level1blueworker.png"),
    L1P("graphicResources/board/level1purpleworker.png"),
    L12R("graphicResources/board/level2redworker.png"),
    L12B("graphicResources/board/level2blueworker.png"),
    L12P("graphicResources/board/level2purpleworker.png"),
    L123R("graphicResources/board/level3redworker.png"),
    L123B("graphicResources/board/level3blueworker.png"),
    L123P("graphicResources/board/level3purpleworker.png");


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
        return new ImageIcon(Images.class.getClassLoader().getResource(resource.getPath())).getImage();
    }

    /**
     * @param resource is one one the Images values
     * @return the Icon object created from the value given
     */
    public static ImageIcon getIcon(Images resource){
        return new ImageIcon(resource.getPath());
    }


}
