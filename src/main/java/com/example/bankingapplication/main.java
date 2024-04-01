package com.example.bankingapplication;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.SetOptions;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.FirebaseAuth;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class main extends Application {
    private static Stage stg;
    static Firestore fstore;
    public static FirebaseAuth fauth;
    private final FirestoreContext contxtFirebase = new FirestoreContext();
    @Override
    public void start(Stage stage) throws IOException {
        fstore = contxtFirebase.firebase();
        fauth = FirebaseAuth.getInstance();
        stg = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("CSC 325 - Capstone Project");
        stage.setScene(scene);
        stage.show();

        //addDataToDB("John", "Wick", "23 Dog Ave", "45674", "12/25/86", "wickj", "dog86");
        //addDataToDB("Jason", "Bourne", "45 Memory Ave", "23145", "04/14/85", "bournej", "identity85");
        //addDataToDB("Jack", "Sparrow", "1 Black Pearl Ave", "79405", "05/06/79", "sparrowj", "rum79");
        //addDataToDB("Joe", "Santagato", "55 Basement Ave", "11256", "04/18/91", "santagatoj", "basement91");
        //addDataToDB("Mickey", "Mouse", "123 Disney Ave", "11111", "01/01/39", "mousem", "minnie39");
    }

    public static void addDataToDB(String firstName, String lastName, String address,
                                   String zipCode, String dob, String username, String password) {

        // Create document reference
        DocumentReference docRef = main.fstore.collection("userinfo").document(UUID.randomUUID().toString());

        // Create data object
        Map<String, Object> data = new HashMap<>();
        data.put("First Name", firstName);
        data.put("Last Name", lastName);
        data.put("Address", address);
        data.put("Zip Code", zipCode);
        data.put("Date of Birth", dob);
        data.put("Username", username);
        data.put("Password", password);

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

    public void changeScene (String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.getScene().setRoot(pane);
    }

    public static void main(String[] args) {
        launch();
    }
}