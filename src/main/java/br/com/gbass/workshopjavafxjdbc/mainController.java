package br.com.gbass.workshopjavafxjdbc;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

import java.net.URL;
import java.util.ResourceBundle;

public class mainController implements Initializable {
    @FXML
    private MenuItem menuItemSeller;
    @FXML
    private MenuItem menuItemDepartment;
    @FXML
    private MenuItem menuItemAbout;


    //como nomear seu metodo seguindo a convenção:
    //nome do metodo = on, nome do controle, evento = onMenuItemSellerAction
    @FXML
    protected void onMenuItemSellerAction() {
        System.out.println("ON MENU ITEM SELLER ACTION");
    }
    @FXML
    protected void onMenuItemDepartmentAction() {
        System.out.println("ON MENU ITEM DEPARTMENT ACTION");
    }
    @FXML
    protected void onMenuItemAboutAction() {
        System.out.println("ON MENU ITEM ABOUT ACTION");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}