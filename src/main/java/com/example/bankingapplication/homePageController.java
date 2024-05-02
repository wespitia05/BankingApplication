package com.example.bankingapplication;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
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
import java.util.HashMap;
import java.util.Map;
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
    private ImageView popupAd;
    @FXML
    private AnchorPane sideBar;
    @FXML
    private Label menu;
    @FXML
    private Label menuBack;
    @FXML
    private AnchorPane legendContainer;
    @FXML
    private Label legend1;
    @FXML
    private Label legend2;

    @FXML
    private Label legend3;

    @FXML
    private Label legend4;


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

        //code for sliding the menu

        userFullName.setText(userInfo.getFirstName() + " " + userInfo.getLastName());
        cardNumLabel.setText("**** **** **** " + userInfo.getCardNum().substring(userInfo.getCardNum().length() - 4));
        cardExpLabel.setText(userInfo.getCardExp());
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

                        String formatCardNum = "**** **** **** " + cardNum.substring(cardNum.length() - 4);
                        Platform.runLater(() -> {
                            setBalances(checkingBalance, savingsBalance);
                            updateSavingsTextField(savingsBalance);
                            updateCheckingTextField(checkingBalance);
                            cardNumLabel.setText(formatCardNum);
                            cardExpLabel.setText(cardExp);
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

    // this code is for the Checking button
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

                    updateCheckingBalanceInFirestore(checking);

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

        // Add animation to checkingBalanceTF
        addAnimation(checkingBalanceTF);

        // Add animation to savingsBalanceTF
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


    // this code is for the save button
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
                    updateSavingsBalanceInFirestore(savings);

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
        // Add animation to checkingBalanceTF
        addAnimation(checkingBalanceTF);

        // Add animation to savingsBalanceTF
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

    private void fetchAndDisplaySpendingPercentage() {
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
                    Color[] colors = generateSmoothColors(categoryTotals.size());
                    displayPieChart(categoryTotals, finalTotalSpent, colors);
                    Map<String, Double> spendingPercentage = calculateSpendingPercentage(categoryTotals, finalTotalSpent);
                    displaySpendingPercentage(spendingPercentage);
                });

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }, Executors.newSingleThreadExecutor());
    }
    private void displayPieChart(Map<String, Double> categoryTotals, double totalSpent, Color[] colors) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        int colorIndex = 0;
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            String category = entry.getKey();
            double sum = entry.getValue();
            double percentage = (sum / totalSpent) * 100;
            PieChart.Data data = new PieChart.Data(category + String.format(" (%.1f%%)", percentage), sum);
            //data.setFill(colors[colorIndex % colors.length]); // Set the fill color
            pieChartData.add(data);
            colorIndex++;
        }

        pieChart.setData(pieChartData);
        pieChart.setTitle("Spending Percentage");
    }

    // Method to add legend to the pie chart
    // Method to add legend to the pie chart
    private void addLegend(Map<String, Double> categoryTotals, Color[] colors) {
        // Clear existing legend items
        legend1.setText("");
        legend2.setText("");
        legend3.setText("");
        legend4.setText("");

        // Create legend items for each category
        int labelIndex = 1;
        int colorIndex = 0;
        for (String category : categoryTotals.keySet()) {
            if (labelIndex > 4) {
                // You can add additional handling here if you have more categories
                break;
            }
            Color color = colors[colorIndex % colors.length]; // Use a consistent color for each category

            // Set category as text for the label
            switch (labelIndex) {
                case 1:
                    legend1.setText(category);
                    legend1.setTextFill(color);
                    break;
                case 2:
                    legend2.setText(category);
                    legend2.setTextFill(color);
                    break;
                case 3:
                    legend3.setText(category);
                    legend3.setTextFill(color);
                    break;
                case 4:
                    legend4.setText(category);
                    legend4.setTextFill(color);
                    break;
            }
            labelIndex++;
            colorIndex++;
        }
    }

    // Method to generate smooth colors
    private Color[] generateSmoothColors(int numColors) {
        Color[] colors = new Color[numColors];
        for (int i = 0; i < numColors; i++) {
            colors[i] = Color.hsb(0, 1, (double) i / numColors);
        }
        return colors;
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

    //display percentage
    private void displaySpendingPercentage(Map<String, Double> spendingPercentage) {
        // Clear existing legend items
        legend1.setText("");
        legend2.setText("");
        legend3.setText("");
        legend4.setText("");

        // Create legend items for each category
        int labelIndex = 1;
        for (Map.Entry<String, Double> entry : spendingPercentage.entrySet()) {
            if (labelIndex > 4) {
                // You can add additional handling here if you have more categories
                break;
            }
            String category = entry.getKey();
            double percentage = entry.getValue();

            // Set category and percentage as text for the label
            switch (labelIndex) {
                case 1:
                    legend1.setText(category + " (" + String.format("%.1f%%", percentage) + ")");
                    break;
                case 2:
                    legend2.setText(category + " (" + String.format("%.1f%%", percentage) + ")");
                    break;
                case 3:
                    legend3.setText(category + " (" + String.format("%.1f%%", percentage) + ")");
                    break;
                case 4:
                    legend4.setText(category + " (" + String.format("%.1f%%", percentage) + ")");
                    break;
            }
            labelIndex++;
        }
    }

    ////////////////////////////////////////////////////// Pie chart //////////////////////////////

    // Helper method to generate a consistent hash code for a string
    private int hash(String s) {
        int hash = 0;
        for (int i = 0; i < s.length(); i++) {
            hash = (hash * 31) + s.charAt(i);
        }
        return hash;
    }


}