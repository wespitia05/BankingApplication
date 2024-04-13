package com.example.bankingapplication;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

public class homePageController {

    //btn = button--- // TF = text Field
    @FXML
    Button save_btn;
    @FXML
    Button saveDraft_btn;
    @FXML
    TextField addAccountNum_TF;
    @FXML
    PieChart pieChart;
    @FXML
    TextField income_TF;
    @FXML
    TextField expenses_TF;
    @FXML
    TextField savings_TF;
    @FXML
    TextField balance_TF;
    @FXML
    TextField currency_TF;
    @FXML
    TextField debit_TF;

    @FXML
    public void initialize() {
        // Initialization code
    }

    @FXML
    private void handleSave_btn() {
        // Handle save button action
    }

    @FXML
    private void handleDraft_btn() {
        // Handle save as draft button action
    }
}
