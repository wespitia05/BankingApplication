package com.example.bankingapplication;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.cloud.firestore.*;
import com.google.common.util.concurrent.MoreExecutors;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static com.example.bankingapplication.main.stg;


public class homePageController extends loginController{

    //btn = button--- // TF = text Field
    @FXML
    private Button save_btn; ///checking
    @FXML
    private Button saveDraft_btn; //savings
    @FXML
    private TextField enterAmount_TF;
    @FXML
    private PieChart pieChart;
    @FXML
    private TextField income_TF;
    @FXML
    public TextField checkingBalanceTF;
    @FXML
    public TextField savingsBalanceTF;
    @FXML
    private TextField debit_TF;
    @FXML
    private ImageView popupAd;
    @FXML
    private AnchorPane sideBar;
    @FXML
    private Label menu;
    @FXML
    private Label menuBack;
    @FXML
    private Label userFullName;
    @FXML
    private Label cardNumLabel;
    @FXML
    private Label cardExpLabel;
    public String username;

    @FXML
    public void initialize() {
        System.out.println ("initialize called");

        userFullName.setText(userInfo.getFirstName() + " " + userInfo.getLastName());
        cardNumLabel.setText("**** **** **** " + userInfo.getCardNum().substring(userInfo.getCardNum().length() - 4));
        cardExpLabel.setText(userInfo.getCardExp());

        sideBar.setTranslateX(-176);

        slider();
    }

