module com.example.goblinshumans {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.goblinshumans to javafx.fxml;
    exports com.example.goblinshumans;
}