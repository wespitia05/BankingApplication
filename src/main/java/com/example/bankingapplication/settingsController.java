package com.example.bankingapplication;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.common.util.concurrent.MoreExecutors;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class settingsController {

    @FXML
    private TextField newPassword_tf;

    @FXML
    private TextField newUserName_Tf;

    @FXML
    private Button save_btn;

    @FXML
    void handleSave_btn(ActionEvent event) {
//        in theory
////that should be the code

                String newUserName = newUserName_Tf.getText();
                String newPassword = newPassword_tf.getText();

//        // Update the user info in Firestore
//        Firestore db = main.fstore;
//        CollectionReference usersRef = db.collection("userinfo");
//
//        ApiFuture<QuerySnapshot> future = usersRef.whereEqualTo("Username", newUserName).get();
//        // Provide a Runnable and an Executor for the addListener method
//        future.addListener(() -> {
//            try {
//                QuerySnapshot querySnapshot = future.get();
//                if (!querySnapshot.isEmpty()) {
//                    DocumentSnapshot document = querySnapshot.getDocuments().get(0);
//                    String userId = document.getId(); // Get the document ID
//                    DocumentReference userDocRef = usersRef.document(userId);
//
//                    // Update only the fields that are being changed
//                    Map<String, Object> updates = new HashMap<>();
//                    if (newFirstName != null && !newFirstName.isEmpty()) {
//                        updates.put("First Name", newFirstName);
//                    }
//                    if (newLastName != null && !newLastName.isEmpty()) {
//                        updates.put("Last Name", newLastName);
//                    }
//                    if (newAddress != null && !newAddress.isEmpty()) {
//                        updates.put("Address", newAddress);
//                    }
//                    if (newBirthDate != null && !newBirthDate.isEmpty()) {
//                        updates.put("Date of Birth", newBirthDate);
//                    }
//                    if (newEmail != null && !newEmail.isEmpty()) {
//                        updates.put("Email", newEmail);
//                    }
//                    if (newNumber != null && !newNumber.isEmpty()) {
//                        updates.put("number", newNumber);
//                    }
//                    if (newUserName != null && !newUserName.isEmpty()) {
//                        updates.put("Username", newUserName);
//                    }
//                    if (newZipCode != null && !newZipCode.isEmpty()) {
//                        updates.put("Zip Code", newZipCode);
//                    }
//
//                    // Update the document with all the changes
//                    userDocRef.update(updates);
//
//                    System.out.println("Profile updated successfully!");
//                } else {
//                    System.out.println("No matching document found for username: " + newUserName);
//                }
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//                System.err.println("Error updating profile: " + e.getMessage());
//            }
//        }, MoreExecutors.directExecutor()); // Provide the executor here

    }

    @FXML
    void handledashBoard_btn(ActionEvent event) {

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

//    @FXML
//    private void handlemyCard_btn(ActionEvent event) throws IOException {
//        System.out.println("My Cards clicked");
//
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("myCards.fxml"));
//        Parent root = loader.load(); // This is the root node of your new scene, loaded from FXML
//        myCardController controller = loader.getController();
//
//        controller.setUserFullName(userInfo.getFirstName() + " " + userInfo.getLastName());
//        controller.setCardNum("**** **** **** " + userInfo.getCardNum().substring(userInfo.getCardNum().length() - 4));
//        controller.setCardExp(userInfo.getCardExp());
//        controller.setBalances(userInfo.getChecking(), userInfo.getSavings());
//        controller.setUsername(userInfo.getUsername());
//
//        Scene scene = new Scene(root);
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        stage.setScene(scene);
//        stage.show();
//
//    }




    @FXML
    private void handletranaction_btn(ActionEvent event) throws IOException {
        System.out.println("Transactions clicked");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("transactions.fxml"));
        Parent root = loader.load();
        transactionController controller = loader.getController();

        controller.setUsername(userInfo.getUsername());

//        Scene scene = new Scene(root);
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        stage.setScene(scene);
//        stage.show();
        switchScene("transactions.fxml", event);
    }

    @FXML
    private void handlepayment_btn(ActionEvent event) throws IOException {
        System.out.println("Payment clicked");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("paymentDeposit.fxml"));
        Parent root = loader.load();
        paymentDepositController controller = loader.getController();

        controller.setUsername(userInfo.getUsername());

//        Scene scene = new Scene(root);
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        stage.setScene(scene);
//        stage.show();
        switchScene("paymentDeposit.fxml", event);
    }


    @FXML
    private void handleprofile_btn(ActionEvent event) throws IOException {
        System.out.println("Profiles clicked");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("profile.fxml"));
        Parent root = loader.load();
        ProfileController controller = loader.getController();

        controller.setUsername(userInfo.getUsername());

//        Scene scene = new Scene(root);
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        stage.setScene(scene);
//        stage.show();
        switchScene("profile.fxml", event);

    }

    @FXML
    private void handlesettings_btn(ActionEvent event) {
        System.out.println("why you keep clicking me");
    }

    private void switchScene(String fxmlFile, ActionEvent event) throws IOException {
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


    public void setUsername(String username) {
    }
}
