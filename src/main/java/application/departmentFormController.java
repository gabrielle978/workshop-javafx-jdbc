package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import util.Constraints;

import java.net.URL;
import java.util.ResourceBundle;

public class departmentFormController implements Initializable {
    private Department entity;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private Label labelErrorName;

    @FXML
    private Button btSave;

    @FXML
    private Button btCancel;

    public void setDepartment(Department entity){
        this.entity = entity;
    }
    @FXML
    public void onBtSaveAction(){
        System.out.println("save button clicked");
    }

    @FXML
    public void onBtCancelAction(){
        System.out.println("cancel button clicked");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void initializeNodes(){
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 32);
    }

    public void updateFormData(){
        if (entity == null){
            throw new IllegalStateException("entity was null.");
        }
        txtId.setText(String.valueOf(entity.getId()));
        txtName.setText(String.valueOf(entity.getName()));
    }
}
