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
import javafx.scene.input.KeyEvent;
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
    private TextField enterAmount_TF;
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
    @FXML
    private Label userFullName;
    private String username;

    @FXML
    public void initialize() {
        // Initialization code
        generatePieChart();
    }

    public void setUsername(String username) {
        this.username = username;
        displayUserBalances();
        displayUserFullName();
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

    public void displayUserFullName () {
        if (username != null && !username.isEmpty()) {
            Firestore db = main.fstore;
            CollectionReference usersRef = db.collection("userinfo");

            ApiFuture<QuerySnapshot> future = usersRef.whereEqualTo("Username", username).get();
            future.addListener(() -> {
                try {
                    QuerySnapshot querySnapshot = future.get();
                    if (!querySnapshot.isEmpty()) {
                        DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                        String firstName = document.getString("First Name");
                        String lastName = document.getString("Last Name");
                        Platform.runLater(() -> setUserFullName(firstName, lastName));
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }, Executors.newSingleThreadExecutor());
        } else {
            System.out.println("Username is not set or empty");
        }
    }

    public void setUserFullName(String firstName, String lastName) {
        userFullName.setText(firstName + " " + lastName);
    }


    /////////////////////////////////////////// side bar //////////////////////////////////
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



    ////////////// side bar ///////////////////////////////////////////////



    /////////////////////////////////////////////////// Quick Transfer ////////////////////////////////
    @FXML
    private void handleSave_btn(ActionEvent event) {
        // These fields are for Debit card info
        String enteredDebit = debit_TF.getText().trim();
        String firebaseDebit = getDebitInfoFromFirestore(); // Retrieve debit info from Firestore

        // Get the entered amount from the enterAmount_TF TextField
        String enteredAmount = enterAmount_TF.getText().trim();

        // Check if the entered amount is a valid number
        if (!enteredAmount.isEmpty() && enteredAmount.matches("\\d*\\.?\\d+")) {
            // Convert the entered amount to double
            double amount = Double.parseDouble(enteredAmount);

            // Update the checking balance if the debit card is found
            if (enteredDebit.equals(firebaseDebit)) {
                String checkingBalance = checkingBalanceTF.getText().trim();
                if (!checkingBalance.isEmpty() && checkingBalance.matches("\\d*\\.?\\d+")) {
                    double checking = Double.parseDouble(checkingBalance);
                    checking += amount;
                    checkingBalanceTF.setText(String.valueOf(checking));

                    // Call the method to update the checking balance in Firestore

                      updateCheckingBalanceInFirestore(checking);

                } else {
                    // Display an error message if the checking balance is empty or not valid
                    System.out.println("Invalid checking balance");
                }
            } else {
                // Print message if debit card is not found
                System.out.println("Debit card not found");
            }
        } else {
            // Display an error message if the entered amount is not valid
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Amount");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid amount.");
            alert.showAndWait();
        }
    }


    ///////////////////// updating balance into Fire Store ///////////////////////

    private void updateCheckingBalanceInFirestore(double checkingBalance) {
        Firestore db = main.fstore;
        CollectionReference usersRef = db.collection("userinfo");

        ApiFuture<QuerySnapshot> future = usersRef.whereEqualTo("Username", username).get();
        future.addListener(() -> {
            try {
                QuerySnapshot querySnapshot = future.get();
                if (!querySnapshot.isEmpty()) {
                    DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                    String documentId = document.getId();

                    // Update the Checking balance field in Firestore
                    usersRef.document(documentId).update("Checking", String.valueOf(checkingBalance));
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }, Executors.newSingleThreadExecutor());
    }


    /////////////////////////////Updating balance in FireStore////////////////////////////////////////

    ///////////////////////////Getting debit into form fireBase //////////////////////////////////////////////////

    private String getDebitInfoFromFirestore() {
        Firestore db = main.fstore;
        CollectionReference debitRef = db.collection("userinfo");

        // Query Firestore to retrieve debit card info based on entered debit card number
        ApiFuture<QuerySnapshot> future = debitRef.whereEqualTo("Debit", debit_TF.getText().trim()).get();
        try {
            QuerySnapshot querySnapshot = future.get();
            if (!querySnapshot.isEmpty()) {
                DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                return document.getString("Debit");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null; // Return null if debit card info not found
    }

    ///????????????????????????????????????????  Getting debit info form fireBase ????????????????????????????????????////




    @FXML
    private void handleDraft_btn() {
        // Handle save as draft button action
    }

    //expense report for pie chart
    // Method to generate and display the pie chart
    public void generatePieChart() {

    }


}
