package application.controllers;

import application.dataChangeListener;
import application.mainApplication;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Seller;
import model.services.sellerService;
import util.Alerts;
import util.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class sellerListController implements Initializable, dataChangeListener {
    private sellerService service;

    @FXML
    private TableView <Seller> SellerTableView;

    @FXML
    private TableColumn <Seller, Integer> tableColumnId;

    @FXML
    private TableColumn <Seller, String> tableColumnName;

    @FXML
    private Button btNew;

    @FXML
    private TableColumn <Seller, String> tableColumnEmail;

    @FXML
    private TableColumn <Seller, Date> tableColumnBirthDate;

    @FXML
    private TableColumn <Seller, Double> tableColumnBaseSalary;


    @FXML
    private TableColumn<Seller, Seller> tableColumnEDIT;

    @FXML
    private TableColumn<Seller, Seller> tableColumnREMOVE;

    private ObservableList<Seller> observableList;

    @FXML
    public void onBtNewAction(ActionEvent event){
        Stage parentStage = Utils.currentStage(event);
        Seller obj = new Seller();
        //createDialogForm(obj, parentStage, "/SellerForm.fxml");
    }

    public void setSellerService (sellerService service){
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
        tableColumnEmail.setCellValueFactory(new  PropertyValueFactory<>("email"));
        tableColumnBirthDate.setCellValueFactory(new  PropertyValueFactory<>("birthDate"));
        Utils.formatTableColumnDate(tableColumnBirthDate, "dd/MM/yyyy");
        tableColumnBaseSalary.setCellValueFactory(new  PropertyValueFactory<>("baseSalary"));
        Utils.formatTableColumnDouble(tableColumnBaseSalary, 2);


        Stage stage = (Stage) mainApplication.getMainScene().getWindow();
        SellerTableView.prefHeightProperty().bind(stage.heightProperty());

    }

    public void updateTableView(){
        if (service == null){
            throw new IllegalStateException("service was null");
        }
        List<Seller> list = service.findAll();
        observableList = FXCollections.observableArrayList(list);
        SellerTableView.setItems(observableList);
        initEditButtons();
        initRemoveButtons();
    }

    private void createDialogForm(Seller obj, Stage parentStage, String pathView){
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(pathView));
//            Pane pane = loader.load();
//
//            sellerFormController controller = loader.getController();
//            controller.setSeller(obj);
//            controller.setSellerService(new SellerService());
//            controller.subscribeDataChangeListener(this);
//            controller.updateFormData();
//
//            //função para carregar e preencher dados no formulário para novo departamento;
//            Stage dialogStage = new Stage();
//            dialogStage.setTitle("Enter Seller data");
//            dialogStage.setScene(new Scene(pane));
//            dialogStage.setResizable(false);
//            dialogStage.initOwner(parentStage);
//            dialogStage.initModality(Modality.WINDOW_MODAL);
//            dialogStage.showAndWait();
//        }
//        catch (IOException e){
//            e.printStackTrace();
//            //Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
//        }
  }

    @Override
    public void onDataChanged() {
        updateTableView();
    }

    private void initEditButtons(){
        tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnEDIT.setCellFactory(param -> new TableCell<Seller, Seller>(){
            private final Button button = new Button("edit");

          @Override
            protected void updateItem(Seller obj, boolean empty){
              super.updateItem(obj, empty);
              if (obj == null){
                  setGraphic(null);
                  return;
              }
              setGraphic(button);
              button.setOnAction(
                      event -> createDialogForm(obj, Utils.currentStage(event), "/SellerForm.fxml"));
          }
        });
    }

    private void initRemoveButtons(){
        tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnREMOVE.setCellFactory(param -> new TableCell<Seller, Seller>(){
            private final Button button = new Button("remove");

            @Override
            protected void updateItem(Seller obj, boolean empty){
                super.updateItem(obj, empty);
                if (obj == null){
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(
                        event -> removeEntity(obj));
            }
        });
    }

    private void removeEntity(Seller obj) {
        Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");
        if (result.isPresent() && result.get() == ButtonType.OK){
            if (service == null){
                throw new IllegalStateException("Service was null");
            }
            try {
                service.remove(obj);
                updateTableView();
            }
            catch (Exception e){
                e.printStackTrace();
                Alerts.showAlert("Error removing object", null, e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }
}
