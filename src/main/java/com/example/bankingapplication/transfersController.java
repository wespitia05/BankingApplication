package com.example.bankingapplication;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.concurrent.ExecutionException;



public class transfersController {
    @FXML
    private ComboBox<String> accountSelection;

    @FXML
    private Label balanceLabel;



    @FXML
    private TextField recipientNo;

    @FXML
    private TextField transferAmountField;

    @FXML
    private TextField descriptionField;



    @FXML
    private void handledashBoard_btn(ActionEvent event) throws IOException {
        System.out.println("Dashboard clicked");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("homePagedemo.fxml"));
        Parent root = loader.load();
        homePageController controller = loader.getController();

        controller.setUserFullName(userInfo.getFirstName(), userInfo.getLastName());
        controller.updateCheckingBalanceInFirestore(Double.parseDouble(userInfo.getChecking()));
        controller.updateSavingsBalanceInFirestore(Double.parseDouble(userInfo.getSavings()));
        controller.setBalances(userInfo.getChecking(), userInfo.getSavings());

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handlemyCard_btn(ActionEvent event) throws IOException {
        System.out.println("My Cards clicked");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("myCards.fxml"));
        Parent root = loader.load();
        myCardController controller = loader.getController();

        controller.setUserFullName(userInfo.getFirstName() + " " + userInfo.getLastName());
        //controller.setBalances(userInfo.getChecking(), userInfo.getSavings());

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handletranaction_btn(ActionEvent event) throws IOException {
        System.out.println("Transactions clicked");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("transactions.fxml"));
        Parent root = loader.load();
        transactionController controller = loader.getController();

        controller.setUsername(userInfo.getUsername());

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handlepayment_btn(ActionEvent event) throws IOException {
        System.out.println("Payment clicked");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("paymentDeposit.fxml"));
        Parent root = loader.load();
        paymentDepositController controller = loader.getController();

        controller.setUsername(userInfo.getUsername());

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleprofile_btn(ActionEvent event)throws IOException {
        System.out.println("Profiles clicked");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("profile.fxml"));
        Parent root = loader.load(); // This is the root node of your new scene, loaded from FXML

        // Set the scene on the current stage
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void handlesettings_btn(ActionEvent event) {
        System.out.println("Settings clicked");
    }

    //////////////////End of Settinghandlers //////////////////////////


    @FXML
    public void handleSelection(ActionEvent event) {
        userInfo user = userInfo.getInstance();
        String selectedAccount = accountSelection.getValue();

        if (selectedAccount != null) {
            if ("Checking".equals(selectedAccount)) {
                balanceLabel.setText("Checking Balance: $" + user.getChecking());
            } else if ("Savings".equals(selectedAccount)) {
                balanceLabel.setText("Savings Balance: $" + user.getSavings());
            }
        }
    }


    public void handleTransfer(ActionEvent event) {
        try {
            double transferAmount = Double.parseDouble(transferAmountField.getText());
            String description = descriptionField.getText();
            String recipientPhone = recipientNo.getText();
            String selectedAccount = accountSelection.getValue();
            userInfo user = userInfo.getInstance();
            double currentBalance = 0;

            if ("Checking".equals(selectedAccount)) {
                currentBalance = Double.parseDouble(user.getChecking());
            } else if ("Savings".equals(selectedAccount)) {
                currentBalance = Double.parseDouble(user.getSavings());
            }

            if (transferAmount > currentBalance) {
                showAlert(Alert.AlertType.ERROR, "Error", "Insufficient Funds",
                        "You do not have enough funds in your selected account.");
                return;
            }


            double updatedBalance = currentBalance - transferAmount;
            if ("Checking".equals(selectedAccount)) {
                user.setChecking(String.valueOf(updatedBalance));
            } else if ("Savings".equals(selectedAccount)) {
                user.setSavings(String.valueOf(updatedBalance));
            }


            if (!description.isEmpty() && !recipientPhone.isEmpty()) {

                String recipientId = getUserIdFromPhone(recipientPhone);

                if (recipientId != null) {




                    String[] recipientName = getRecipientNameFromFirestore(recipientId);

                    if (recipientName != null) {
                        showAlert(Alert.AlertType.INFORMATION, "Success", "Transfer Successful",
                                "Transferred Amount: $" + transferAmount + "\nRecipient Phone: " + recipientPhone +
                                        "\nRecipient Name: " + recipientName[0] + " " + recipientName[1] +
                                        "\nDescription: " + description);
                    } else {
                        showAlert(Alert.AlertType.WARNING, "Warning", "Recipient Name Not Found",
                                "Recipient's name not available.");
                    }

                    clearFields();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Recipient Not Found",
                            "Recipient with the entered phone number does not exist.");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Incomplete Information",
                        "Please fill in all fields.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid Amount",
                    "Please enter a valid numerical amount.");
        }
    }

    private String getUserIdFromPhone(String phone) {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference usersRef = db.collection("userinfo");

        ApiFuture<QuerySnapshot> future = usersRef.whereEqualTo("number", phone).get();
        try {
            QuerySnapshot querySnapshot = future.get();
            if (!querySnapshot.isEmpty()) {
                DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                return document.getId();
            } else {
                System.out.println("No user found with phone number: " + phone);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            System.err.println("Error retrieving user ID: " + e.getMessage());
        }
        return null;
    }

    private String[] getRecipientNameFromFirestore(String userId) {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference userRef = db.collection("userinfo").document(userId);

        try {
            DocumentSnapshot document = userRef.get().get();
            if (document.exists()) {
                String firstName = document.getString("First Name");
                String lastName = document.getString("Last Name");
                return new String[]{firstName, lastName};
            } else {
                System.out.println("No such document found in Firestore.");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            System.err.println("Error retrieving recipient's name: " + e.getMessage());
        }
        return null;
    }

    private void clearFields() {
        transferAmountField.clear();
        descriptionField.clear();
        recipientNo.clear();
        accountSelection.getSelectionModel().clearSelection();
        balanceLabel.setText("");
    }

    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    @FXML
    void handleViewPieChart(ActionEvent event) {

    }



}
