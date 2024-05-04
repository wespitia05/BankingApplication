package com.example.bankingapplication;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static com.example.bankingapplication.main.stg;

public class loginController {
    @FXML
    public TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button loginButton;
    @FXML
    private Button createAcctButton;
    @FXML
    private Button employeeLoginButton;
    @FXML
    private Button showPasswordButton;
    @FXML
    private Label showPassword;
    @FXML
    private Label userFullName;

    private boolean passwordVisible = false;
    String firstName;
    String lastName;
    String cardNum;
    String cardExp;
    // testing for branch
    // branch test for mian worked
    // luis branch test
    // luis branch test 2
    public void initialize () {
        System.out.println ("Initialize called");
    }

    public void setUserFullName (String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setCardNum (String cardNum) {
        this.cardNum = cardNum;
    }

    public void setCardExp (String cardExp) {
        this.cardExp = cardExp;
    }


    public void handleLoginButton (ActionEvent event) throws IOException, ExecutionException, InterruptedException {
        System.out.println ("handleLoginButton called");

        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        Firestore db = main.fstore;
        CollectionReference usersRef = db.collection("userinfo");

        ApiFuture<QuerySnapshot> future = usersRef.whereEqualTo("Username", username).get();
        QuerySnapshot querySnapshot;
        try {
            querySnapshot = future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return;
        }
        if (!querySnapshot.isEmpty()) {
            DocumentSnapshot document = querySnapshot.getDocuments().get(0);
            String storedPassword = document.getString("Password");
            if (storedPassword.equals(password)) {
                System.out.println("Login successful");

                userInfo.setUsername(username);
                userInfo.setFirstName(document.getString("First Name"));
                userInfo.setLastName(document.getString("Last Name"));
                userInfo.setChecking(document.getString("Checking"));
                userInfo.setSavings(document.getString("Savings"));
                userInfo.setCardNum(document.getString("Card Number"));
                userInfo.setCardExp(document.getString("Card Expiration Date"));
                userInfo.setCardCVV(document.getString("Card CVV"));
                transactionsInfo.setName(document.getString("Name"));
                transactionsInfo.setCategory(document.getString("Category"));
                transactionsInfo.setAmount(document.getString("Amount"));
                transactionsInfo.setDate(document.getString("Date"));

                // Retrieve the first name associated with the username

                FXMLLoader loader = new FXMLLoader(getClass().getResource("homePagedemo.fxml"));
                Parent root = loader.load();
                homePageController homeController = loader.getController();
                homeController.setUsername(username);
                homeController.setUserFullName(firstName, lastName);
                homeController.setCardNum(cardNum);
                homeController.setCardExp(cardExp);
                stg.getScene().setRoot(root);


            } else {
                System.out.println("Password Incorrect");
                incorrectPasswordAlert();
            }
        } else {
            System.out.println("Username Incorrect");
            incorrectUsernameAlert();
        }
    }

    public void incorrectUsernameAlert () {
        Alert alert = new Alert (Alert.AlertType.WARNING);
        alert.setTitle("Incorrect Username");
        alert.setContentText("Sorry! Username not found. Please try again.");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            System.out.println ("User acknowledged incorrect input.");
        }
        else {
            System.out.println ("Invalid input please try again.");
        }
    }

    public void incorrectPasswordAlert () {
        Alert alert = new Alert (Alert.AlertType.WARNING);
        alert.setTitle("Incorrect Password");
        alert.setContentText("Sorry! Incorrect Password. Please try again.");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            System.out.println ("User acknowledged incorrect input.");
        }
        else {
            System.out.println ("Invalid input please try again.");
        }
    }

    public void showPasswordKeyTyped (KeyEvent event) {
        if (passwordVisible) {
            showPassword.setText(passwordTextField.getText());
        }
    }

    public void showPasswordButton() {
        if (!passwordVisible) {
            System.out.println("showPasswordButton called");
            showPassword.setText(passwordTextField.getText());
            showPassword.setVisible(true);
            showPasswordButton.setText("Hide Password");
            passwordVisible = true;
        } else {
            System.out.println("hidePasswordButton called");
            showPassword.setVisible(false);
            showPasswordButton.setText("Show Password");
            passwordVisible = false;
        }
    }

    public void handleEmployeeLoginButton () throws IOException {
        System.out.println ("handleEmployeeLogin called");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("employeeLogin.fxml"));
        Parent root = loader.load();
        stg.getScene().setRoot(root);
    }
    public void handleCreateAcctButton () throws IOException {
        System.out.println ("handleCreateButton called");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("createAcct.fxml"));
        Parent root = loader.load();
        stg.getScene().setRoot(root);
    }
}