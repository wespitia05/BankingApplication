package com.example.bankingapplication;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import org.w3c.dom.events.MouseEvent;

import java.time.format.DateTimeFormatter;

public class TranactionsaddedController {
    @FXML
    private TextField amount_TF;

    @FXML
    private ChoiceBox<?> category_CB;

    @FXML
    private DatePicker date_Picker;

    @FXML
    private TextField itemName_TF;

    @FXML
    public void handleCB_Listener(MouseEvent event) {

    }

    @FXML
    public void initialize() {
        // Initialize the ChoiceBox with items of type String
        category_CB.getItems().addAll("Food", "Bills", "Entertainment", "Health", "Streaming", "Retail", "Groceries", "Transportation");

        // Set an event handler for when the date is picked
        date_Picker.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                processTransaction();
            }
        });
    }


    private void processTransaction() {
        // Check if all fields are filled
        if (itemName_TF.getText().isEmpty() || category_CB.getValue() == null || amount_TF.getText().isEmpty() || date_Picker.getValue() == null) {
            System.out.println("Please fill in all fields before proceeding.");
            return;
        }

        String itemName = itemName_TF.getText();
        String category = (String) category_CB.getValue();
        String amount = amount_TF.getText();
        String date = date_Picker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yy"));

        // Log the transaction data for demonstration purposes (replace with your actual save logic)
        System.out.println("Processing transaction: " + itemName + ", " + category + ", " + amount + ", " + date);
    }


}
