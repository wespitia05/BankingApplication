package com.example.bankingapplication;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static com.example.bankingapplication.userInfo.username;

public class transactionController {

    @FXML
    private TableColumn<Transaction, String> categoryCol;
    @FXML
    private TableColumn<Transaction, Double> costCol;
    @FXML
    private TableColumn<Transaction, String> dateCol;
    @FXML
    private TableColumn<Transaction, String> nameCol;
    @FXML
    private TableColumn<Transaction, String> itemName_COL;
    @FXML
    private TableView<transactionInfoDisplay> transactionTable;

    private Firestore firestore;


    public void setFirestore(Firestore firestore) {
        this.firestore = firestore;
    }


    @FXML
    private void initialize() {
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("Category"));
        costCol.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("Date"));
        itemName_COL.setCellValueFactory(new PropertyValueFactory<>("Name"));

        // Set cell value factory for the new column
        // Fetch user info to get the user's name
        Firestore db = main.fstore;
        DocumentReference userDocRef = db.collection("userinfo").document(username);
        ApiFuture<DocumentSnapshot> userFuture = userDocRef.get();

        // Fetch data from Firestore and populate the table
        ApiFuture<QuerySnapshot> transactionFuture = db.collection("userinfo").document(username).collection("transactions").get();

        transactionFuture.addListener(() -> {
            try {
                QuerySnapshot transactionSnapshot = transactionFuture.get();
                ObservableList<transactionInfoDisplay> transactions = FXCollections.observableArrayList();
                if (!transactionSnapshot.isEmpty()) {
                    // Fetch user's info to get the user's name
                    DocumentSnapshot userSnapshot = userFuture.get();
                    String userName = userSnapshot.getString("firstName") + " " + userSnapshot.getString("lastName"); //display username in the name colum

                    // Populate transaction details
                    transactionSnapshot.getDocuments().forEach(doc -> {
                        String name = doc.getString("Name"); ///get name of item bought
                        String category = doc.getString("Category");
                        String amount = doc.getString("Amount");
                        String date = doc.getString("Date");
                        transactions.add(new transactionInfoDisplay(name, category, amount, date));
                    });
                    Platform.runLater(() -> transactionTable.setItems(transactions));
                } else {
                    System.out.println("No transactions found for selected user.");
                    Platform.runLater(() -> transactionTable.setItems(FXCollections.observableArrayList()));
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }, Executors.newSingleThreadExecutor());
    }

    @FXML
    void handledashBoard_btn(ActionEvent event) {

    }

    @FXML
    void handlemyCard_btn(ActionEvent event) {

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

}
