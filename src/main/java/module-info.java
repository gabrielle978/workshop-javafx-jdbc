module br.com.gbass.workshopjavafxjdbc {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.compiler;
    requires java.sql;


    opens application to javafx.fxml;
    opens model.entities to javafx.base;

    exports application;
    exports application.controllers;
    opens application.controllers to javafx.fxml;

}