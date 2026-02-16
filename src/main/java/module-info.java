module br.com.gbass.workshopjavafxjdbc {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.compiler;


    opens application to javafx.fxml;
    exports application;

}