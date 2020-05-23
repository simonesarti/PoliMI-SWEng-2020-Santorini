package it.polimi.ingsw.GUI;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame(){
        frameInitialization();
    }

    private void frameInitialization(){
        createMainPanel();
        setFrameLook();
        setFrameProperties();
    }

    private void createMainPanel(){
        add(new MainPanel());
    }

    private void setFrameLook(){
        setTitle(SettingsGUI.TITLE);
        setSize(SettingsGUI.frameWidth,SettingsGUI.frameHeight);
        setResizable(false);
        setDefaultLookAndFeelDecorated(true);
        setIconImage(Images.getImage(Images.GAME_ICON));
    }

    private void setFrameProperties(){
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        //pack();
    }





}
