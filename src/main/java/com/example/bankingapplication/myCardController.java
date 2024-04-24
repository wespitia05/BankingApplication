package com.example.bankingapplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class myCardController {


    @FXML
    private TextField cardNum_TF;
    @FXML
    private TextField currency_Tf;
    @FXML
    private TextField balance_TF;
    @FXML
    private TextField name_TF;

    // Event Handlers for the Sidebar Buttons
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
    private void handlemyCard_btn(ActionEvent event) {
        System.out.println("Why are you clicking me, You are already on my page.");
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
    private void handleAddCard_btn(ActionEvent event) throws IOException {
        System.out.println("Add Card clicked");

        System.out.println("Dashboard clicked");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("addAccount.fxml"));
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
    private void handleDeposit_btn(ActionEvent event) {
        System.out.println("Deposit clicked");
    }

    @FXML
    private void handleWithdrawl_btn(ActionEvent event) {
        System.out.println("Withdrawal clicked");
    }

    @FXML
    private void handleTransfer_btn(ActionEvent event) {
        System.out.println("Transfer clicked");
    }

    @FXML
    private void handleHistory_btn(ActionEvent event) {
        System.out.println("History clicked");
    }

    // Initialization method
    @FXML
    public void initialize() {
        // Initialization logic here
    }





}
