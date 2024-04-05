package com.example.bankingapplication;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
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

        int employeeID = Integer.parseInt(employeeIDTextField.getText());
        String employeeUsername = employeeUsernameTextField.getText();
        String employeePassword = employeePasswordTextField.getText();

        Firestore db = main.fstore;
        CollectionReference employeesRef = db.collection("employeeinfo");


    }

    public void handleOnMouseClick (MouseEvent event) throws IOException {
        System.out.println ("handleOnMouseClick called");

        main m = new main();
        m.changeScene("login.fxml");
    }
}
