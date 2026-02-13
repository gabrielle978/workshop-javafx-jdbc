module br.com.gbass.workshopjavafxjdbc {
    requires javafx.controls;
    requires javafx.fxml;


    opens br.com.gbass.workshopjavafxjdbc to javafx.fxml;
    exports br.com.gbass.workshopjavafxjdbc;

}