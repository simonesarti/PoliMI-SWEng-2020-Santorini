package it.polimi.ingsw.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

//TODO JLIST NON CONVINCE
public class DivinityChoiceScreen extends JDialog implements ActionListener {


    JLabel request;
    JList namesList;

    public DivinityChoiceScreen(ArrayList<String> names){
        request=new JLabel("Choose your card:");
        DefaultListModel<String> l = new DefaultListModel<>();
        for(String name : names){
            l.addElement(name);
        }
        namesList=new JList(l);

        setBounds();
        addToDialog();
        dialogSettings();

    }

    private void dialogSettings(){
        setTitle("Gods");
        setSize(300,300);
        setLayout(null);
        setVisible(true);
    }

    private void addToDialog(){
        add(request);
        add(namesList);
    }

    private void setBounds(){
        request.setBounds(50,50,150,20);
        namesList.setBounds(50,100, 75,75);
    }





    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
