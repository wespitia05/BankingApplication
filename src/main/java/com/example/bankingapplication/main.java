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
    static Stage stg;
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
        //Branch test 1 is it working

        //addDataToDB("John", "Wick", "23 Dog Ave", "45674", "12/25/86", "wickj", "dog86", "5000", "6000");
        //addDataToDB("Jason", "Bourne", "45 Memory Ave", "23145", "04/14/85", "bournej", "identity85", "4500", "2300");
        //addDataToDB("Jack", "Sparrow", "1 Black Pearl Ave", "79405", "05/06/79", "sparrowj", "rum79", "1250", "1000");
        //addDataToDB("Joe", "Santagato", "55 Basement Ave", "11256", "04/18/91", "santagatoj", "basement91", "3000", "1700");
        //addDataToDB("Mickey", "Mouse", "123 Disney Ave", "11111", "01/01/39", "mousem", "minnie39", "7690", "5680");

        //addEmployeeDataToDB("Scott", "Mescudi", "23 Rager Ave", "83758", "10/07/88", "cudik", "rager88", 9607);
        //addEmployeeDataToDB("Kanye", "West", "47 Fantasy Ave", "30495", "05/26/85", "westk", "yeezy85", 4069);
        //addEmployeeDataToDB("Slim", "Shady", "8 Mile Rd", "45832", "02/14/83", "shadys", "eminem83", 1203);
        //addEmployeeDataToDB("Travis", "Scott", "67 Utopia Blvd", "49558", "03/15/89", "scottt", "laflame89", 4390);
        //addEmployeeDataToDB("Metro", "Boomin", "45 Heros Ave", "45903", "11/20/90", "boominm", "heroesvillains90", 3467);
    }

    public static void addDataToDB(String firstName, String lastName, String address,
                                   String zipCode, String dob, String username, String password,
                                   String checking, String savings) {

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
        data.put("Checking", checking);
        data.put("Savings", savings);

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

    public static void addEmployeeDataToDB(String firstName, String lastName, String address,
                                   String zipCode, String dob, String username, String password, int employeeID) {

        // Create document reference
        DocumentReference docRef = main.fstore.collection("employeeinfo").document(UUID.randomUUID().toString());

        // Create data object
        Map<String, Object> data = new HashMap<>();
        data.put("First Name", firstName);
        data.put("Last Name", lastName);
        data.put("Address", address);
        data.put("Zip Code", zipCode);
        data.put("Date of Birth", dob);
        data.put("Username", username);
        data.put("Password", password);
        data.put("Employee ID", employeeID);

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