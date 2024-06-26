package com.example.bankingapplication;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static com.example.bankingapplication.main.addDataToDB;
import static com.example.bankingapplication.main.stg;

public class employeeHomePageController extends employeeLoginController {
    @FXML
    private Label employeeName;
    @FXML
    private TableView <userInfoDisplay> userInfoTV;
    @FXML
    private TableColumn <userInfoDisplay, String> firstNameCol;
    @FXML
    private TableColumn <userInfoDisplay, String> lastNameCol;
    @FXML
    private TableColumn <userInfoDisplay, String> dobCol;
    @FXML
    private TableColumn <userInfoDisplay, String> usernameCol;
    @FXML
    private TableColumn <userInfoDisplay, String> passwordCol;
    @FXML
    private TableColumn <userInfoDisplay, String> checkingCol;
    @FXML
    private TableColumn <userInfoDisplay, String> savingsCol;
    @FXML
    private TableColumn <userInfoDisplay, String> addressCol;
    @FXML
    private TableColumn <userInfoDisplay, String> zipCodeCol;
    @FXML
    private TableColumn <userInfoDisplay, String> cardNumCol;
    @FXML
    private TableColumn <userInfoDisplay, String> cardExpCol;
    @FXML
    private TableColumn <userInfoDisplay, String> cardCVVCol;
    @FXML
    private TableColumn <userInfoDisplay, String> emailCol;
    @FXML
    private TableColumn <userInfoDisplay, String> phoneNumberCol;
    @FXML
    private TableView <transactionInfoDisplay> transactionTV;
    @FXML
    private TableColumn <transactionInfoDisplay, String> transactionNameCol;
    @FXML
    private TableColumn <transactionInfoDisplay, String> transactionCategoryCol;
    @FXML
    private TableColumn <transactionInfoDisplay, String> transactionAmountCol;
    @FXML
    private TableColumn <transactionInfoDisplay, String> transactionDateCol;
    @FXML
    private Button displayAllCustomersButton;
    @FXML
    private Button editCustomerButton;
    @FXML
    private Button deleteCustomerButton;
    @FXML
    private Button viewTransactionsButton;
    @FXML
    private Button viewCustomerButton;
    @FXML
    private RadioButton fromCheckingRB;
    @FXML
    private RadioButton fromSavingsRB;
    @FXML
    private RadioButton toCheckingRB;
    @FXML
    private RadioButton toSavingsRB;
    @FXML
    private ToggleGroup fromtg;
    @FXML
    private ToggleGroup totg;
    @FXML
    private Button startTransferButton;
    @FXML
    private TextField amountEnteredTF;
    @FXML
    private Button spendingPercentageButton;

