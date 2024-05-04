package com.example.bankingapplication;





import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;



public class withdrawalController  {

    @FXML
    private Label userFullName;
    @FXML
    private ComboBox<String> cardSelection;

    @FXML
    private Label balanceLabel;

    @FXML
    private TextField bankField;

    @FXML
    private TextField amountField;



    @FXML
    public void handleSelection(ActionEvent event) {


        userInfo user = userInfo.getInstance();
        String selectedAccount = cardSelection.getValue();

        if (selectedAccount != null) {
            if ("Checking".equals(selectedAccount)) {
                balanceLabel.setText("Checking Balance: $" + user.getChecking());
            } else if ("Savings".equals(selectedAccount)) {
                balanceLabel.setText("Savings Balance: $" + user.getSavings());
            }
        }
    }

    @FXML
    public void handleWithdraw(ActionEvent event) {
        userInfo user = userInfo.getInstance();
        String selectedAccount = cardSelection.getValue();
        String bank = bankField.getText();
        String amountStr = amountField.getText();

        if (selectedAccount == null || bank.isEmpty() || amountStr.isEmpty()) {
            // Show error message if any field is empty
            showAlert(Alert.AlertType.ERROR, "Error", "Incomplete Information",
                    "Please fill in all fields.");
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            double currentBalance = 0;

            if ("Checking".equals(selectedAccount)) {
                currentBalance = Double.parseDouble(user.getChecking());
            } else if ("Savings".equals(selectedAccount)) {
                currentBalance = Double.parseDouble(user.getSavings());
            }

            if (amount <= currentBalance) {
                double updatedBalance = currentBalance - amount;

                if ("Checking".equals(selectedAccount)) {
                    user.setChecking(String.valueOf(updatedBalance));
                    balanceLabel.setText("");
                } else if ("Savings".equals(selectedAccount)) {
                    user.setSavings(String.valueOf(updatedBalance));
                    balanceLabel.setText("");
                }


                showAlert(Alert.AlertType.INFORMATION, "Success", "Withdrawal Successful",
                        "Updated Balance: $" + updatedBalance);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Insufficient Balance",
                        "You do not have enough funds in your account.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid Amount",
                    "Please enter a valid numerical amount.");
        }
        bankField.clear();
        amountField.clear();
        cardSelection.getSelectionModel().clearSelection();
    }

    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }


    @FXML
    private void setAmount10(ActionEvent event) {
        amountField.setText("10");
    }

    @FXML
    private void setAmount50(ActionEvent event) {
        amountField.setText("50");
    }

    @FXML
    private void setAmount100(ActionEvent event) {
        amountField.setText("100");
    }

    @FXML
    private void setAmount150(ActionEvent event) {
        amountField.setText("150");
    }

    @FXML
    private void setAmount200(ActionEvent event) {
        amountField.setText("200");
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
        controller.setBalances(userInfo.getChecking(), userInfo.getSavings());

        // Set the scene on the current stage
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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

}
