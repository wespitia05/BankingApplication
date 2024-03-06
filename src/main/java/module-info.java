module com.example.bankingapplication {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.bankingapplication to javafx.fxml;
    exports com.example.bankingapplication;
}