package com.example.bankingapplication;

///this controller takes you to another scene in which you can add another account


import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class addAccountController {

    @FXML
    private TextField CVV_TF;

    @FXML
    private TextField accountNumber_TF;

    @FXML
    private TextField enterName_TF;

    @FXML
    private Button save_btn;



    // Initialization method
    @FXML
    public void initialize() {
        // Initialization logic here
    }

    @FXML
    private void handleSave_btn(ActionEvent event) {
        System.out.println("Save clicked");

        // Get the data from the text fields
        String CVV = CVV_TF.getText();
        String accountNumber = accountNumber_TF.getText();
        String enterName = enterName_TF.getText();

        // Access Firestore
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference accountsRef = db.collection("userinfo");

        // Prepare the document data
        Map<String, Object> accountData = new HashMap<>();
        accountData.put("CVV", CVV);
        accountData.put("Account Number", accountNumber);
        accountData.put("Holder Name", enterName);

        // Asynchronously add the document to Firestore
        ApiFuture<WriteResult> future = accountsRef.document().create(accountData);
        future.addListener(() -> {
            try {
                WriteResult result = future.get();
                Platform.runLater(() -> {
                    System.out.println("Account added successfully at: " + result.getUpdateTime());
                });
            } catch (InterruptedException | ExecutionException e) {
                Platform.runLater(() -> {
                    System.err.println("Error adding account: " + e.getMessage());
                });
            }
        }, Runnable::run);
    }
    @FXML
    private void handlemyCard_btn(ActionEvent event) throws IOException {
        System.out.println("My Cards clicked");


        FXMLLoader loader = new FXMLLoader(getClass().getResource("myCards.fxml"));
        Parent root = loader.load();

        // Create a new scene
        Scene scene = new Scene(root);

        // Get the stage information
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the new scene on the stage
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handletranaction_btn(ActionEvent event) {
        System.out.println("Transactions clicked");
    }

    @FXML
    private void handlepayment_btn(ActionEvent event) {
        System.out.println("Payments clicked");
    }

    @FXML
    private void handlereports_btn(ActionEvent event) {
        System.out.println("Reports clicked");
    }

    @FXML
    private void handleprofile_btn(ActionEvent event) {
        System.out.println("Profiles clicked");
    }

    @FXML
    private void handlesettings_btn(ActionEvent event) {
        System.out.println("Settings clicked");
    }

    // Event Handlers for the Card Operations Buttons
    @FXML
    private void handledashBoard_btn(ActionEvent event) {
        System.out.println("Add Card clicked");
    }



}