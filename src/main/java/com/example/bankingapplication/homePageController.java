package com.example.bankingapplication;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;


public class homePageController extends loginController{

    //btn = button--- // TF = text Field
    @FXML
    private Button save_btn; ///checking
    @FXML
    private Button saveDraft_btn; //savings
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
    public TextField checkingBalanceTF;
    @FXML
    public TextField savingsBalanceTF;
    @FXML
    private TextField debit_TF;
    @FXML
    private AnchorPane sideBar;
    @FXML
    private Label menu;

    @FXML
    private Label menuBack;
    @FXML
    private Label userFullName;
    public String username;

    @FXML
    public void initialize() {
        System.out.println ("initialize called");


        userFullName.setText(userInfo.getFirstName() + " " + userInfo.getLastName());
        // Manually set the onAction event handler for the saveDraft_btn button
        saveDraft_btn.setOnAction(this::handleSaveDraft_btn);


        sideBar.setTranslateX(-176);

        menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(sideBar);

            slide.setToX(0);
            slide.play();

            sideBar.setTranslateX(-176);

            slide.setOnFinished((ActionEvent e)-> {
                menu.setVisible(false);
                menuBack.setVisible(true);
            });
        });

        menuBack.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(sideBar);

            double finalTranslateX = -sideBar.getWidth();

            slide.setInterpolator(Interpolator.EASE_BOTH); //apply a smooth easing function

            slide.setFromX(0); // start from the current position
            slide.setToX(finalTranslateX); //Move to the final position

//            sideBar.setTranslateX(-176);
//            slide.play();



            //  sideBar.setTranslateX(0);

            slide.setOnFinished((ActionEvent e)-> {
                menu.setVisible(true);
                menuBack.setVisible(false);
            });

            slide.play();
        });

    }

    public void setUsername(String username) {
        this.username = username;
        displayUserBalances();
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
                        Platform.runLater(() -> {
                            setBalances(checkingBalance, savingsBalance);
                            updateSavingsTextField(savingsBalance);
                            updateCheckingTextField(checkingBalance);
                        });
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

    private void updateSavingsTextField(String savingsBalance) { /////////////////look over this method
        if (savingsBalanceTF != null) {
            savingsBalanceTF.setText(savingsBalance);
        }
    }
    private void updateCheckingTextField(String savingsBalance) { /////////////////look over this method
        if (checkingBalanceTF != null) {
            checkingBalanceTF.setText(savingsBalance);
        }
    }

    @FXML
    private void handledashBoard_btn() {
        System.out.println("Stop Clicking me, you are on my page");
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

    @FXML
    public void handleSave_btn(ActionEvent event) {   ///updates your checking balance

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
                    userInfo.setChecking(String.valueOf(checking));

                    // Call the method to update the checking balance in Firestore

                    updateCheckingBalanceInFirestore(String.valueOf(checking));

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

    public void updateCheckingBalanceInFirestore(String checkingBalance) {
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

    public String getDebitInfoFromFirestore() {
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
        return null;
    }

    @FXML
    public void handleSaveDraft_btn(ActionEvent event) {
        // These fields are for Debit card info
        String enteredDebit = debit_TF.getText().trim();
        String firebaseDebit = getDebitInfoFromFirestore(); // Retrieve debit info from Firestore

        // Get the entered amount from the enterAmount_TF TextField
        String enteredAmount = enterAmount_TF.getText().trim();

        // Check if the entered amount is a valid number
        if (!enteredAmount.isEmpty() && enteredAmount.matches("\\d*\\.?\\d+")) {
            // Convert the entered amount to double
            double amount = Double.parseDouble(enteredAmount);

            // Update the savings balance if the debit card is found
            if (enteredDebit.equals(firebaseDebit)) {
                String savingsBalance = savingsBalanceTF.getText().trim();
                if (!savingsBalance.isEmpty() && savingsBalance.matches("\\d*\\.?\\d+")) {
                    double savings = Double.parseDouble(savingsBalance);
                    savings += amount;
                    savingsBalanceTF.setText(String.valueOf(savings));
                    userInfo.setSavings(String.valueOf(savings));

                    // Call the method to update the savings balance in Firestore
                    updateSavingsBalanceInFirestore(String.valueOf(savings));

                } else {
                    // Display an error message if the savings balance is empty or not valid
                    System.out.println("Invalid savings balance");
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

    public void updateSavingsBalanceInFirestore(String savingsBalance) {
        Firestore db = main.fstore;
        CollectionReference usersRef = db.collection("userinfo");

        ApiFuture<QuerySnapshot> future = usersRef.whereEqualTo("Username", username).get();
        future.addListener(() -> {
            try {
                QuerySnapshot querySnapshot = future.get();
                if (!querySnapshot.isEmpty()) {
                    DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                    String documentId = document.getId();

                    // Update the Savings balance field in Firestore
                    usersRef.document(documentId).update("Savings", String.valueOf(savingsBalance));
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }, Executors.newSingleThreadExecutor());
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