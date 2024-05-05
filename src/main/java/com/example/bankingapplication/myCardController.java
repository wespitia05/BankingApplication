package com.example.bankingapplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static com.example.bankingapplication.main.stg;

public class myCardController extends homePageController{

    @FXML
    private TextField cardNum_TF;
    @FXML
    private TextField currency_Tf;
    @FXML
    private TextField balance_TF;
    @FXML
    private TextField name_TF;
    @FXML
    private Label userFullName;  //label next ot the circle
    @FXML
    private Label cardNumLabel;  //in card one
    @FXML
    private Label cardExpLabel; //in card one

    /////////////////////////// Event Handlers for the Sidebar Buttons/////////////////////////
    @FXML
    private void handledashBoard_btn(ActionEvent event) throws IOException {
        System.out.println("Dashboard clicked");



        // Load the FXML file and get the root and controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homePagedemo.fxml"));
        Parent root = loader.load(); // This is the root node of your new scene, loaded from FXML
        homePageController controller = loader.getController();

        // Set data using methods in your controller
        controller.setUserFullName(userInfo.getFirstName(), userInfo.getLastName());
        controller.setCardNum("**** **** **** " + userInfo.getCardNum().substring(userInfo.getCardNum().length() - 4));
        controller.setCardExp(userInfo.getCardExp());
        controller.updateCheckingBalanceInFirestore(Double.parseDouble(userInfo.getChecking()));
        controller.updateSavingsBalanceInFirestore(Double.parseDouble(userInfo.getSavings()));
        controller.setBalances(userInfo.getChecking(), userInfo.getSavings());

        // Set the scene on the current stage
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    public void setUserFullName(String fullName) {
        userFullName.setText(fullName);
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addAccount.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleDeposit_btn(ActionEvent event) {
        System.out.println("Deposit clicked");
    }

    @FXML
    private void handleWithdrawl_btn(ActionEvent event)throws IOException {
        System.out.println("Withdrawal clicked");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("withdrawal.fxml"));


        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }



    @FXML
    private void handleTransfer_btn(ActionEvent event)throws IOException {
        System.out.println("Transfers clicked");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("transfers.fxml"));


        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void handleHistory_btn(ActionEvent event) {
        System.out.println("History clicked");
    }

    /////////////////////////// Event Handlers for the Sidebar Buttons/////////////////////////


    // Initialization method
    public void initialize() {
        userFullName.setText(userInfo.getFirstName() + " " + userInfo.getLastName());
        cardNumLabel.setText("**** **** **** " + userInfo.getCardNum().substring(userInfo.getCardNum().length() - 4));
        cardExpLabel.setText(userInfo.getCardExp());
    }
}
