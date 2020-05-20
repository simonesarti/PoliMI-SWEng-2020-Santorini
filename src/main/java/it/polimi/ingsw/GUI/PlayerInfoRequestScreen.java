package it.polimi.ingsw.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


//TODO RESIZE
public class PlayerInfoRequestScreen extends JDialog implements ActionListener {

    JLabel nicknameRequest;
    JTextField nickname;
    JLabel birthdayRequest;
    JTextField birthday;
    JButton confirmButton;

    public PlayerInfoRequestScreen(){

        nicknameRequest=new JLabel("Nickname: ");
        nickname=new JTextField();
        birthdayRequest=new JLabel("Date of birth: ");
        birthday=new JTextField();
        confirmButton=new JButton("Confirm");

        setBounds();
        addListeners();
        addToDialog();
        dialogSettings();

    }

    private void setBounds(){
        nicknameRequest.setBounds(50,20, 100,20);
        nickname.setBounds(150,20, 100,20);

        birthdayRequest.setBounds(50,50, 100,20);
        birthday.setBounds(150,50, 100,20);

        confirmButton.setBounds(50,80, 150,20);
    }

    private void addListeners(){
        confirmButton.addActionListener(this);
    }

    private void addToDialog(){
        add(nicknameRequest);
        add(nickname);
        add(birthdayRequest);
        add(birthday);
        add(confirmButton);
    }

    private void dialogSettings(){
        setTitle("Info Request");
        setSize(300,175);
        //pack();
        setLayout(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
