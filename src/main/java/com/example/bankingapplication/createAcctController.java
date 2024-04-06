package com.example.bankingapplication;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

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
    @FXML
    private Label goBackOption;

    private String firstName;
    private String lastName;
    private String address;
    private String zipCode;
    private String dob;

    public void setUserInformation(String firstName, String lastName, String address, String zipCode, String dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.zipCode = zipCode;
        this.dob = dob;
    }

    public void handleNextButton () throws IOException {
        System.out.println ("handleNextButton called");

        firstName = firstNameTextField.getText();
        lastName = lastNameTextField.getText();
        address = addressTextField.getText();
        zipCode = zipCodeTextField.getText();
        dob = dobTextField.getText();

        setUserInformation(firstName, lastName, address, zipCode, dob);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("createAcct2.fxml"));
        Parent root = loader.load();
        createAcct2Controller controller = loader.getController();
        controller.setUserData(firstName, lastName, address, zipCode, dob);

        main m = new main();
        m.changeScene("createAcct2.fxml");
    }

    public void handleOnMouseClicked (MouseEvent event) throws IOException {
        System.out.println ("handleOnMouseClicked");

        main m = new main();
        m.changeScene("login.fxml");
    }
}
