package it.polimi.ingsw.GUI;

import it.polimi.ingsw.messages.GameToPlayerMessages.Others.ErrorMessage;
import it.polimi.ingsw.messages.GameToPlayerMessages.Others.InfoMessage;

import javax.swing.*;

public class ReportScreen extends JDialog{

    public ReportScreen(InfoMessage infoMessage){
        JOptionPane.showMessageDialog(this,infoMessage.getInfo(),"Info Message",JOptionPane.INFORMATION_MESSAGE);
    }

    public ReportScreen(ErrorMessage errorMessage){
        JOptionPane.showMessageDialog(this,errorMessage.getError(),"Error Message",JOptionPane.ERROR_MESSAGE);
    }

}
