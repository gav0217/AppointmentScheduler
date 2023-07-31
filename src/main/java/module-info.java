module com.example.fofiuappointments {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.fofiuappointments to javafx.fxml;
    exports com.example.fofiuappointments;
    exports model;
    exports controller;
    opens controller to javafx.fxml;
    exports db;
    opens db to javafx.fxml;
}