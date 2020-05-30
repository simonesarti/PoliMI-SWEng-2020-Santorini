package it.polimi.ingsw.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Calendar;


public class PlayerInfoRequestScreen extends JDialog implements ActionListener {

    JLabel nicknameRequest;
    JLabel invalidNickname;
    JTextField nickname;
    JLabel birthdayRequest;
    JSpinner birthday;
    JButton confirmButton;

    public PlayerInfoRequestScreen(boolean isNicknameTaken){

        nicknameRequest=new JLabel("Nickname: ");
        nickname=new JTextField();
        invalidNickname=new JLabel();

        birthdayRequest=new JLabel("Date of birth: ");

        Date date=new Date();
        birthday=new JSpinner(new SpinnerDateModel(date,null,null, Calendar.DAY_OF_MONTH));
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(birthday, "dd/MM/yyyy");
        birthday.setEditor(dateEditor);


        confirmButton=new JButton("Confirm");

        if(isNicknameTaken){
            invalidNickname.setText("This username is already taken");
            invalidNickname.setForeground(Color.RED);
        }

        setBounds(isNicknameTaken);
        addToDialog();
        dialogSettings();

    }

    private void setBounds(boolean taken){

        int h=0;
        if(taken) {
            h = 20;
        }

        nicknameRequest.setBounds(20,20, 100,20);
        nickname.setBounds(120,20, 200,20);
        invalidNickname.setBounds(120,40,200,20);

        birthdayRequest.setBounds(20,60+h, 100,20);
        birthday.setBounds(120,60+h, 200,20);

        confirmButton.setBounds(125,100+h, 150,20);
    }


    private void addToDialog(){
        add(nicknameRequest);
        add(nickname);
        add(invalidNickname);
        add(birthdayRequest);
        add(birthday);
        add(confirmButton);
    }

    private void dialogSettings(){
        setTitle("Info Request");
        setSize(400,200);
        setLayout(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
