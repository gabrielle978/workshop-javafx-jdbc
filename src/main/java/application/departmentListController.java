package application;

import com.sun.tools.javac.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.departmentService;
import util.Alerts;
import util.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class departmentListController implements Initializable, dataChangeListener {
    private departmentService service;

    @FXML
    private TableView <Department> departmentTableView;

    @FXML
    private TableColumn <Department, Integer> tableColumnId;

    @FXML
    private TableColumn <Department, String> tableColumnName;

    @FXML
    private Button btNew;

    private ObservableList<Department> observableList;

    @FXML
    public void onBtNewAction(ActionEvent event){
        Stage parentStage = Utils.currentStage(event);
        Department obj = new Department();
        createDialogForm(obj, parentStage, "/departmentForm.fxml");
    }

    public void setDepartmentService (departmentService service){
        this.service = service;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    private void initializeNodes() {
        //o java 9+ não permite reflection automaticamente ("id" e "name")
        //o pacote precisa ser explicitamente aberto;
        //add no module info -> opens model.entities to javafx.base;
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new  PropertyValueFactory<>("name"));

        Stage stage = (Stage) mainApplication.getMainScene().getWindow();
        departmentTableView.prefHeightProperty().bind(stage.heightProperty());

    }

    public void updateTableView(){
        if (service == null){
            throw new IllegalStateException("service was null");
        }
        List<Department> list = service.findAll();
        observableList = FXCollections.observableArrayList(list);
        departmentTableView.setItems(observableList);
    }

    private void createDialogForm(Department obj, Stage parentStage, String pathView){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(pathView));
            Pane pane = loader.load();

            departmentFormController controller = loader.getController();
            controller.setDepartment(obj);
            controller.setDepartmentService(new departmentService());
            controller.subscribeDataChangeListener(this);
            controller.updateFormData();

            //função para carregar e preencher dados no formulário para novo departamento;
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Enter department data");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();
        }
        catch (IOException e){
            e.printStackTrace();
            //Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public void onDataChanged() {
        updateTableView();
    }
}
