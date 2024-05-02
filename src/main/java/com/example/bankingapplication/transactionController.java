package com.example.bankingapplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;

public class transactionController extends homePageController  {

        @FXML
        private TableColumn<?, ?> categoryCol;

        @FXML
        private TableColumn<?, ?> costCol;

        @FXML
        private TableColumn<?, ?> dateCol;

        @FXML
        private ChoiceBox<?> myChoiceBox;

        @FXML
        private TableColumn<?, ?> nameCol;

        @FXML
        private Label userFullName;
        @FXML
        private Label cardNumLabel;
        @FXML
        private Label cardExpLabel;

        public void initialize() {
            userFullName.setText(userInfo.getFirstName() + " " + userInfo.getLastName());
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

        ///add username with bold font and dark grey color
        userFullName.setText(" " + userInfo.getFirstName() + " " + userInfo.getLastName());
        userFullName.setFont(Font.font("System", FontWeight.BOLD, 17)); // Choose the font size as per your UI requirement
        userFullName.setStyle("-fx-text-fill: #454545;"); // Changing the font color to dark gray (#454545)

        cardNumLabel.setText("**** **** **** " + userInfo.getCardNum().substring(userInfo.getCardNum().length() - 4));
        cardNumLabel.setStyle("-fx-text-fill: #454545; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, rgba(255,255,255,0.8), 0, 0, 0, 3);");

        cardExpLabel.setText(userInfo.getCardExp());
        cardExpLabel.setStyle("-fx-text-fill: #454545; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, rgba(255,255,255,0.8), 0, 0, 0, 3);");


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

}
