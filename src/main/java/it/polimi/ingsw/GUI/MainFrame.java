package it.polimi.ingsw.GUI;

import javax.swing.*;

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
        setSize(1920,1080);
        setResizable(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }







}
