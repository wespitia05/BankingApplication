package com.example.bankingapplication;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class employeeLoginController {
    @FXML
    private TextField employeeIDTextField;
    @FXML
    private TextField employeeUsernameTextField;
    @FXML
    private TextField employeePasswordTextField;
    @FXML
    private Button employeeLoginButton;

    public void handleEmployeeLoginButton () {
        System.out.println("handleEmployeeLoginButton called");
    }
}
