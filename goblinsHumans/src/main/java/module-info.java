module com.example.goblinshumans {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.goblinshumans to javafx.fxml;
    exports com.example.goblinshumans;
}