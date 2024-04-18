package com.example.bankingapplication;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    @FXML
    private Button viewCustomerButton;

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
                        document.getString("Savings"),
                        document.getId(),
                        document.getString("Address"),
                        document.getString("Zip Code")
                ));
            }
            userInfoTV.setItems(data);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void handleEditCustomerButton() {
        System.out.println("handleEditCustomerButton called");
        userInfo selectedUser = userInfoTV.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            System.out.println("No user selected for editing.");
            return;
        }

        Dialog<userInfo> dialog = new Dialog<>();
        dialog.setTitle("Edit Customer");
        dialog.setHeaderText("Edit the selected customer's details");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField firstNameField = new TextField(selectedUser.getFirstName());
        TextField lastNameField = new TextField(selectedUser.getLastName());
        TextField usernameField = new TextField(selectedUser.getUsername());
        TextField dobField = new TextField(selectedUser.getDob());
        TextField passwordField = new TextField(selectedUser.getPassword());
        TextField checkingField = new TextField(selectedUser.getChecking());
        TextField savingsField = new TextField(selectedUser.getSavings());
        TextField addressField = new TextField(selectedUser.getAddress());
        TextField zipCodeField = new TextField(selectedUser.getZipCode());
        // Add fields for other userInfo properties as needed

        grid.add(new Label("First Name:"), 0, 0);
        grid.add(firstNameField, 1, 0);
        grid.add(new Label("Last Name:"), 0, 1);
        grid.add(lastNameField, 1, 1);
        grid.add(new Label("Address:"),0,2);
        grid.add(addressField,1,2);
        grid.add(new Label("Zip Code:"),0,3);
        grid.add(zipCodeField,1,3);
        grid.add(new Label("Date of Birth:"), 0, 4);
        grid.add(dobField, 1, 4);
        grid.add(new Label("Username:"),0,5);
        grid.add(usernameField,1,5);
        grid.add(new Label("Password:"),0,6);
        grid.add(passwordField,1,6);
        grid.add(new Label("Checking:"),0,7);
        grid.add(checkingField,1,7);
        grid.add(new Label("Username:"),0,8);
        grid.add(savingsField,1,8);
        // Add other fields to the grid

        dialog.getDialogPane().setContent(grid);
        Platform.runLater(firstNameField::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return new userInfo(
                        firstNameField.getText(),  // First Name
                        lastNameField.getText(),   // Last Name
                        dobField.getText(),        // Date of Birth
                        usernameField.getText(),   // Username
                        passwordField.getText(),   // Password
                        checkingField.getText(),   // Checking Account Balance
                        savingsField.getText(),    // Savings Account Balance
                        addressField.getText(),
                        zipCodeField.getText(),
                        selectedUser.getId());
            }
            return null;
        });

        Optional<userInfo> result = dialog.showAndWait();

        result.ifPresent(newUserInfo -> {
            updateCustomerTV(newUserInfo);
            updateCustomerInFirestore(newUserInfo);
        });
    }
    // merge test
    // merge test 2
    // williams branch test 1
    // lets see if it works


    //it works lets try pushing it

    private void updateCustomerTV(userInfo updatedUser) {
        int index = userInfoTV.getItems().indexOf(userInfoTV.getSelectionModel().getSelectedItem());
        if (index >= 0) {
            userInfoTV.getItems().set(index, updatedUser);
            System.out.println ("Update table view successful");
        }
    }

    private void updateCustomerInFirestore(userInfo updatedUser) {
        Firestore db = main.fstore;
        DocumentReference docRef = db.collection("userinfo").document(updatedUser.getId());

        Map<String, Object> updates = new HashMap<>();
        updates.put("First Name", updatedUser.getFirstName());
        updates.put("Last Name", updatedUser.getLastName());
        updates.put("Date of Birth", updatedUser.getDob());
        updates.put("Username", updatedUser.getUsername());
        updates.put("Password", updatedUser.getPassword());
        updates.put("Checking", updatedUser.getChecking());
        updates.put("Savings", updatedUser.getSavings());

        docRef.update(updates).addListener(() -> {
            System.out.println("Update successful");
        }, Executors.newSingleThreadExecutor());
    }

    public void handleDeleteCustomerButton() {
        System.out.println("handleDeleteCustomerButton called");

        userInfo selectedUser = userInfoTV.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Removal");
            alert.setContentText("Are you sure you want to remove " + selectedUser.getFirstName() + " " + selectedUser.getLastName() + " " +
                    "from the table view and database?");
            alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    deleteCustomerFromFirestore(selectedUser.getId());
                    userInfoTV.getItems().remove(selectedUser);
                    System.out.println("Removed: " + selectedUser.getFirstName() + " " + selectedUser.getLastName());
                }
                else {
                    System.out.println("No user selected.");
                }
        }
    }


    private void deleteCustomerFromFirestore(String id) {
        if (id == null || id.isEmpty()) {
            System.out.println("Invalid document ID");
            return;
        }

        Firestore db = main.fstore;
        DocumentReference docRef = db.collection("userinfo").document(id);
        ApiFuture<WriteResult> future = docRef.delete();
        future.addListener(() -> {
            try {
                WriteResult result = future.get();
                System.out.println("Delete successful: " + result.getUpdateTime());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }, Executors.newSingleThreadExecutor());
    }

    public void handleViewCustomerButton () {
        System.out.println ("handleViewCustomerButton called");

        userInfo selectedUser = userInfoTV.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            System.out.println("No user selected for editing.");
            return;
        }

        Dialog<userInfo> dialog = new Dialog<>();
        dialog.setTitle("Customer Information");
        dialog.setHeaderText("Displaying customer information below");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        Label firstName = new Label(selectedUser.getFirstName());
        Label lastName = new Label(selectedUser.getLastName());
        Label address = new Label(selectedUser.getAddress());
        Label zipCode = new Label(selectedUser.getZipCode());
        Label dob = new Label(selectedUser.getDob());
        Label username = new Label(selectedUser.getUsername());
        Label password = new Label(selectedUser.getPassword());
        Label checking = new Label(selectedUser.getChecking());
        Label savings = new Label(selectedUser.getSavings());

        grid.add(new Label("First Name:"), 0, 0);
        grid.add(firstName, 1, 0);
        grid.add(new Label("Last Name:"), 0, 1);
        grid.add(lastName, 1, 1);
        grid.add(new Label("Address:"),0,2);
        grid.add(address,1,2);
        grid.add(new Label("Zip Code:"),0,3);
        grid.add(zipCode,1,3);
        grid.add(new Label("Date of Birth:"), 0, 4);
        grid.add(dob, 1, 4);
        grid.add(new Label("Username:"),0,5);
        grid.add(username,1,5);
        grid.add(new Label("Password:"),0,6);
        grid.add(password,1,6);
        grid.add(new Label("Checking:"),0,7);
        grid.add(checking,1,7);
        grid.add(new Label("Username:"),0,8);
        grid.add(savings,1,8);

        dialog.getDialogPane().setContent(grid);

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType editButton = new ButtonType("Edit", ButtonBar.ButtonData.OTHER);
        ButtonType exitButton = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);

        dialog.getDialogPane().getButtonTypes().addAll(okButton, editButton, exitButton);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == editButton) {
                handleEditCustomerButton();
            } else if (dialogButton == exitButton) {
                dialog.close();
            }
            return null;
        });

        dialog.showAndWait();
    }

    public void handleStartTransactionButton () {
        System.out.println ("handleStartTransactionButton called");
    }
}
