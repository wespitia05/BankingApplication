package com.example.bankingapplication;

///this controller takes you to another scene in which you can add another account


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class addAccountController {

    @FXML
    private TextField bankName;
    @FXML
    private TextField accountName;
    @FXML
    private TextField accountNum;

    @FXML
    private void handledashBoard_btn(ActionEvent event) throws IOException {
        System.out.println("Dashboard clicked");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("homePagedemo.fxml"));
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
    private void handleAddCard_btn(ActionEvent event) {
        System.out.println("Add Card clicked");
    }

    @FXML
    private void handleSave_btn(ActionEvent event) {
        System.out.println("Save clicked");
    }

    // Initialization method
    @FXML
    public void initialize() {
        // Initialization logic here
    }


}