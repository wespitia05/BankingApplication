package com.example.bankingapplication;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

import static com.example.bankingapplication.main.addDataToDB;
import static com.example.bankingapplication.main.stg;


public class createAcct2Controller extends createAcctController {
    @FXML
    private TextField createUsernameTextField;
    @FXML
    private TextField createPasswordTextField;
    @FXML
    private TextField confirmPasswordTextField;
    @FXML
    private Button createAcctButton;
    @FXML
    private Label goBackOption;

    private String firstName;
    private String lastName;
    private String address;
    private String zipCode;
    private String dob;

    public void setUserData(String firstName, String lastName, String address, String zipCode, String dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.zipCode = zipCode;
        this.dob = dob;
    }

    public void handleCreateAcctButton () throws IOException {
        System.out.println ("handleCreateAcctButton called");

        String username = createUsernameTextField.getText();
        String password = createPasswordTextField.getText();
        String confirmPassword = confirmPasswordTextField.getText();
        String checking = "0";
        String savings = "0";

        if (!password.equals(confirmPassword)) {
            Alert alert = new Alert (Alert.AlertType.WARNING);
            alert.setTitle("Passwords Don't Match!");
            alert.setContentText("Both passwords inputted do not match each other. Please try again.");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                System.out.println ("User acknowledged incorrect input.");
            }
            else {
                System.out.println ("Invalid input please try again.");
            }
            System.out.println("Passwords do not match");
            return;
        }

        if (usernameExists(username)) {
            System.out.println("Username already exists");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Username Exists");
            alert.setContentText("This username already exists. Please select a new username.");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                System.out.println("User acknowledged incorrect input.");
            }
            else {
                System.out.println("Invalid input please try again.");
            }
            return;
        }
        addDataToDB(firstName, lastName, address, zipCode, dob, username, password, checking, savings);
        System.out.println("Account created successfully");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homePagedemo.fxml"));
        Parent root = loader.load();
        stg.getScene().setRoot(root);
    }

    private boolean usernameExists(String username) {
        try {
            Firestore db = main.fstore;
            Query query = db.collection("userinfo").whereEqualTo("Username", username);
            QuerySnapshot querySnapshot = query.get().get();
            if (!querySnapshot.isEmpty()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void handleOnMouseClicked (MouseEvent event) throws IOException {
        System.out.println ("handleOnMouseClicked");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("createAcct.fxml"));
        Parent root = loader.load();
        stg.getScene().setRoot(root);
    }
}
