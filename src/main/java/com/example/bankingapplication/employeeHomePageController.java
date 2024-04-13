package com.example.bankingapplication;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class employeeHomePageController extends employeeLoginController {
    @FXML
    private Label employeeName;
    @FXML
    private TableView <userInfo> userInfoTV;
    @FXML
    private TableColumn <userInfo, String> firstNameCol;
    @FXML
    private TableColumn <userInfo, String> lastNameCol;
    @FXML
    private TableColumn <userInfo, String> dobCol;
    @FXML
    private TableColumn <userInfo, String> usernameCol;
    @FXML
    private TableColumn <userInfo, String> passwordCol;
    @FXML
    private TableColumn <userInfo, String> checkingCol;
    @FXML
    private TableColumn <userInfo, String> savingsCol;
    @FXML
    private Button displayAllCustomersButton;
    @FXML
    private Button editCustomerButton;
    @FXML
    private Button deleteCustomerButton;
    @FXML
    private Button startTransactionButton;

    public void initialize () {
        System.out.println("initialize called");

        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<userInfo, String>("firstName"));
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<userInfo, String>("lastName"));
        dobCol.setCellValueFactory(
                new PropertyValueFactory<userInfo, String>("dob"));
        usernameCol.setCellValueFactory(
                new PropertyValueFactory<userInfo, String>("username"));
        passwordCol.setCellValueFactory(
                new PropertyValueFactory<userInfo, String>("password"));
        checkingCol.setCellValueFactory(
                new PropertyValueFactory<userInfo, String>("checking"));
        savingsCol.setCellValueFactory(
                new PropertyValueFactory<userInfo, String>("savings"));

        fetchAndDisplayEmployeeDetails();
    }

    private void fetchAndDisplayEmployeeDetails() {
        if (employeeIDTextField != null && !employeeIDTextField.getText().isEmpty()) {
            int employeeID = Integer.parseInt(employeeIDTextField.getText());

            Firestore db = main.fstore;
            CollectionReference employeesRef = db.collection("employeeinfo");

            ApiFuture<QuerySnapshot> future = employeesRef.whereEqualTo("Employee ID", employeeID).get();
            future.addListener(() -> {
                try {
                    QuerySnapshot querySnapshot = future.get();
                    if (!querySnapshot.isEmpty()) {
                        DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                        String storedFirstName = document.getString("First Name");
                        String storedLastName = document.getString("Last Name");
                        Platform.runLater(() -> setEmployeeName(storedFirstName, storedLastName));
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace(); // or more sophisticated error handling
                }
            }, Executors.newSingleThreadExecutor());
        }
    }

    public void setEmployeeName(String firstName, String lastName) {
        if (employeeName != null) {
            employeeName.setText(firstName + " " + lastName);
        }
    }

    public void handleDisplayAllCustomersButton () {
        System.out.println ("handleDisplayAllCustomersButton called");

        displayAllCustomers();
    }

    public void displayAllCustomers() {
        Firestore db = main.fstore;
        ApiFuture<QuerySnapshot> future = db.collection("userinfo").get();

        ObservableList<userInfo> data = FXCollections.observableArrayList();

        try {
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                data.add(new userInfo(
                        document.getString("First Name"),
                        document.getString("Last Name"),
                        document.getString("Date of Birth"),
                        document.getString("Username"),
                        document.getString("Password"),
                        document.getString("Checking"),
                        document.getString("Savings")
                ));
            }
            userInfoTV.setItems(data);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void handleEditCustomerButton () {
        System.out.println ("handleEditCustomerButton called");
    }

    public void handleDeleteCustomerButton () {
        System.out.println ("handleDeleteCustomerButton called");
    }

    public void handleStartTransactionButton () {
        System.out.println ("handleStartTransactionButton called");
    }
}
