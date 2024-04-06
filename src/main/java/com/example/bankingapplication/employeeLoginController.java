package com.example.bankingapplication;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class employeeLoginController {
    @FXML
    private TextField employeeIDTextField;
    @FXML
    private TextField employeeUsernameTextField;
    @FXML
    private TextField employeePasswordTextField;
    @FXML
    private Button employeeLoginButton;
    @FXML
    private Label EmployeeGoBackOption;

    // Add mian's branch

    public void handleEmployeeLoginButton () throws IOException {
        System.out.println("handleEmployeeLoginButton called");

        int employeeID = Integer.parseInt(employeeIDTextField.getText());
        String employeeUsername = employeeUsernameTextField.getText();
        String employeePassword = employeePasswordTextField.getText();

        Firestore db = main.fstore;
        CollectionReference employeesRef = db.collection("employeeinfo");

        ApiFuture<QuerySnapshot> future = employeesRef.whereEqualTo("Employee ID", employeeID).get();
        QuerySnapshot querySnapshot = null;

        try {
            querySnapshot = future.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
            if (!querySnapshot.isEmpty()) {
                DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                String storedUsername = document.getString("Username");
                String storedPassword = document.getString("Password");

                if (storedUsername.equals(employeeUsername) && storedPassword.equals(employeePassword)) {
                    System.out.println("Login successful");
                    main m = new main();
                    m.changeScene("employeeHomePage.fxml");
                } else if (!storedUsername.equals(employeeUsername)) {
                    System.out.println ("Username Incorrect");
                    incorrectUsernameAlert();
                }
                else {
                    System.out.println ("Password Incorrect");
                    incorrectPasswordAlert();
                }
            } else {
                System.out.println ("Employee ID Incorrect");
                incorrectEmployeeIDAlert();
            }
    }

    public void incorrectEmployeeIDAlert () {
        Alert alert = new Alert (Alert.AlertType.WARNING);
        alert.setTitle("Incorrect Employee ID");
        alert.setContentText("Sorry! Employee ID not found. Please try again.");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            System.out.println ("User acknowledged incorrect input.");
        }
        else {
            System.out.println ("Invalid input please try again.");
        }
    }

    public void incorrectUsernameAlert () {
        Alert alert = new Alert (Alert.AlertType.WARNING);
        alert.setTitle("Incorrect Username");
        alert.setContentText("Sorry! Username not found. Please try again.");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            System.out.println ("User acknowledged incorrect input.");
        }
        else {
            System.out.println ("Invalid input please try again.");
        }
    }

    public void incorrectPasswordAlert () {
        Alert alert = new Alert (Alert.AlertType.WARNING);
        alert.setTitle("Incorrect Password");
        alert.setContentText("Sorry! Incorrect Password. Please try again.");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            System.out.println ("User acknowledged incorrect input.");
        }
        else {
            System.out.println ("Invalid input please try again.");
        }
    }

    public void handleOnMouseClick (MouseEvent event) throws IOException {
        System.out.println ("handleOnMouseClick called");

        main m = new main();
        m.changeScene("login.fxml");
    }
}
