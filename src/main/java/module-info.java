module br.com.gbass.workshopjavafxjdbc {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.compiler;


    opens application to javafx.fxml;
    opens model.entities to javafx.base;

    exports application;

}