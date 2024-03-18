package com.example.bankingapplication;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class loginController {
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button loginButton;
    @FXML
    private Button createAcctButton;
    @FXML
    private Button employeeLoginButton;
    public void initialize () {
        System.out.println ("Initialize called");

    }
    public void handleLoginButton () {
        System.out.println ("handleLoginButton called");
    }

    public void handleEmployeeLoginButton () throws IOException {
        System.out.println ("handleEmployeeLogin called");

        main m = new main();
        m.changeScene("employeeLogin.fxml");
    }
    public void handleCreateAcctButton () throws IOException {
        System.out.println ("handleCreateButton called");

        main m = new main();
        m.changeScene("createAcct.fxml");
    }
}