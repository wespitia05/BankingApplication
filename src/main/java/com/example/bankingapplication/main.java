package com.example.bankingapplication;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.auth.FirebaseAuth;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

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
        stage.initStyle(StageStyle.TRANSPARENT); //takes the x button away
        stage.show();
        //Branch test 1 is it working

        //addDataToDB("John", "Wick", "23 Dog Ave", "45674", "12/25/86", "wickj", "dog86", "5000", "6000", "2233445566777098", "08/26", "546");
            //addTransactionToUser("wickj", "Netflix", "Streaming", "15", "04/15/24");
            //addTransactionToUser("wickj", "Hulu", "Streaming", "13", "04/15/24");
            //addTransactionToUser("wickj", "Taco Bell", "Food", "12", "04/13/24");
            //addTransactionToUser("wickj", "Gucci", "Retail", "400", "04/02/24");
            //addTransactionToUser("wickj", "Mortgage", "Bills", "2400", "04/01/24");
            //addTransactionToUser("wickj", "Uber", "Transportation", "25", "04/23/24");
        //addDataToDB("Jason", "Bourne", "45 Memory Ave", "23145", "04/14/85", "bournej", "identity85", "4500", "2300", "2233445566773297", "09/26", "921");
            //addTransactionToUser("bournej", "Paramount+", "Streaming", "15", "04/12/24");
            //addTransactionToUser("bournej", "Target", "Retail", "55", "04/20/24");
            //addTransactionToUser("bournej", "Texas Roadhouse", "Food", "35", "03/24/24");
            //addTransactionToUser("bournej", "Costco", "Groceries", "200", "03/29/24");
            //addTransactionToUser("bournej", "Rent", "Bills", "1400", "04/01/24");
            //addTransactionToUser("bournej", "Verizon", "Bills", "50", "04/01/24");
        //addDataToDB("Jack", "Sparrow", "1 Black Pearl Ave", "79405", "05/06/79", "sparrowj", "rum79", "1250", "1000", "2233445566770546", "04/26", "567");
            //addTransactionToUser("sparrowj", "Liquor Store", "Food", "90", "03/20/24");
            //addTransactionToUser("sparrowj", "Walmart", "Retail", "100", "03/20/24");
            //addTransactionToUser("sparrowj", "AT&T", "Bills", "60", "04/01/24");
            //addTransactionToUser("sparrowj", "Applebees", "Food", "60", "04/12/24");
            //addTransactionToUser("sparrowj", "Regal Cinemas", "Entertainment", "40", "04/09/24");
        //addDataToDB("Joe", "Santagato", "55 Basement Ave", "11256", "04/18/91", "santagatoj", "basement91", "3000", "1700", "2233445566771907", "02/26", "435");
            //addTransactionToUser("santagatoj", "AMC", "Entertainment", "40", "03/30/24");
            //addTransactionToUser("santagatoj", "Skyzone", "Entertainment", "30", "04/25/24");
            //addTransactionToUser("santagatoj", "HBO MAX", "Streaming", "15", "04/01/24");
            //addTransactionToUser("santagatoj", "Bowling", "Entertainment", "40", "03/15/24");
            //addTransactionToUser("santagatoj", "BJ's", "Groceries", "200", "04/05/24");
        //addDataToDB("Mickey", "Mouse", "123 Disney Ave", "11111", "01/01/39", "mousem", "minnie39", "7690", "5680", "2233445566771207", "11/26", "223");
            //addTransactionToUser("mousem", "Sam's Club", "Groceries", "250", "04/05/24");
            //addTransactionToUser("mousem", "T-Mobile", "Bills", "50", "04/01/24");
            //addTransactionToUser("mousem", "Mortgage", "Bills", "3000", "04/01/24");
            //addTransactionToUser("mousem", "Louis Vuitton", "Retail", "5000", "03/22/24");
            //addTransactionToUser("mousem", "Ticketmaster", "Entertainment", "600", "04/20/24");

        //addEmployeeDataToDB("Scott", "Mescudi", "23 Rager Ave", "83758", "10/07/88", "cudik", "rager88", 9607);
        //addEmployeeDataToDB("Kanye", "West", "47 Fantasy Ave", "30495", "05/26/85", "westk", "yeezy85", 4069);
        //addEmployeeDataToDB("Slim", "Shady", "8 Mile Rd", "45832", "02/14/83", "shadys", "eminem83", 1203);
        //addEmployeeDataToDB("Travis", "Scott", "67 Utopia Blvd", "49558", "03/15/89", "scottt", "laflame89", 4390);
        //addEmployeeDataToDB("Metro", "Boomin", "45 Heros Ave", "45903", "11/20/90", "boominm", "heroesvillains90", 3467);
    }

    public static void addTransactionToUser(String username, String name, String category, String amount, String date) {
        // Direct reference to the user document using username as the document ID
        DocumentReference userDocRef = fstore.collection("userinfo").document(username);

        // Reference to the transactions subcollection
        CollectionReference transactions = userDocRef.collection("transactions");

        // Transaction data
        Map<String, Object> transactionData = new HashMap<>();
        transactionData.put("Name", name);
        transactionData.put("Category", category);
        transactionData.put("Amount", amount);
        transactionData.put("Date", date);

        // Add a new transaction with a generated ID
        transactions.document(UUID.randomUUID().toString()).set(transactionData).addListener(() -> {
            System.out.println("Transaction added successfully for user: " + username);
        }, Executors.newSingleThreadExecutor());
    }

    public static void addDataToDB(String firstName, String lastName, String address,
                                   String zipCode, String dob, String username, String password,
                                   String checking, String savings, String cardNum, String cardExp,
                                   String cardCVV) {

        // Create document reference
        DocumentReference docRef = main.fstore.collection("userinfo").document(username);

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
        data.put("Card Number", cardNum);
        data.put("Card Expiration Date", cardExp);
        data.put("Card CVV", cardCVV);

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