    public void initialize () {
        System.out.println("initialize called");

        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<userInfoDisplay, String>("firstName"));
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<userInfoDisplay, String>("lastName"));
        dobCol.setCellValueFactory(
                new PropertyValueFactory<userInfoDisplay, String>("dob"));
        usernameCol.setCellValueFactory(
                new PropertyValueFactory<userInfoDisplay, String>("username"));
        passwordCol.setCellValueFactory(
                new PropertyValueFactory<userInfoDisplay, String>("password"));
        checkingCol.setCellValueFactory(
                new PropertyValueFactory<userInfoDisplay, String>("checking"));
        savingsCol.setCellValueFactory(
                new PropertyValueFactory<userInfoDisplay, String>("savings"));
        addressCol.setCellValueFactory(
                new PropertyValueFactory<userInfoDisplay, String>("address"));
        zipCodeCol.setCellValueFactory(
                new PropertyValueFactory<userInfoDisplay, String>("zipCode"));
        cardNumCol.setCellValueFactory(
                new PropertyValueFactory<userInfoDisplay, String>("cardNum"));
        cardExpCol.setCellValueFactory(
                new PropertyValueFactory<userInfoDisplay, String>("cardExp"));
        cardCVVCol.setCellValueFactory(
                new PropertyValueFactory<userInfoDisplay, String>("cardCVV"));
        emailCol.setCellValueFactory(
                new PropertyValueFactory<userInfoDisplay, String>("email"));
        phoneNumberCol.setCellValueFactory(
                new PropertyValueFactory<userInfoDisplay, String>("number"));

        transactionNameCol.setCellValueFactory(
                new PropertyValueFactory<transactionInfoDisplay, String>("name"));
        transactionCategoryCol.setCellValueFactory(
                new PropertyValueFactory<transactionInfoDisplay, String>("category"));
        transactionAmountCol.setCellValueFactory(
                new PropertyValueFactory<transactionInfoDisplay, String>("amount"));
        transactionDateCol.setCellValueFactory(
                new PropertyValueFactory<transactionInfoDisplay, String>("date"));

        fetchAndDisplayEmployeeDetails();

        fromtg = new ToggleGroup();
        fromCheckingRB.setToggleGroup(fromtg);
        fromSavingsRB.setToggleGroup(fromtg);

        totg = new ToggleGroup();
        toCheckingRB.setToggleGroup(totg);
        toSavingsRB.setToggleGroup(totg);

        fromtg.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue == fromCheckingRB && toCheckingRB.isSelected()) {
                    totg.selectToggle(null);
                } else if (newValue == fromSavingsRB && toSavingsRB.isSelected()) {
                    totg.selectToggle(null);
                }
            }
        });

        totg.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue == toCheckingRB && fromCheckingRB.isSelected()) {
                    fromtg.selectToggle(null);
                } else if (newValue == toSavingsRB && fromSavingsRB.isSelected()) {
                    fromtg.selectToggle(null);
                }
            }
        });
    }

    private void fetchAndDisplayEmployeeDetails() {
        if (employeeIDTextField != null && !employeeIDTextField.getText().isEmpty()) {
            int employeeID = Integer.parseInt(employeeIDTextField.getText());

            Firestore db = main.fstore;
            CollectionReference employeesRef = db.collection("employeeinfo");

            ApiFuture<QuerySnapshot> future = employeesRef.whereEqualTo("Employee ID", employeeID).get();
            future.addListener(() -> {
                try {
                    QuerySnapshot querySnapshot = future.get();
                    if (!querySnapshot.isEmpty()) {
                        DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                        String storedFirstName = document.getString("First Name");
                        String storedLastName = document.getString("Last Name");
                        Platform.runLater(() -> setEmployeeName(storedFirstName, storedLastName));
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }, Executors.newSingleThreadExecutor());
        }
    }

    public void setEmployeeName(String firstName, String lastName) {
        if (employeeName != null) {
            employeeName.setText(firstName + " " + lastName);
        }
    }

    public void handleDisplayAllCustomersButton () {
        System.out.println ("handleDisplayAllCustomersButton called");

        displayAllCustomers();
    }

    public void displayAllCustomers() {

        Firestore db = main.fstore;
        ApiFuture<QuerySnapshot> future = db.collection("userinfo").get();

        ObservableList<userInfoDisplay> data = FXCollections.observableArrayList();

        try {
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                data.add(new userInfoDisplay(
                        document.getString("First Name"),
                        document.getString("Last Name"),
                        document.getString("Date of Birth"),
                        document.getString("Username"),
                        document.getString("Password"),
                        document.getString("Checking"),
                        document.getString("Savings"),
                        document.getString("Address"),
                        document.getString("Zip Code"),
                        document.getString("Card Number"),
                        document.getString("Card Expiration Date"),
                        document.getString("Card CVV"),
                        document.getString("Email"),
                        document.getString("Phone Number"),
                        document.getId()
                ));
            }
            userInfoTV.setItems(data);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void handleEditCustomerButton() {
        System.out.println("handleEditCustomerButton called");
        userInfoDisplay selectedUser = userInfoTV.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            System.out.println("No user selected for editing.");
            return;
        }

        Dialog<userInfoDisplay> dialog = new Dialog<>();
        dialog.setTitle("Edit Customer");
        dialog.setHeaderText("Edit the selected customer's details");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField firstNameField = new TextField(selectedUser.getFirstName());
        TextField lastNameField = new TextField(selectedUser.getLastName());
        TextField usernameField = new TextField(selectedUser.getUsername());
        TextField dobField = new TextField(selectedUser.getDob());
        TextField passwordField = new TextField(selectedUser.getPassword());
        TextField checkingField = new TextField(selectedUser.getChecking());
        TextField savingsField = new TextField(selectedUser.getSavings());
        TextField addressField = new TextField(selectedUser.getAddress());
        TextField zipCodeField = new TextField(selectedUser.getZipCode());
        TextField cardNumField = new TextField(selectedUser.getCardNum());
        TextField cardExpField = new TextField(selectedUser.getCardExp());
        TextField cardCVVField = new TextField(selectedUser.getCardCVV());
        TextField emailField = new TextField(selectedUser.getEmail());
        TextField numberField = new TextField(selectedUser.getNumber());

        grid.add(new Label("First Name:"), 0, 0);
        grid.add(firstNameField, 1, 0);
        grid.add(new Label("Last Name:"), 0, 1);
        grid.add(lastNameField, 1, 1);
        grid.add(new Label("Address:"),0,2);
        grid.add(addressField,1,2);
        grid.add(new Label("Zip Code:"),0,3);
        grid.add(zipCodeField,1,3);
        grid.add(new Label("Date of Birth:"), 0, 4);
        grid.add(dobField, 1, 4);
        grid.add(new Label("Username:"),0,5);
        grid.add(usernameField,1,5);
        grid.add(new Label("Password:"),0,6);
        grid.add(passwordField,1,6);
        grid.add(new Label("Checking:"),0,7);
        grid.add(checkingField,1,7);
        grid.add(new Label("Username:"),0,8);
        grid.add(savingsField,1,8);
        grid.add(new Label("Card Number:"), 0, 9);
        grid.add(cardNumField, 1, 9);
        grid.add(new Label("Card Expiration Date:"), 0, 10);
        grid.add(cardExpField, 1, 10);
        grid.add(new Label("Card CVV:"), 0, 11);
        grid.add(cardCVVField, 1, 11);
        grid.add(new Label("Email:"),0, 12);
        grid.add(emailField,1,12);
        grid.add(new Label("Phone Number:"), 0, 13);
        grid.add(numberField, 1, 13);

        dialog.getDialogPane().setContent(grid);
        Platform.runLater(firstNameField::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return new userInfoDisplay(
                        firstNameField.getText(),
                        lastNameField.getText(),
                        dobField.getText(),
                        usernameField.getText(),
                        passwordField.getText(),
                        checkingField.getText(),
                        savingsField.getText(),
                        addressField.getText(),
                        zipCodeField.getText(),
                        cardNumField.getText(),
                        cardExpField.getText(),
                        cardCVVField.getText(),
                        emailField.getText(),
                        numberField.getText(),
                        selectedUser.getId()
                );
            }
            return null;
        });

        Optional<userInfoDisplay> result = dialog.showAndWait();
        result.ifPresent(newUserInfo -> {
            updateCustomerTV(newUserInfo);
            updateCustomerInFirestore(newUserInfo);
        });
    }

    private void updateCustomerTV(userInfoDisplay updatedUser) {
        int index = userInfoTV.getItems().indexOf(userInfoTV.getSelectionModel().getSelectedItem());
        if (index >= 0) {
            userInfoTV.getItems().set(index, updatedUser);
            System.out.println("Update table view successful");
        }
    }

    private void updateCustomerInFirestore(userInfoDisplay updatedUser) {
        Firestore db = main.fstore;
        DocumentReference docRef = db.collection("userinfo").document(updatedUser.getId());

        Map<String, Object> updates = new HashMap<>();
        updates.put("First Name", updatedUser.getFirstName());
        updates.put("Last Name", updatedUser.getLastName());
        updates.put("Date of Birth", updatedUser.getDob());
        updates.put("Username", updatedUser.getUsername());
        updates.put("Password", updatedUser.getPassword());
        updates.put("Checking", updatedUser.getChecking());
        updates.put("Savings", updatedUser.getSavings());
        updates.put("Address", updatedUser.getAddress());
        updates.put("Zip Code", updatedUser.getZipCode());
        updates.put("Card Number", updatedUser.getCardNum());
        updates.put("Card Expiration Date", updatedUser.getCardExp());
        updates.put("Card CVV", updatedUser.getCardCVV());
        updates.put("Email", updatedUser.getEmail());
        updates.put("Phone Number", updatedUser.getNumber());

        docRef.set(updates, SetOptions.merge()).addListener(() -> {
            System.out.println("Database updated successfully.");
        }, Executors.newSingleThreadExecutor());
    }

    public void handleDeleteCustomerButton() {
        System.out.println("handleDeleteCustomerButton called");

        userInfoDisplay selectedUser = userInfoTV.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Removal");
            alert.setContentText("Are you sure you want to remove " + selectedUser.getFirstName() + " " + selectedUser.getLastName() + " " +
                    "from the table view and database?");
            alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    deleteCustomerFromFirestore(selectedUser.getId());
                    userInfoTV.getItems().remove(selectedUser);
                    System.out.println("Removed: " + selectedUser.getFirstName() + " " + selectedUser.getLastName());
                }
                else {
                    System.out.println("No user selected.");
                }
        }
    }

    private void deleteCustomerFromFirestore(String id) {
        if (id == null || id.isEmpty()) {
            System.out.println("Invalid document ID");
            return;
        }

        Firestore db = main.fstore;
        DocumentReference docRef = db.collection("userinfo").document(id);
        ApiFuture<WriteResult> future = docRef.delete();
        future.addListener(() -> {
            try {
                WriteResult result = future.get();
                System.out.println("Delete successful: " + result.getUpdateTime());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }, Executors.newSingleThreadExecutor());
    }

    public void handleViewCustomerButton () {
        System.out.println ("handleViewCustomerButton called");

        userInfoDisplay selectedUser = userInfoTV.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            System.out.println("No user selected to view");
            return;
        }

        Dialog<userInfo> dialog = new Dialog<>();
        dialog.setTitle("Customer Information");
        dialog.setHeaderText("Displaying customer information below");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 100, 10, 10));

        Label firstName = new Label(selectedUser.getFirstName());
        Label lastName = new Label(selectedUser.getLastName());
        Label address = new Label(selectedUser.getAddress());
        Label zipCode = new Label(selectedUser.getZipCode());
        Label dob = new Label(selectedUser.getDob());
        Label username = new Label(selectedUser.getUsername());
        Label password = new Label(selectedUser.getPassword());
        Label checking = new Label(selectedUser.getChecking());
        Label savings = new Label(selectedUser.getSavings());
        Label cardNum = new Label(selectedUser.getCardNum());
        Label cardExp = new Label(selectedUser.getCardExp());
        Label cardCVV = new Label(selectedUser.getCardCVV());
        Label email = new Label(selectedUser.getEmail());
        Label number = new Label(selectedUser.getNumber());

        grid.add(new Label("First Name:"), 0, 0);
        grid.add(firstName, 1, 0);
        grid.add(new Label("Last Name:"), 0, 1);
        grid.add(lastName, 1, 1);
        grid.add(new Label("Address:"),0,2);
        grid.add(address,1,2);
        grid.add(new Label("Zip Code:"),0,3);
        grid.add(zipCode,1,3);
        grid.add(new Label("Date of Birth:"), 0, 4);
        grid.add(dob, 1, 4);
        grid.add(new Label("Username:"),0,5);
        grid.add(username,1,5);
        grid.add(new Label("Password:"),0,6);
        grid.add(password,1,6);
        grid.add(new Label("Checking:"),2,0);
        grid.add(checking,3,0);
        grid.add(new Label("Savings:"),2,1);
        grid.add(savings,3,1);
        grid.add(new Label("Card Number:"), 2, 2);
        grid.add(cardNum, 3, 2);
        grid.add(new Label("Card Expiration Date:"), 2, 3);
        grid.add(cardExp, 3, 3);
        grid.add(new Label("Card CVV:"), 2, 4);
        grid.add(cardCVV, 3, 4);
        grid.add(new Label("Email:"),2, 5);
        grid.add(email, 3, 5);
        grid.add(new Label("Phone Number"), 2, 6);
        grid.add(number, 3, 6);

        dialog.getDialogPane().setContent(grid);

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType editButton = new ButtonType("Edit Customer", ButtonBar.ButtonData.OTHER);
        ButtonType addTransactionButton = new ButtonType("Add Transaction", ButtonBar.ButtonData.OTHER);
        ButtonType exitButton = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);

        dialog.getDialogPane().getButtonTypes().addAll(okButton, editButton);
        dialog.getDialogPane().getButtonTypes().addAll(addTransactionButton, exitButton);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == editButton) {
                handleEditCustomerButton();
            } else if (dialogButton == exitButton) {
                dialog.close();
            } else if (dialogButton == addTransactionButton) {
                handleAddTransactionButton();
            }
            return null;
        });

        dialog.showAndWait();
    }

    public void handleAddTransactionButton () {
        System.out.println("handleAddTransactionButton");

        userInfoDisplay selectedUser = userInfoTV.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            System.out.println("no user selected to add transaction to");
            return;
        }

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
                return new transactionInfoDisplay(
                        nameField.getText(),
                        categoryComboBox.getValue(),
                        amountField.getText(),
                        formattedDate
                );
            }
            return null;
        });

        Optional<transactionInfoDisplay> result = dialog.showAndWait();
        result.ifPresent(newTransaction -> {
            updateTransactionTV(newTransaction);
            addTransactionToFirestore(newTransaction, selectedUser.getUsername());
        });
    }

    private void updateTransactionTV(transactionInfoDisplay newTransaction) {
        ObservableList<transactionInfoDisplay> currentData = transactionTV.getItems();
        currentData.add(newTransaction);
        transactionTV.setItems(currentData);
    }

    private void addTransactionToFirestore(transactionInfoDisplay newTransaction, String username) {
        DocumentReference userDocRef = main.fstore.collection("userinfo").document(username);
        CollectionReference transactionsRef = userDocRef.collection("transactions");

        Map<String, Object> transactionData = new HashMap<>();
        transactionData.put("Name", newTransaction.getName());
        transactionData.put("Category", newTransaction.getCategory());
        transactionData.put("Amount", newTransaction.getAmount());
        transactionData.put("Date", newTransaction.getDate()); // Ensure the date format is consistent with your database

        ApiFuture<DocumentReference> future = transactionsRef.add(transactionData);
        future.addListener(() -> {
            try {
                DocumentReference docRef = future.get();
                System.out.println("Transaction added with ID: " + docRef.getId());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }, Executors.newSingleThreadExecutor());
    }

    public void handleViewTransactionsButton() {
        System.out.println("handleViewTransactionsButton called");
        userInfoDisplay selectedUser = userInfoTV.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            System.out.println("no user selected to view transactions");
            return;
        }

        String username = selectedUser.getUsername();  // Assuming getUsername() correctly returns the document ID used in Firestore

        Firestore db = main.fstore;
        ApiFuture<QuerySnapshot> future = db.collection("userinfo").document(username).collection("transactions").get();

        future.addListener(() -> {
            try {
                QuerySnapshot querySnapshot = future.get();
                ObservableList<transactionInfoDisplay> transactions = FXCollections.observableArrayList();
                if (!querySnapshot.isEmpty()) {
                    querySnapshot.getDocuments().forEach(doc -> {
                        String name = doc.getString("Name");
                        String category = doc.getString("Category");
                        String amount = doc.getString("Amount");
                        String date = doc.getString("Date");
                        transactions.add(new transactionInfoDisplay(name, category, amount, date));
                    });
                    Platform.runLater(() -> transactionTV.setItems(transactions));
                } else {
                    System.out.println("No transactions found for selected user.");
                    Platform.runLater(() -> transactionTV.setItems(FXCollections.observableArrayList())); // Clear previous entries
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }, Executors.newSingleThreadExecutor());
    }

    public void handleStartTransferButton () {
        System.out.println("handleStartTransferButton called");

        userInfoDisplay selectedUser = userInfoTV.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            System.out.println("No user selected.");
            return;
        }

        RadioButton selectedFrom = (RadioButton) fromtg.getSelectedToggle();
        RadioButton selectedTo = (RadioButton) totg.getSelectedToggle();
        if (selectedFrom == null || selectedTo == null) {
            System.out.println("Both source and destination accounts must be selected.");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountEnteredTF.getText());
            if (amount <= 0) {
                throw new NumberFormatException("Amount must be positive.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount entered.");
            return;
        }

        int fromAmount = Integer.parseInt(selectedFrom == fromCheckingRB ? selectedUser.getChecking() : selectedUser.getSavings());
        int toAmount = Integer.parseInt(selectedTo == toCheckingRB ? selectedUser.getChecking() : selectedUser.getSavings());

        if (fromAmount < amount) {
            System.out.println("Insufficient funds.");
            return;
        }

        fromAmount -= amount;
        toAmount += amount;

        if (selectedFrom == fromCheckingRB) {
            selectedUser.setChecking(String.valueOf(fromAmount));
        } else {
            selectedUser.setSavings(String.valueOf(fromAmount));
        }

        if (selectedTo == toCheckingRB) {
            selectedUser.setChecking(String.valueOf(toAmount));
        } else {
            selectedUser.setSavings(String.valueOf(toAmount));
        }

        updateCustomerTV(selectedUser);
        updateCustomerInFirestore(selectedUser);
    }

    public void handleSpendingPercentageButton () {
        System.out.println("handleSpendingPercentageButton called");

        userInfoDisplay selectedUser = userInfoTV.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            System.out.println("No user selected.");
            return;
        }

        fetchAndDisplaySpendingPercentage(selectedUser.getUsername());
    }

    private void fetchAndDisplaySpendingPercentage(String username) {
        DocumentReference userDocRef = main.fstore.collection("userinfo").document(username);
        ApiFuture<QuerySnapshot> future = userDocRef.collection("transactions").get();

        future.addListener(() -> {
            try {
                QuerySnapshot querySnapshot = future.get();
                Map<String, Double> categoryTotals = new HashMap<>();
                double totalSpent = 0;

                for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                    String category = doc.getString("Category");
                    double amount = Double.parseDouble(doc.getString("Amount"));
                    categoryTotals.put(category, categoryTotals.getOrDefault(category, 0.0) + amount);
                    totalSpent += amount;
                }

                double finalTotalSpent = totalSpent;
                Platform.runLater(() -> displayPieChart(categoryTotals, finalTotalSpent));
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }, Executors.newSingleThreadExecutor());
    }

    private void displayPieChart(Map<String, Double> categoryTotals, double totalSpent) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        categoryTotals.forEach((category, sum) -> {
            double percentage = (sum / totalSpent) * 100;
            pieChartData.add(new PieChart.Data(category + String.format(" (%.1f%%)", percentage), sum));
        });

        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Spending Percentage");

        Dialog<Void> dialog = new Dialog<>();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stg);
        dialog.setTitle("Spending Pie Chart");

        DialogPane dialogPane = new DialogPane();
        dialogPane.setContent(pieChart);
        dialog.setDialogPane(dialogPane);

        ButtonType closeButton = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialogPane.getButtonTypes().add(closeButton);

        dialog.showAndWait();
    }

    public void handleSignOutOnMouseClicked () throws IOException {
        System.out.println("handleSignOutOnMouseClicked called");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("employeeLogin.fxml"));
        Parent root = loader.load();
        stg.getScene().setRoot(root);
    }

    public void handleAddNewCustomerButton() {
        Dialog<userInfoDisplay> dialog = new Dialog<>();
        dialog.setTitle("Add New Customer");
        dialog.setHeaderText("Enter new customer details:");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField emailField = new TextField();
        emailField.setEditable(false);
        TextField usernameField = new TextField();
        usernameField.setEditable(false);
        lastNameField.textProperty().addListener((obs, oldVal, newVal) -> {
            emailField.setText(generateEmail(firstNameField.getText(), newVal));
        });
        lastNameField.textProperty().addListener((obs, oldVal, newVal) -> {
            usernameField.setText(generateUsername(firstNameField.getText(), newVal));
        });
        TextField passwordField = new TextField();
        passwordField.setText("temporarypassword");
        passwordField.setEditable(false);
        TextField checkingField = new TextField();
        checkingField.setText("0");
        checkingField.setEditable(false);
        TextField savingsField = new TextField();
        savingsField.setText("0");
        savingsField.setEditable(false);
        TextField addressField = new TextField();
        TextField zipCodeField = new TextField();
        String cardNum;
        do {
            cardNum = generateCardNumber();
        } while (cardNumExists(cardNum));
        TextField cardNumField = new TextField(cardNum);
        cardNumField.setEditable(false);
        TextField cardExpField = new TextField(generateExpirationDate());
        cardExpField.setEditable(false);
        TextField cardCVVField = new TextField(generateCVV());
        cardCVVField.setEditable(false);
        TextField numberField = new TextField();
        TextField dobField = new TextField();

        grid.addRow(0, new Label("First Name:"), firstNameField);
        grid.addRow(1, new Label("Last Name:"), lastNameField);
        grid.addRow(2, new Label("Address:"), addressField);
        grid.addRow(3, new Label("Zip Code:"), zipCodeField);
        grid.addRow(4, new Label("Date of Birth:"), dobField);
        grid.addRow(5, new Label("Username:"), usernameField);
        grid.addRow(6, new Label("Password:"), passwordField);
        grid.addRow(7, new Label("Checking:"), checkingField);
        grid.addRow(8, new Label("Savings:"), savingsField);
        grid.addRow(9, new Label("Card Number:"), cardNumField);
        grid.addRow(10, new Label("Card Expiration Date:"), cardExpField);
        grid.addRow(11, new Label("Card CVV:"), cardCVVField);
        grid.addRow(12, new Label("Email:"), emailField);
        grid.addRow(13, new Label("Phone Number:"), numberField);

        dialog.getDialogPane().setContent(grid);
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                // Format the date of birth to mm/dd/yy
                String formattedDob = formatDob(dobField.getText());
                // Format the phone number to xxx-xxx-xxxx
                String formattedPhoneNumber = formatPhoneNumber(numberField.getText());

                return new userInfoDisplay(
                        firstNameField.getText(),
                        lastNameField.getText(),
                        formattedDob,
                        usernameField.getText(),
                        passwordField.getText(),
                        checkingField.getText(),
                        savingsField.getText(),
                        addressField.getText(),
                        zipCodeField.getText(),
                        cardNumField.getText(),
                        cardExpField.getText(),
                        cardCVVField.getText(),
                        emailField.getText(),
                        formattedPhoneNumber,
                        null
                );
            }
            return null;
        });

        Optional<userInfoDisplay> result = dialog.showAndWait();
        result.ifPresent(this::addNewCustomer);
    }

    private void addNewCustomer(userInfoDisplay newCustomer) {
        ObservableList<userInfoDisplay> currentData = userInfoTV.getItems();
        currentData.add(newCustomer);
        userInfoTV.setItems(currentData);
        addDataToDB(newCustomer.getFirstName(), newCustomer.getLastName(), newCustomer.getAddress(),
                newCustomer.getZipCode(), newCustomer.getDob(), newCustomer.getUsername(), newCustomer.getPassword(),
                newCustomer.getChecking(), newCustomer.getSavings(), newCustomer.getCardNum(), newCustomer.getCardExp(),
                newCustomer.getCardCVV(), newCustomer.getEmail(), newCustomer.getNumber());
    }

    public static void addDataToDB(String firstName, String lastName, String address,
                                   String zipCode, String dob, String username, String password,
                                   String checking, String savings, String cardNum, String cardExp,
                                   String cardCVV, String email, String number) {

        // Create document reference
        DocumentReference docRef = main.fstore.collection("userinfo").document(username);

        // Create data object
        Map<String, Object> data = new HashMap<>();
        data.put("First Name", firstName);
        data.put("Last Name", lastName);
        data.put("Address", address);
        data.put("Zip Code", zipCode);
        data.put("Date of Birth", dob);
        data.put("Username", username);
        data.put("Password", password);
        data.put("Checking", checking);
        data.put("Savings", savings);
        data.put("Card Number", cardNum);
        data.put("Card Expiration Date", cardExp);
        data.put("Card CVV", cardCVV);
        data.put("Email", email);
        data.put("Phone Number", number);

        // Add data to document
        ApiFuture<WriteResult> future = docRef.set(data, SetOptions.merge());

        try {
            // Wait for the result
            WriteResult result = future.get();
            System.out.println("Data added/updated at: " + result.getUpdateTime());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private String generateCardNumber() {
        String prefix = "223344556677";
        Random random = new Random();
        int lastFourDigits = random.nextInt(10000);
        return prefix + String.format("%04d", lastFourDigits);
    }

    private boolean cardNumExists(String cardNum) {
        try {
            Firestore db = main.fstore;
            Query query = db.collection("userinfo").whereEqualTo("Card Number", cardNum);
            QuerySnapshot querySnapshot = query.get().get();
            if (!querySnapshot.isEmpty()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String generateExpirationDate() {
        Random random = new Random();
        int month = random.nextInt(12) + 1;
        return String.format("%02d", month) + "/26";
    }

    private String generateCVV() {
        Random random = new Random();
        int cvv = random.nextInt(900) + 100;
        return String.valueOf(cvv);
    }

    private String generateEmail(String firstName, String lastName) {
        if (lastName.isEmpty() || firstName.isEmpty()) {
            return "";
        }
        return lastName.toLowerCase() + firstName.substring(0, 1).toLowerCase() + "@unitybank.com";
    }

    private String generateUsername(String firstName, String lastName) {
        if (lastName.isEmpty() || firstName.isEmpty()) {
            return "";
        }
        return lastName.toLowerCase() + firstName.substring(0, 1).toLowerCase();
    }

    private String formatDob(String dob) {
        try {
            // Assume the input could be in a variety of formats, try to parse it
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("[MM/dd/yyyy][M/d/yyyy][yyyy-MM-dd][MMddyy][MMddyyyy][MMdyy][Mdyy][Mdyyyy][Mddyy][Mddyyy]");
            LocalDate date = LocalDate.parse(dob, inputFormatter);
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
            return date.format(outputFormatter);
        } catch (DateTimeParseException e) {
            // If parsing fails, return the original input or handle the case appropriately
            System.err.println("Invalid date format, please enter date in MM/dd/yyyy or M/d/yyyy format.");
            return dob;
        }
    }

    // Method to format the phone number to xxx-xxx-xxxx
    private String formatPhoneNumber(String number) {
        // Remove all non-digit characters from the string
        String digits = number.replaceAll("\\D+", "");
        if (digits.length() == 10) { // Ensure the number has exactly 10 digits
            // Reformat to the desired style
            return String.format("%s-%s-%s", digits.substring(0, 3), digits.substring(3, 6), digits.substring(6));
        } else {
            // If the number of digits is incorrect, return the original or handle the case appropriately
            System.err.println("Invalid phone number, ensure it has exactly 10 digits.");
            return number;
        }
    }
}

