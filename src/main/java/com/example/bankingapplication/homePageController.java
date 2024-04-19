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
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class homePageController extends loginController{

    //btn = button--- // TF = text Field
    @FXML
    private Button save_btn;
    @FXML
    private Button saveDraft_btn;
    @FXML
    private TextField addAccountNum_TF;
    @FXML
    private PieChart pieChart;
    @FXML
    private TextField income_TF;
    @FXML
    private TextField expenses_TF;
    @FXML
    private TextField savings_TF;
    @FXML
    private TextField checkingBalanceTF;
    @FXML
    private TextField savingsBalanceTF;
    @FXML
    private TextField debit_TF;
    private String username;

    @FXML
    public void initialize() {
        // Initialization code
        generatePieChart();
    }

    public void setUsername(String username) {
        this.username = username;
        displayUserBalances();  // Now call to fetch balances after username is set
    }

    private void displayUserBalances() {
        if (username != null && !username.isEmpty()) {
            Firestore db = main.fstore;
            CollectionReference usersRef = db.collection("userinfo");

            ApiFuture<QuerySnapshot> future = usersRef.whereEqualTo("Username", username).get();
            future.addListener(() -> {
                try {
                    QuerySnapshot querySnapshot = future.get();
                    if (!querySnapshot.isEmpty()) {
                        DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                        String checkingBalance = document.getString("Checking");
                        String savingsBalance = document.getString("Savings");
                        Platform.runLater(() -> setBalances(checkingBalance, savingsBalance));
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }, Executors.newSingleThreadExecutor());
        } else {
            System.out.println("Username is not set or empty");
        }
    }

    public void setBalances(String checking, String savings) {
        if (checkingBalanceTF != null && savingsBalanceTF != null) {
            checkingBalanceTF.setText(checking);
            savingsBalanceTF.setText(savings);
        }
    }


    @FXML
    private void handledashBoard_btn() {
        System.out.println("Stop Clicking me, you are on my page");

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

    @FXML
    private void handleSave_btn() {
        // Handle save button action
    }

    @FXML
    private void handleDraft_btn() {
        // Handle save as draft button action
    }

    //expense report for pie chart
    // Method to generate and display the pie chart
    public void generatePieChart() {

    }

}
