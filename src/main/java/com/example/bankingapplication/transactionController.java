package com.example.bankingapplication;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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

        Firestore db = main.fstore;
        DocumentReference userDocRef = db.collection("userinfo").document(username);
        ApiFuture<DocumentSnapshot> userFuture = userDocRef.get();

        ApiFuture<QuerySnapshot> transactionFuture = db.collection("userinfo").document(username).collection("transactions").get();

        transactionFuture.addListener(() -> {
            try {
                QuerySnapshot transactionSnapshot = transactionFuture.get();
                ObservableList<transactionInfoDisplay> transactions = FXCollections.observableArrayList();
                if (!transactionSnapshot.isEmpty()) {
                    DocumentSnapshot userSnapshot = userFuture.get();
                    String userName = userSnapshot.getString("firstName") + " " + userSnapshot.getString("lastName"); //display username in the name colum

                    transactionSnapshot.getDocuments().forEach(doc -> {
                        String name = doc.getString("Name");
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
    void handleAddTranactions_btn(ActionEvent event) {
        Dialog<transactionInfoDisplay> dialog = new Dialog<>();
        dialog.setTitle("Add New Transaction");
        dialog.setHeaderText("Enter the details of the new transaction");

        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nameField = new TextField();
        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll("Food", "Bills", "Entertainment", "Health", "Streaming", "Retail", "Groceries", "Transportation");
        TextField amountField = new TextField();
        DatePicker datePicker = new DatePicker();

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Category:"), 0, 1);
        grid.add(categoryComboBox, 1, 1);
        grid.add(new Label("Amount:"), 0, 2);
        grid.add(amountField, 1, 2);
        grid.add(new Label("Date:"), 0, 3);
        grid.add(datePicker, 1, 3);

        dialog.getDialogPane().setContent(grid);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                String formattedDate = datePicker.getValue().format(dateFormatter);
                transactionInfoDisplay newTransaction = new transactionInfoDisplay(
                        nameField.getText(),
                        categoryComboBox.getValue(),
                        amountField.getText(),
                        formattedDate
                );
                addTransactionToDatabase(newTransaction);  // Method to add transaction to Firestore and update the TableView
                return newTransaction;
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void addTransactionToDatabase(transactionInfoDisplay transaction) {

        Map<String, Object> transactionData = new HashMap<>();
        transactionData.put("Name", transaction.getName());
        transactionData.put("Category", transaction.getCategory());
        transactionData.put("Amount", transaction.getAmount());
        transactionData.put("Date", transaction.getDate());

        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<DocumentReference> future = db.collection("userinfo").document(username)
                .collection("transactions").add(transactionData);

        future.addListener(() -> {
            try {
                DocumentReference docRef = future.get();
                Platform.runLater(() -> {
                    transactionTable.getItems().add(transaction);
                    System.out.println("Transaction added with ID: " + docRef.getId());
                });
            } catch (InterruptedException | ExecutionException e) {
                System.err.println("Error adding transaction: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }, Executors.newSingleThreadExecutor());
    }

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
