package it.polimi.ingsw.GUI;

import it.polimi.ingsw.client.ClientSideConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


//TODO RESIZE, AND INSERT ERROR LABELS
public class PlayerInfoRequestScreen extends JDialog implements ActionListener {

    ClientSideConnection connection;

    JLabel nicknameRequest;
    JLabel invalidNickname;
    JTextField nickname;
    JLabel birthdayRequest;
    JLabel invalidDate;
    JTextField birthday;
    JButton confirmButton;

    public PlayerInfoRequestScreen(ClientSideConnection connection){

        this.connection=connection;

        nicknameRequest=new JLabel("Nickname: ");
        nickname=new JTextField();
        invalidNickname=new JLabel();
        birthdayRequest=new JLabel("Date of birth: ");
        birthday=new JTextField();
        invalidNickname=new JLabel();
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
