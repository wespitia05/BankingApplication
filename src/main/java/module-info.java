module com.example.bankingapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.healthmarketscience.jackcess;
    requires org.mongodb.driver.core;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;

    opens com.example.bankingapplication to javafx.fxml;
    exports com.example.bankingapplication;
}