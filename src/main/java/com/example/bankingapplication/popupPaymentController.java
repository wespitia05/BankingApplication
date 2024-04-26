package com.example.bankingapplication;

import io.netty.util.internal.SocketUtils;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class popupPaymentController {
  //  homePageController controller = new homePageController();
    @FXML
    public ListView cart;
    @FXML
    public Label totalLabel;
    @FXML
    public ChoiceBox cb;

    public String subtotal;

    public void initialize() {
        cb.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                choiceBoxListener();
            }
        });
    }

    public void cardNumberListener() {

    }

    public void cardExpDateListener() {

    }

    public void cardSecurityCodeListener() {

    }

    public void payBtnHandler() {

    }

    public void closeBtnHandler() {
        Stage stage = (Stage) cart.getScene().getWindow();
        stage.close();
    }

    public void updateTotalLabel(String txt) {
        totalLabel.setText(txt);
    }

    public void choiceBoxListener() {

        String s = (String)cb.getValue();
        if (s != null) {
            double showing = Double.parseDouble(s);
            System.out.println(subtotal);
            double sub = Double.parseDouble(subtotal);


            double newTotal = showing * sub;
            String num = String.format("%.2f", newTotal);
            System.out.println(num);
            updateTotalLabel("Total:      $" + num);
        }
    }
}
