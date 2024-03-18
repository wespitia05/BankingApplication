package com.example.bankingapplication;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class createAcctController {
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField zipCodeTextField;
    @FXML
    private TextField dobTextField;
    @FXML
    private Button nextButton;

    public void handleNextButton () {
        System.out.println ("handleNextButton called");
    }
}
