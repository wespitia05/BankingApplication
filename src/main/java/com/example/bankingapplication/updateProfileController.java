package com.example.bankingapplication;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.google.common.util.concurrent.MoreExecutors;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class updateProfileController extends homePageController{


    @FXML
    private TextField firstName_TF;

    @FXML
    private TextField lastName_TF;

    @FXML
    private TextField newAddress_tf;

    @FXML
    private TextField newBirthDate_tf;

    @FXML
    private TextField newEmail_tf;

    @FXML
    private TextField newNumber_tf;

    @FXML
    private TextField newUserName_tf;

    @FXML
    private TextField newZipCode_tf;

    @FXML
    private Button save_btn;
    @FXML
    private Button goBack_btn;




    public void initialize() {
        firstName_TF.setText(userInfo.getFirstName());
        lastName_TF.setText(userInfo.getLastName());
        newUserName_tf.setText(userInfo.getUsername());
        newEmail_tf.setText(userInfo.getEmail());
        newBirthDate_tf.setText(userInfo.getDob());
        newZipCode_tf.setText(userInfo.getZipCode());
        newAddress_tf.setText(userInfo.getAddress());
        newNumber_tf.setText(userInfo.getNumber());


    }


    @FXML
    void handleSaveProfile_btn(ActionEvent event) {
        // Get the updated values from the text fields
        String newAddress = newAddress_tf.getText();
        String newBirthDate = newBirthDate_tf.getText();
        String newEmail = newEmail_tf.getText();
        String newFirstName = firstName_TF.getText();
        String newLastName = lastName_TF.getText();
        String newNumber = newNumber_tf.getText();
        String newUserName = newUserName_tf.getText();
        String newZipCode = newZipCode_tf.getText();

        // Update the user info in Firestore
        Firestore db = main.fstore;
        CollectionReference usersRef = db.collection("userinfo");

        ApiFuture<QuerySnapshot> future = usersRef.whereEqualTo("Username", newUserName).get();
        // Provide a Runnable and an Executor for the addListener method
        future.addListener(() -> {
            try {
                QuerySnapshot querySnapshot = future.get();
                if (!querySnapshot.isEmpty()) {
                    DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                    String userId = document.getId(); // Get the document ID
                    DocumentReference userDocRef = usersRef.document(userId);

                    // Update only the fields that are being changed
                    Map<String, Object> updates = new HashMap<>();
                    if (newFirstName != null && !newFirstName.isEmpty()) {
                        updates.put("First Name", newFirstName);
                    }
                    if (newLastName != null && !newLastName.isEmpty()) {
                        updates.put("Last Name", newLastName);
                    }
                    if (newAddress != null && !newAddress.isEmpty()) {
                        updates.put("Address", newAddress);
                    }
                    if (newBirthDate != null && !newBirthDate.isEmpty()) {
                        updates.put("Date of Birth", newBirthDate);
                    }
                    if (newEmail != null && !newEmail.isEmpty()) {
                        updates.put("Email", newEmail);
                    }
                    if (newNumber != null && !newNumber.isEmpty()) {
                        updates.put("number", newNumber);
                    }
                    if (newUserName != null && !newUserName.isEmpty()) {
                        updates.put("Username", newUserName);
                    }
                    if (newZipCode != null && !newZipCode.isEmpty()) {
                        updates.put("Zip Code", newZipCode);
                    }

                    // Update the document with all the changes
                    userDocRef.update(updates);

                    System.out.println("Profile updated successfully!");
                } else {
                    System.out.println("No matching document found for username: " + newUserName);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                System.err.println("Error updating profile: " + e.getMessage());
            }
        }, MoreExecutors.directExecutor()); // Provide the executor here
    }



    @FXML
    void handleGoBack_btn(ActionEvent event) throws IOException {
        // Get the current stage
        Stage stage = (Stage) goBack_btn.getScene().getWindow();

        // Close the current stage
         stage.close();
         Stage previousStage = new Stage();
         Parent previousPane = FXMLLoader.load(getClass().getResource("profile.fxml"));
         Scene scene = new Scene(previousPane);
         previousStage.setScene(scene);
         previousStage.show();
    }

    public void setUsername(String username) {
    }
}
