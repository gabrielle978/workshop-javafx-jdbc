package application.controllers.seller;

import application.dataChangeListener;
import db.DbException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import model.entities.Department;
import model.entities.Seller;
import model.exceptions.ValidationException;
import model.services.sellerService;
import util.Alerts;
import util.Constraints;
import util.Utils;
import model.services.departmentService;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class sellerFormController implements Initializable {
    private Seller entity;
    //sellS = sellerService
    private sellerService sellS;
    private List<dataChangeListener> dataChangeListeners = new ArrayList<>();
    //depS = departmentService
    private departmentService depS;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtEmail;

    @FXML
    private DatePicker dpBirthDate;

    @FXML
    private TextField txtBaseSalary;

    @FXML
    private ComboBox<Department> departmentComboBox;

    @FXML
    private Label labelErrorName;

    @FXML
    private Label labelErrorEmail;

    @FXML
    private Label labelErrorBirthDate;

    @FXML
    private Label labelErrorBaseSalary;

    @FXML
    private Button btSave;

    @FXML
    private Button btCancel;

    private ObservableList<Department> observableList;

    public void setseller(Seller entity){
        this.entity = entity;
    }

    public void setServices(sellerService sellS, departmentService depS){
        this.sellS = sellS;
        this.depS = depS;
    }

    public void subscribeDataChangeListener(dataChangeListener listener){
        dataChangeListeners.add(listener);
    }

    @FXML
    public void onBtSaveAction(ActionEvent event){
        //exceções que avisam caso o programador esqueça de injetar dependências
        if (entity == null){
            throw new IllegalStateException("Entity is null");
        }
        if (sellS == null){
            throw new IllegalStateException(("Service is null"));
        }
        try{
            entity = getFormData();
            sellS.saveOrUpdate(entity);
            notifyDataChangeListeners();
            Utils.currentStage(event).close();
        } catch (DbException e){
            Alerts.showAlert("Error saving object", null, e.getMessage(), Alert.AlertType.ERROR);
        }
        catch (ValidationException e){
            setErrorMessages(e.getErrors());
        }

    }

    private void notifyDataChangeListeners() {
        for (dataChangeListener listener : dataChangeListeners){
            listener.onDataChanged();
        }
    }

    private Seller getFormData() {
        Seller obj = new Seller();

        ValidationException exception = new ValidationException("Validation error");

        obj.setId(Utils.tryParseToInt(txtId.getText()));

        if (txtName.getText() == null || txtName.getText().trim().equals("")){
            exception.addError("name", "Field can't be empty");
        }
        obj.setName(txtName.getText());
        if (!exception.getErrors().isEmpty()){
            throw exception;
        }
        return obj;
    }

    @FXML
    public void onBtCancelAction(ActionEvent event){
        Utils.currentStage(event).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void initializeNodes(){
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 60);
        Constraints.setTextFieldDouble(txtBaseSalary);
        Constraints.setTextFieldMaxLength(txtEmail, 50);
        Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");

        initializeComboBoxDepartment();
    }

    public void updateFormData(){
        if (entity == null){
            throw new IllegalStateException("entity was null.");
        }
        txtId.setText(String.valueOf(entity.getId()));
        txtName.setText(entity.getName());
        txtEmail.setText(entity.getEmail());
        Locale.setDefault(Locale.US);
        txtBaseSalary.setText(String.format("%.2f", entity.getBaseSalary()));
        if (entity.getBirthDate() != null){
            dpBirthDate.setValue(
                    ((java.sql.Date)entity.getBirthDate()).toLocalDate());
        }
        if(entity.getDepartment() == null){
            departmentComboBox.getSelectionModel().selectFirst();
        }
        departmentComboBox.setValue(entity.getDepartment());
    }

    public void loadAssociatedObjects(){
        List<Department> list = depS.findAll();
        observableList = FXCollections.observableArrayList(list);
        departmentComboBox.setItems(observableList);
    }

    private void setErrorMessages(Map<String, String> errors){
        Set<String> fields = errors.keySet();
        if (fields.contains("name")){
            labelErrorName.setText(errors.get("name"));
        }
    }

    private void initializeComboBoxDepartment(){
        Callback<ListView<Department>, ListCell<Department>> factory = lv ->
                new ListCell<Department>(){
                    @Override
                    protected void updateItem(Department item, boolean empty){
                        super.updateItem(item,empty);
                        setText(empty? "": item.getName());
                    }
                };
        departmentComboBox.setCellFactory(factory);
        departmentComboBox.setButtonCell(factory.call(null));
    }
}
