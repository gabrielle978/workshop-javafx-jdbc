package application;

import com.sun.tools.javac.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.departmentService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class departmentListController implements Initializable {
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
    public void onBtnAction(){
        System.out.println("onBtnAction");
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
}
