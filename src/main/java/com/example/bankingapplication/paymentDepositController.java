package com.example.bankingapplication;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;


public class paymentDepositController {
    @FXML
    private ComboBox<String> depositAccountSelection1;
    @FXML
    private TextField depositAmountField;
    @FXML
    private CheckBox subscriptionCheckbox;
    @FXML
    private CheckBox rentCheckbox;
    @FXML
    private CheckBox utilitiesCheckbox;
    @FXML
    private CheckBox entertainmentCheckbox;
    @FXML
    private TextField paymentAmountField;


    @FXML
    private ComboBox<String> paymentAccountSelection;

    @FXML
    private Label balanceLabel;

    @FXML
    private ComboBox<String> depositAccountSelection;

    @FXML
    private Label balanceLabel1;
    @FXML
    private Label maskedCardNumLabel;

    @FXML
    private Label expDateLabel;

    @FXML
    private Label cvvLabel;

    @FXML
    private Label cardHolderNameLabel;

    private String selectedPaymentDescription;

    @FXML
    private void handledashBoard_btn(ActionEvent event) throws IOException {
        System.out.println("Dashboard clicked");


        // Load the FXML file and get the root and controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homePagedemo.fxml"));
        Parent root = loader.load(); // This is the root node of your new scene, loaded from FXML
        homePageController controller = loader.getController();

        // Set data using methods in your controller
        controller.setUserFullName(userInfo.getFirstName(), userInfo.getLastName());
        controller.updateCheckingBalanceInFirestore(Double.parseDouble(userInfo.getChecking()));
        controller.updateSavingsBalanceInFirestore(Double.parseDouble(userInfo.getSavings()));
        controller.setBalances(userInfo.getChecking(), userInfo.getSavings());
        controller.setUsername(userInfo.getUsername());

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
        controller.setBalances(userInfo.getChecking(), userInfo.getSavings());
        controller.setUsername(userInfo.getUsername());

        // Set the scene on the current stage
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handletranaction_btn(ActionEvent event) throws IOException {
        System.out.println("Transactions clicked");

        // Load the FXML file and get the root and controller for the transactions view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("transactions.fxml"));
        Parent root = loader.load(); // This is the root node of your new scene, loaded from FXML
        transactionController controller = loader.getController();

        //controller.setUsername(userInfo.getUsername());

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handlepayment_btn(ActionEvent event) throws IOException {
        System.out.println("Payments clicked");

        // Load the FXML file and get the root and controller for the transactions view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("paymentDeposit.fxml"));
        Parent root = loader.load(); // This is the root node of your new scene, loaded from FXML
        paymentDepositController controller = loader.getController();

        controller.setUsername(userInfo.getUsername());

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handlereports_btn(ActionEvent event) {
        System.out.println("Reports clicked");
    }

    @FXML
    private void handleprofile_btn(ActionEvent event) throws IOException {
        System.out.println("Profiles clicked");

        // Load the FXML file and get the root and controller for the transactions view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("profile.fxml"));
        Parent root = loader.load(); // This is the root node of your new scene, loaded from FXML
        updateProfileController controller = loader.getController();

        controller.setUsername(userInfo.getUsername());

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handlesettings_btn(ActionEvent event) {
        System.out.println("Settings clicked");
    }

    @FXML
    public void initialize() {

        subscriptionCheckbox.setOnAction(event -> handleCheckboxSelection(subscriptionCheckbox));
        rentCheckbox.setOnAction(event -> handleCheckboxSelection(rentCheckbox));
        utilitiesCheckbox.setOnAction(event -> handleCheckboxSelection(utilitiesCheckbox));
        entertainmentCheckbox.setOnAction(event -> handleCheckboxSelection(entertainmentCheckbox));

        depositAccountSelection.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if ("Checking".equals(newValue)) {
                depositAccountSelection1.setItems(FXCollections.observableArrayList("Savings"));
            } else if ("Savings".equals(newValue)) {
                depositAccountSelection1.setItems(FXCollections.observableArrayList("Checking"));
            }
            depositAccountSelection1.getSelectionModel().selectFirst();
        });
    }

    private void handleCheckboxSelection(CheckBox selectedCheckbox) {

        if (selectedCheckbox.isSelected()) {
            subscriptionCheckbox.setSelected(selectedCheckbox == subscriptionCheckbox);
            rentCheckbox.setSelected(selectedCheckbox == rentCheckbox);
            utilitiesCheckbox.setSelected(selectedCheckbox == utilitiesCheckbox);
            entertainmentCheckbox.setSelected(selectedCheckbox == entertainmentCheckbox);
            selectedPaymentDescription = selectedCheckbox.getText();
        } else {
            selectedPaymentDescription = null;
        }
    }

    @FXML
    private void handlePaymentSelection() {
        userInfo user = userInfo.getInstance();
        String selectedAccount = paymentAccountSelection.getValue();

        if (selectedAccount != null) {
            if ("Checking".equals(selectedAccount)) {
                balanceLabel.setText("Checking Balance: $" + user.getChecking());
                maskedCardNumLabel.setText("**** **** **** " + user.getCardNum().substring(user.getCardNum().length() - 4));
                expDateLabel.setText(user.getCardExp());
                cvvLabel.setText(user.getCardCVV());
                cardHolderNameLabel.setText(user.getFirstName() + " " + user.getLastName());
            } else if ("Savings".equals(selectedAccount)) {
                balanceLabel.setText("Savings Balance: $" + user.getSavings());
                maskedCardNumLabel.setText("**** **** **** " + user.getCardNum().substring(user.getCardNum().length() - 4));
                expDateLabel.setText(user.getCardExp());
                cvvLabel.setText(user.getCardCVV());
                cardHolderNameLabel.setText(user.getFirstName() + " " + user.getLastName());
            }
        }
    }


    @FXML
    private void handleDepositSelection() {
        userInfo user = userInfo.getInstance();
        String selectedAccount = depositAccountSelection.getValue();

        if (selectedAccount != null) {
            if ("Checking".equals(selectedAccount)) {
                balanceLabel1.setText("Checking Balance: $" + user.getChecking());
            } else if ("Savings".equals(selectedAccount)) {
                balanceLabel1.setText("Savings Balance: $" + user.getSavings());
            }
        }
    }

    @FXML
    private void handleDeposit() {
        userInfo user = userInfo.getInstance();
        String fromAccount = depositAccountSelection.getValue();
        String toAccount = depositAccountSelection1.getValue();
        String amountText = depositAmountField.getText();

        if (fromAccount == null || toAccount == null || amountText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Incomplete Information", "Please fill in all fields.");
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);


            if (("Checking".equals(fromAccount) && amount > Double.parseDouble(user.getChecking())) ||
                    ("Savings".equals(fromAccount) && amount > Double.parseDouble(user.getSavings()))) {
                showAlert(Alert.AlertType.ERROR, "Insufficient Amount", "The deposit amount exceeds the available balance.");
                return;
            }


            if ("Checking".equals(fromAccount)) {
                user.setChecking(String.valueOf(Double.parseDouble(user.getChecking()) - amount));
            } else if ("Savings".equals(fromAccount)) {
                user.setSavings(String.valueOf(Double.parseDouble(user.getSavings()) - amount));
            }

            if ("Checking".equals(toAccount)) {
                user.setChecking(String.valueOf(Double.parseDouble(user.getChecking()) + amount));
            } else if ("Savings".equals(toAccount)) {
                user.setSavings(String.valueOf(Double.parseDouble(user.getSavings()) + amount));
            }


            depositAmountField.clear();
            depositAccountSelection.getSelectionModel().clearSelection();
            depositAccountSelection1.getSelectionModel().clearSelection();
            balanceLabel1.setText("");


            showAlert(Alert.AlertType.INFORMATION, "Deposit Successful", "Deposit of: $" + amount + "\nFrom: " + fromAccount + "\nTo: " + toAccount );


        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Amount", "Please enter a valid numeric amount.");
        }
    }

    @FXML
    private void handlePayment() {
        userInfo user = userInfo.getInstance();
        String selectedAccount = paymentAccountSelection.getValue();

        if (selectedPaymentDescription == null || paymentAmountField.getText().isEmpty() || selectedAccount == null) {
            showAlert(Alert.AlertType.ERROR, "Incomplete Information", "Please fill in all fields.");
            return;
        }

        try {
            double amount = Double.parseDouble(paymentAmountField.getText().trim());
            if (("Checking".equals(selectedAccount) && amount > Double.parseDouble(user.getChecking())) ||
                    ("Savings".equals(selectedAccount) && amount > Double.parseDouble(user.getSavings()))) {
                showAlert(Alert.AlertType.ERROR, "Insufficient Amount", "The payment amount exceeds the available balance.");
                return;
            }


            if ("Checking".equals(selectedAccount)) {
                user.setChecking(String.valueOf(Double.parseDouble(user.getChecking()) - amount));
            } else if ("Savings".equals(selectedAccount)) {
                user.setSavings(String.valueOf(Double.parseDouble(user.getSavings()) - amount));
            }

            paymentAmountField.clear();
            paymentAccountSelection.getSelectionModel().clearSelection();
            subscriptionCheckbox.setSelected(false);
            rentCheckbox.setSelected(false);
            utilitiesCheckbox.setSelected(false);
            entertainmentCheckbox.setSelected(false);
            balanceLabel.setText("");
            maskedCardNumLabel.setText("");
            expDateLabel.setText("");
            cvvLabel.setText("");
            cardHolderNameLabel.setText("");


            String billingAddress = user.getAddress() + ", " + user.getZipCode();
            String successMessage = "Payment To: " + selectedPaymentDescription + "\nAmount: $" + amount +
                    "\n\nBilling Address:\n" + billingAddress;
            showAlert(Alert.AlertType.INFORMATION, "Payment Successful", successMessage);

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Amount", "Please enter a valid numeric amount.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    public void setUsername(String username) {
    }
}

