package it.polimi.ingsw.GUI;

import javax.swing.*;
import java.awt.*;

public enum Images {

    SANTORINI_LOGO("C:\\Users\\simon\\IdeaProjects\\Santorini\\src\\main\\java\\it\\polimi\\ingsw\\GUI\\graphicResources\\santorini-logo.png"),
    INITIAL_BACKGROUND("C:\\Users\\simon\\IdeaProjects\\Santorini\\src\\main\\java\\it\\polimi\\ingsw\\GUI\\graphicResources\\background#18810.png");

    private String path;

    Images(String path){
        this.path=path;
    }

    public String getPath(){return path;}

    public static Image getIcon(Images resource){

        Toolkit t=Toolkit.getDefaultToolkit();
        Image image=t.getImage(resource.getPath());
        return new ImageIcon(image).getImage();
    }


}
