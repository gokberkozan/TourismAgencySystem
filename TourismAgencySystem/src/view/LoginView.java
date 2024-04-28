package view;

import core.Helper;
import entity.Admin;
import entity.User;
import core.Config;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LoginView extends JFrame {
    private JPanel container;
    private JTextField fld_user_name;
    private JTextField fld_user_pass;
    private JButton btn_login;
    private JButton btn_logout;
    private JButton btn_signin;

    // Design features of the Login Screen
    public LoginView(){
        add(container);
        setSize(450,550);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);

        fld_user_pass.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        // Method that will run when login is pressed
        btn_login.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_user_name) || Helper.isFieldEmpty(fld_user_pass)){
                Helper.showMsg("fill");
            }
            else {
                User u = User.getFetch(fld_user_name.getText(), fld_user_pass.getText());
                if (u==null){
                    Helper.showMsg("No users found");
                } else {
                    switch (u.getType()){
                        case "admin":
                            AdminView opGUI = new AdminView((Admin) u);
                            break;
                        case "employee":
                            EmployeeView adGUI = new EmployeeView();
                            adGUI.setVisible(true);
                            dispose();
                            break;
                    }
                    dispose();
                }
            }
        });

        // Button that will be written when the exit button is pressed
        btn_logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}