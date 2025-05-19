module com.example.exercicetp6 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.exercicetp6 to javafx.graphics;
    exports com.example.exercicetp6;
    exports com.example.exercicetp6.controller;
    opens com.example.exercicetp6.controller to javafx.fxml, javafx.graphics;
    opens com.example.exercicetp6.model to javafx.base;
}