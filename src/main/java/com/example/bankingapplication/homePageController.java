package com.example.bankingapplication;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.Stage;

public class homePageController {

    //btn = button--- // TF = text Field
    @FXML
    private Button save_btn;
    @FXML
    private Button saveDraft_btn;
    @FXML
    private TextField addAccountNum_TF;
    @FXML
    private PieChart pieChart;
    @FXML
    private TextField income_TF;
    @FXML
    private TextField expenses_TF;
    @FXML
    private TextField savings_TF;
    @FXML
    private TextField balance_TF;
    @FXML
    private TextField currency_TF;
    @FXML
    private TextField debit_TF;

    @FXML
    public void initialize() {
        // Initialization code
        generatePieChart();
    }

    @FXML
    private void handleSave_btn() {
        // Handle save button action
    }

    @FXML
    private void handleDraft_btn() {
        // Handle save as draft button action
    }

    //expense report for pie chart
    // Method to generate and display the pie chart
    public void generatePieChart() {
        // Sample expense data
        ObservableList<PieChart.Data> expenses = FXCollections.observableArrayList(
                new PieChart.Data("Food", 500),
                new PieChart.Data("Rent", 1000),
                new PieChart.Data("Transportation", 300),
                new PieChart.Data("Entertainment", 200),
                new PieChart.Data("Utilities", 400)
        );

        // Set the pie chart data
        pieChart.setData(expenses);
        pieChart.setTitle("Expense Report");

        //enabel legend
        pieChart.setLegendVisible(true);
        //customize label color font
        pieChart.getData().forEach(data->
                data.getNode().setStyle("-fx-font-weight: bold; -fx-font-size: 12pt;"));

        //put all the design parts into the css file.
    }

}