    //method to make the menu slide
    public void slider(){
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

            slide.setInterpolator(Interpolator.EASE_BOTH);

            slide.setFromX(0);
            slide.setToX(finalTranslateX);

            slide.setOnFinished((ActionEvent e)-> {
                menu.setVisible(true);
                menuBack.setVisible(false);
            });

            slide.play();
        });


    }

    // Method to add animation to a TextField
    private void addAnimation(TextField textField) {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.6), textField);
        transition.setFromY(-5); // Move up by 5 pixels
        transition.setToY(0);    // Move back to original position
        textField.setOnMouseClicked(event -> transition.play());
    }

    public void setUsername(String username) {
        this.username = username;
        displayUserBalances();
        fetchAndDisplaySpendingPercentage();
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
                        String cardNum = document.getString("Card Number");
                        String cardExp = document.getString("Card Expiration Date");
                        String email = document.getString("Email");
                        String dob = document.getString("Date of Birth");
                        String zipCode = document.getString("Zip Code");
                        String address = document.getString("Address");
                        String number = document.getString("Number");
                        String firstName = document.getString("First Name");
                        String lastName = document.getString("Last Name");


                        String formatCardNum = "**** **** **** " + cardNum.substring(cardNum.length() - 4);
                        Platform.runLater(() -> {
                            setBalances(checkingBalance, savingsBalance);
                            updateSavingsTextField(savingsBalance);
                            updateCheckingTextField(checkingBalance);
                            cardNumLabel.setText(formatCardNum);
                            cardExpLabel.setText(cardExp);
                            userInfo.setEmail(email);
                            userInfo.setDob(dob);
                            userInfo.setZipCode(zipCode);
                            userInfo.setAddress(address);
                            userInfo.setNumber(number);
                            userInfo.setNumber(firstName);
                            userInfo.setNumber(lastName);


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

    private void updateSavingsTextField(String savingsBalance) {
        if (savingsBalanceTF != null) {
            savingsBalanceTF.setText(savingsBalance);
        }
    }
    private void updateCheckingTextField(String savingsBalance) {
        if (checkingBalanceTF != null) {
            checkingBalanceTF.setText(savingsBalance);
        }
    }

    @FXML
    void handledashBoard_btn() {
        System.out.println("Stop Clicking me, you are on my page");
    }

    //switching the scenes should be smoother
    void switchScene(String fxmlFile, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), root);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();

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
        controller.setCardNum("**** **** **** " + userInfo.getCardNum().substring(userInfo.getCardNum().length() - 4));
        controller.setCardExp(userInfo.getCardExp());
        controller.setBalances(userInfo.getChecking(), userInfo.getSavings());
        controller.setUsername(userInfo.getUsername());

        switchScene("myCards.fxml", event);
    }


    @FXML
    private void handletranaction_btn(ActionEvent event) throws IOException {
        System.out.println("Transactions clicked");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("transactions.fxml"));
        Parent root = loader.load();
        transactionController controller = loader.getController();

        controller.setUsername(userInfo.getUsername());

        switchScene("transactions.fxml", event);
    }

    @FXML
    private void handlepayment_btn(ActionEvent event) throws IOException {
        System.out.println("Payment clicked");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("paymentDeposit.fxml"));
        Parent root = loader.load();
        paymentDepositController controller = loader.getController();

        controller.setUsername(userInfo.getUsername());

        switchScene("paymentDeposit.fxml", event);
    }

    @FXML
    private void handleprofile_btn(ActionEvent event) throws IOException {
        System.out.println("Profiles clicked");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("profile.fxml"));
        Parent root = loader.load();
        ProfileController controller = loader.getController();

        controller.setUsername(userInfo.getUsername());
        switchScene("profile.fxml", event);
    }

    @FXML
    private void handlesettings_btn(ActionEvent event) throws IOException {
        System.out.println("Settings clicked");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("settings.fxml"));
        Parent root = loader.load();
        settingsController controller = loader.getController();

        controller.setUsername(userInfo.getUsername());

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void handleSave_btn(ActionEvent event) {

        String enteredDebit = debit_TF.getText().trim();
        String firebaseDebit = getDebitInfoFromFirestore();

        String enteredAmount = enterAmount_TF.getText().trim();

        if (!enteredAmount.isEmpty() && enteredAmount.matches("\\d*\\.?\\d+")) {

            double amount = Double.parseDouble(enteredAmount);

            if (enteredDebit.equals(firebaseDebit)) {
                String checkingBalance = checkingBalanceTF.getText().trim();
                if (!checkingBalance.isEmpty() && checkingBalance.matches("\\d*\\.?\\d+")) {
                    double checking = Double.parseDouble(checkingBalance);
                    checking += amount;
                    checkingBalanceTF.setText(String.valueOf(checking));
                    userInfo.setChecking(String.valueOf(checking));

                    updateCheckingBalanceInFirestore(checking);

                } else {
                    System.out.println("Invalid checking balance");
                }
            } else {
                System.out.println("Debit card not found");
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Amount");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid amount.");
            alert.showAndWait();
        }
        addAnimation(checkingBalanceTF);
        addAnimation(savingsBalanceTF);
    }

    public void updateCheckingBalanceInFirestore(Double checkingBalance) {
        Firestore db = main.fstore;
        CollectionReference usersRef = db.collection("userinfo");

        ApiFuture<QuerySnapshot> future = usersRef.whereEqualTo("Username", username).get();
        future.addListener(() -> {
            try {
                QuerySnapshot querySnapshot = future.get();
                if (!querySnapshot.isEmpty()) {
                    DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                    String documentId = document.getId();

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

        ApiFuture<QuerySnapshot> future = debitRef.whereEqualTo("Card Number", debit_TF.getText().trim()).get();
        try {
            QuerySnapshot querySnapshot = future.get();
            if (!querySnapshot.isEmpty()) {
                DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                return document.getString("Card Number");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @FXML
    public void handleSaveDraft_btn(ActionEvent event) {

        String enteredDebit = debit_TF.getText().trim();
        String firebaseDebit = getDebitInfoFromFirestore();

        String enteredAmount = enterAmount_TF.getText().trim();

        if (!enteredAmount.isEmpty() && enteredAmount.matches("\\d*\\.?\\d+")) {

            double amount = Double.parseDouble(enteredAmount);

            if (enteredDebit.equals(firebaseDebit)) {
                String savingsBalance = savingsBalanceTF.getText().trim();
                if (!savingsBalance.isEmpty() && savingsBalance.matches("\\d*\\.?\\d+")) {
                    double savings = Double.parseDouble(savingsBalance);
                    savings += amount;
                    savingsBalanceTF.setText(String.valueOf(savings));
                    userInfo.setSavings(String.valueOf(savings));

                    updateSavingsBalanceInFirestore(savings);

                } else {
                    System.out.println("Invalid savings balance");
                }
            } else {
                System.out.println("Debit card not found");
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Amount");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid amount.");
            alert.showAndWait();
        }
        addAnimation(checkingBalanceTF);
        addAnimation(savingsBalanceTF);
    }

    public void updateSavingsBalanceInFirestore(Double savingsBalance) {
        Firestore db = main.fstore;
        CollectionReference usersRef = db.collection("userinfo");

        ApiFuture<QuerySnapshot> future = usersRef.whereEqualTo("Username", username).get();
        future.addListener(() -> {
            try {
                QuerySnapshot querySnapshot = future.get();
                if (!querySnapshot.isEmpty()) {
                    DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                    String documentId = document.getId();

                    usersRef.document(documentId).update("Savings", String.valueOf(savingsBalance));
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }, Executors.newSingleThreadExecutor());
    }

    @FXML
    private void popupShow() {
        try {
            Stage popupStage = new Stage();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("popupPayment.fxml"));
            Parent popupContent = loader.load();

            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Payment");

            popupPaymentController controller = loader.getController();

            ObservableList<String> travMeal = FXCollections.observableArrayList("Quarter Pounder w/ Cheese and Bacon", "Medium Fry", "Sprite", "Barbeque Sauce");
            controller.cart.setItems(travMeal);
            controller.updateTotalLabel("Total:      $6.00");
            controller.subtotal = "6.00";

            // Set the loaded FXML as the root of the scene for the popup stage
            Scene popupScene = new Scene(popupContent);
            popupStage.setScene(popupScene);

            // Show the popup
            popupStage.show();

            // Add explosion-type animation to the popup ad
            addExplosionAnimation(popupAd);

            // Add listener to trigger reverse animation when popup window is closed
            popupStage.setOnHidden(event -> {
                // Reverse explosion-type animation
                reverseExplosionAnimation(popupAd);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getImg () {
        Image img = popupAd.getImage();
        String file = img.getUrl();
        return file;
    }

    //animation for the pop-up add. Makes the add increase in size
    private void addExplosionAnimation(ImageView imageView) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), imageView);
        scaleTransition.setToX(1.5);
        scaleTransition.setToY(1.5);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();
    }
    //makes the add go back to original size
    private void reverseExplosionAnimation(ImageView imageView) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), imageView);
        scaleTransition.setToX(1); // Return to normal size
        scaleTransition.setToY(1); // Return to normal size
        scaleTransition.play();
    }

    public void fetchAndDisplaySpendingPercentage() {
        DocumentReference userDocRef = main.fstore.collection("userinfo").document(username);
        ApiFuture<QuerySnapshot> future = userDocRef.collection("transactions").get();

        future.addListener(() -> {
            try {
                QuerySnapshot querySnapshot = future.get(); // Retrieve the query results
                Map<String, Double> categoryTotals = new HashMap<>();
                double totalSpent = 0;

                for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                    String category = doc.getString("Category");
                    double amount = Double.parseDouble(doc.getString("Amount"));
                    categoryTotals.put(category, categoryTotals.getOrDefault(category, 0.0) + amount);
                    totalSpent += amount;
                }

                double finalTotalSpent = totalSpent;
                Platform.runLater(() -> {
                    displayPieChart(categoryTotals, finalTotalSpent);
                    Map<String, Double> spendingPercentage = calculateSpendingPercentage(categoryTotals, finalTotalSpent);
                });

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }, Executors.newSingleThreadExecutor());
    }

    public void displayPieChart(Map<String, Double> categoryTotals, double totalSpent) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        int colorIndex = 0;
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            String category = entry.getKey();
            double sum = entry.getValue();
            double percentage = (sum / totalSpent) * 100;
            PieChart.Data data = new PieChart.Data(category + String.format(" (%.1f%%)", percentage), sum);
            Node node = data.getNode();
            pieChartData.add(data);
            colorIndex++;
        }

        pieChart.setData(pieChartData);
        pieChart.setTitle("Spending Percentage");
    }

    //calculatingSpendingPercentage
    private Map<String, Double> calculateSpendingPercentage(Map<String, Double> categoryTotals, double totalSpent) {
        Map<String, Double> spendingPercentage = new HashMap<>();
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            String category = entry.getKey();
            double sum = entry.getValue();
            double percentage = (sum / totalSpent) * 100;
            spendingPercentage.put(category, percentage);
        }
        return spendingPercentage;
    }

    public void handleSignOutOnMouseClicked () throws IOException {
        System.out.println("handleSignOutOnMouseClicked called");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("employeeLogin.fxml"));
        Parent root = loader.load();
        stg.getScene().setRoot(root);
    }

}