package com.example.bankingapplication;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.bson.Document;

import java.io.IOException;

public class createAcct2Controller {
    @FXML
    private TextField createUsernameTextField;
    @FXML
    private TextField createPasswordTextField;
    @FXML
    private TextField confirmPasswordTextField;
    @FXML
    private Button createAcctButton;
    @FXML
    private Label goBackOption;

    private String firstName;
    private String lastName;
    private String address;
    private String zipCode;
    private String dob;

    public void setUserData(String firstName, String lastName, String address, String zipCode, String dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.zipCode = zipCode;
        this.dob = dob;
    }

    public void handleCreateAcctButton () {
        System.out.println ("handleCreateAcctButton called");
        // Get additional user input
        String username = createUsernameTextField.getText();
        String password = createPasswordTextField.getText();
        // Combine user information
        Document userDocument = new Document();
        userDocument.append("First Name", firstName);
        userDocument.append("Last Name", lastName);
        userDocument.append("Address", address);
        userDocument.append("Zip Code", zipCode);
        userDocument.append("Date of Birth", dob);
        userDocument.append("Username", username);
        userDocument.append("Password", password);
        // Add user information to the database
        String uri = "mongodb+srv://admin:admin@mongodb.c9iio9o.mongodb.net/?retryWrites=true&w=majority&appName=MongoDB";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("MongoDB");
            MongoCollection<Document> collection = database.getCollection("bankingUserInfo");
            collection.insertOne(userDocument);
            System.out.println("User information successfully added to the database");
        } catch (Exception e) {
            System.err.println("Failed to add user information to the database: " + e);
        }
    }

    public void handleOnMouseClicked (MouseEvent event) throws IOException {
        System.out.println ("handleOnMouseClicked");

        main m = new main();
        m.changeScene("createAcct.fxml");
    }
}
