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
import java.util.Locale;
import java.util.Random;

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
    private TextField createEmailTextField;
    @FXML
    private TextField enterPhoneNumberTextField;
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
        String cardNum;
        String cardExp = generateExpirationDate();
        String cardCVV = generateCVV();
        String email = createEmailTextField.getText();
        String number = enterPhoneNumberTextField.getText();

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

        if (!isValidEmail(email)) {
            System.out.println("invalid email entry");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Email Entry");
            alert.setContentText("Email needs to be lastname, first initial of first name, @unitybank.com." +
                    "\nExample: riveraj@unitybank.com");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                System.out.println("User acknowledged incorrect input.");
            }
            else {
                System.out.println("Invalid input please try again.");
            }
            return;
        }

        if(!isValidPhoneNumber(number)) {
            System.out.println("invalid phone number entry");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Phone Number Entry");
            alert.setContentText("Phone number must be formatted as: xxx-xxx-xxxx");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                System.out.println("User acknowledged incorrect input.");
            }
            else {
                System.out.println("Invalid input please try again.");
            }
            return;
        }

        do {
            cardNum = generateCardNumber();
        } while (cardNumExists(cardNum));

        addDataToDB(firstName, lastName, address, zipCode, dob, username, password, checking, savings, cardNum, cardExp, cardCVV, email, number);
        System.out.println("Account created successfully");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();
        stg.getScene().setRoot(root);
    }

    private boolean isValidEmail(String email) {
        if (!email.matches(lastName.toLowerCase() + firstName.substring(0, 1).toLowerCase() + "@unitybank.com") || email.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        if (!phoneNumber.matches("\\d\\d\\d-\\d\\d\\d-\\d\\d\\d\\d") || phoneNumber.isEmpty()) {
            return false;
        }
        return true;
    }

    private String generateCardNumber() {
        String prefix = "223344556677";
        Random random = new Random();
        int lastFourDigits = random.nextInt(10000);
        return prefix + String.format("%04d", lastFourDigits);
    }

    private String generateExpirationDate() {
        Random random = new Random();
        int month = random.nextInt(12) + 1;
        return String.format("%02d", month) + "/26";
    }

    private String generateCVV() {
        Random random = new Random();
        int cvv = random.nextInt(900) + 100;
        return String.valueOf(cvv);
    }

    private boolean cardNumExists(String cardNum) {
        try {
            Firestore db = main.fstore;
            Query query = db.collection("userinfo").whereEqualTo("Card Number", cardNum);
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
