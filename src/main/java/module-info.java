module com.example.bankingapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.healthmarketscience.jackcess;

    opens com.example.bankingapplication to javafx.fxml;
    exports com.example.bankingapplication;
}