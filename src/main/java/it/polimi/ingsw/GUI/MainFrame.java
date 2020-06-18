package it.polimi.ingsw.GUI;

import javax.swing.*;

/**
 * frame of the gui
 */
public class MainFrame extends JFrame {

    public MainFrame(){
        frameInitialization();
    }

    private void frameInitialization(){
        setFrameLook();
        setFrameProperties();
    }

    private void setFrameLook(){
        setTitle("SANTORINI");
        setDefaultLookAndFeelDecorated(true);
        setIconImage(Images.getImage(Images.GAME_ICON));
    }

    private void setFrameProperties(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900,600);
        setResizable(true);
        setLocationRelativeTo(null);
    }







}
