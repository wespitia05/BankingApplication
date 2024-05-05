package com.example.bankingapplication;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.EventObject;

import static com.example.bankingapplication.main.stg;

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
    private Object event;

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

        if (!isValidZipCode(zipCode)) {
            System.out.println("zip code format incorrect or is empty");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Zip Code Entry");
            alert.setContentText("Zip code must contain 5 digits");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                System.out.println("User acknowledged incorrect input.");
            }
            else {
                System.out.println("Invalid input please try again.");
            }
            return;
        }

        if (!isValidDob(dob)) {
            System.out.println("date of birth format incorrect or is empty");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Date of Birth Entry");
            alert.setContentText("Date of birth must be formatted as mm/dd/yy");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                System.out.println("User acknowledged incorrect input.");
            }
            else {
                System.out.println("Invalid input please try again.");
            }
            return;
        }

        setUserInformation(firstName, lastName, address, zipCode, dob);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("createAcct2.fxml"));
        Parent root = loader.load();
        createAcct2Controller controller = loader.getController();
        controller.setUserData(firstName, lastName, address, zipCode, dob);
        stg.getScene().setRoot(root);
    }

    private boolean isValidZipCode (String zipCode) {
        if (!zipCode.matches("\\d\\d\\d\\d\\d") || zipCode.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean isValidDob (String dob) {
        if (!dob.matches("^(0[1-9]|1[0-2])/(0[1-9]|1[0-9]|2[0-9]|3[0-1])/\\d{2}$") || dob.isEmpty()) {
            return false;
        }
        return true;
    }

    public void handleOnMouseClicked (MouseEvent event) throws IOException {
        System.out.println ("handleOnMouseClicked");

        main m = new main();
        m.changeScene("login.fxml");
    }
}
