package com.example.bankingapplication;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.cloud.firestore.*;
import com.google.common.util.concurrent.MoreExecutors;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static com.example.bankingapplication.userInfo.username;

public class myCardController extends homePageController{

    @FXML
    private TextField cardNum_TF;
    @FXML
    private TextField expDate_TF;
    @FXML
    private TextField CVV_TF;
    @FXML
    private TextField name_TF;
    @FXML
    private Label userFullName;  //label next ot the circle
    @FXML
    private TableColumn<?, ?> cost_COL;

    @FXML
    private TableColumn<?, ?> transactions_COL;
    @FXML
    private TableView<transactionInfoDisplay> Table_View;

    @FXML
    private Label cardNumLabel;  //in card one

    @FXML
    private Label cardExpLabel; //in card one
    private Firestore firestore;


    public void setFirestore(Firestore firestore) {
        this.firestore = firestore;
    }


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
    private void handleTransfer_btn(ActionEvent event) {
        System.out.println("Transfer clicked");
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
        name_TF.setText(userInfo.getFirstName() + " " + userInfo.getLastName());
        cardNum_TF.setText(userInfo.getCardNum());
        CVV_TF.setText(userInfo.getCardCVV());
        expDate_TF.setText(userInfo.getCardExp());


//        transactions_COL.setCellValueFactory(new PropertyValueFactory<>("Category"));
//        cost_COL.setCellValueFactory(new PropertyValueFactory<>("Amount"));


        // Configure the TableView columns
        transactions_COL.setCellValueFactory(new PropertyValueFactory<>("Category"));
        cost_COL.setCellValueFactory(new PropertyValueFactory<>("Amount"));

        // Fetch data from Firebase Firestore and populate the TableView




        fetchDataFromFirestore();
    }


    private void fetchDataFromFirestore() {
        if (userInfo.getUsername() != null && !userInfo.getUsername().isEmpty()) {
            Firestore db = main.fstore;

            // Get the user document reference
            DocumentReference userDocRef = db.collection("userinfo").document(userInfo.getUsername());

            // Access the transactions subcollection under the user document
            CollectionReference transactionsRef = userDocRef.collection("transactions");

            // Query the transactions subcollection
            Query query = transactionsRef.orderBy("Amount", Query.Direction.DESCENDING); // Order by amount instead of date

            // Execute the query asynchronously
            ApiFuture<QuerySnapshot> future = query.get();
            ApiFutures.addCallback(future, new ApiFutureCallback<QuerySnapshot>() {
                @Override
                public void onFailure(Throwable throwable) {
                    throwable.printStackTrace();
                    // Handle the failure gracefully, e.g., show an error message to the user
                }

                @Override
                public void onSuccess(QuerySnapshot querySnapshot) {
                    List<transactionInfoDisplay> transactions = new ArrayList<>();

                    // Process the query result
                    for (QueryDocumentSnapshot document : querySnapshot) {
                        String category = document.getString("Category");
                        String amount = document.getString("Amount");

                        transactions.add(new transactionInfoDisplay(category, amount));
                    }

                    // Update the TableView with the retrieved transactions
                    Platform.runLater(() -> {
                        Table_View.getItems().clear(); // Clear existing items
                        Table_View.getItems().addAll(transactions); // Add new items
                    });
                }
            }, MoreExecutors.directExecutor());
        } else {
            // Handle the case where username is empty or null
            // You can show a message to the user or perform any other action
        }
    }

}
