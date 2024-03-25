package com.example.bankingapplication;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class employeeLoginController {
    @FXML
    private TextField employeeIDTextField;
    @FXML
    private TextField employeeUsernameTextField;
    @FXML
    private TextField employeePasswordTextField;
    @FXML
    private Button employeeLoginButton;
    @FXML
    private Label EmployeeGoBackOption;

    public void handleEmployeeLoginButton () {
        System.out.println("handleEmployeeLoginButton called");
    }

    public void handleOnMouseClick (MouseEvent event) throws IOException {
        System.out.println ("handleOnMouseClick called");

        main m = new main();
        m.changeScene("login.fxml");
    }
}
