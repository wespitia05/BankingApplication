package com.example.bankingapplication;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static com.example.bankingapplication.userInfo.username;

public class ProfileController {

    @FXML
    private TextField address_tf;

    @FXML
    private TextField birthDate_tf;

    @FXML
    private TextField email_tf;

    @FXML
    private TextField name_tf;

    @FXML
    private TextField number_tf;

    @FXML
    private Button updateProfile_btn;

    @FXML
    private TextField userName_tf;

    @FXML
    private TextField zipCode_tf;




    ///////////// buttons on side bar////////////////////////////////////////////
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


    @FXML
    private void handlemyCard_btn(ActionEvent event) throws IOException {
        System.out.println("My Cards clicked");

        // Load the FXML file and get the root and controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("myCards.fxml"));
        Parent root = loader.load(); // This is the root node of your new scene, loaded from FXML
        myCardController controller = loader.getController();

        // Set data using methods in your controller
        controller.setUserFullName(userInfo.getFirstName() + " " + userInfo.getLastName());
        controller.setCardNum("**** **** **** " + userInfo.getCardNum().substring(userInfo.getCardNum().length() - 4));
        controller.setCardExp(userInfo.getCardExp());
        controller.setBalances(userInfo.getChecking(), userInfo.getSavings());

        // Set the scene on the current stage
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void handlepayment_btn(ActionEvent event) {

    }

    @FXML
    void handleprofile_btn(ActionEvent event) {

    }

    @FXML
    void handlereports_btn(ActionEvent event) {

    }

    @FXML
    void handlesettings_btn(ActionEvent event) {

    }

    @FXML
    void handletranaction_btn(ActionEvent event) {

    }

    @FXML
    void handleUpdateProfile_btn(ActionEvent event) throws IOException {
        System.out.println("Update Profile clicked");

        // Load the FXML file and get the root and controller for the transactions view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("updateProfile.fxml"));
        Parent root = loader.load(); // This is the root node of your new scene, loaded from FXML

        // Set the scene on the current stage
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    //////////////////////////////////////// buttons on side bar/////////////////////////////////

    public void initialize() {
        name_tf.setText(userInfo.getFirstName() + " " + userInfo.getLastName());
        userName_tf.setText(userInfo.getUsername());
        email_tf.setText(userInfo.getEmail());
        birthDate_tf.setText(userInfo.getDob());
        zipCode_tf.setText(userInfo.getZipCode());
        address_tf.setText(userInfo.getAddress());
        number_tf.setText(userInfo.getNumber());


    }




}
