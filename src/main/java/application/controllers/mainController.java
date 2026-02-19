package application.controllers;

import application.controllers.department.departmentListController;
import application.controllers.seller.sellerListController;
import application.mainApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.departmentService;
import model.services.sellerService;
import util.Alerts;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

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
        loadView("/gui/sellerList.fxml",(sellerListController controller) ->{
            controller.setSellerService(new sellerService());
            controller.updateTableView();
        });
    }
    @FXML
    public void onMenuItemDepartmentAction() {
        //2º parametro -> ação de inicialização como parâmetro com expressão lambda
        loadView("/gui/departmentList.fxml",(departmentListController controller) ->{
            controller.setDepartmentService(new departmentService());
            controller.updateTableView();
        });
    }
    @FXML
    public void onMenuItemAboutAction() {
        loadView("/gui/aboutView.fxml", x -> {});
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    //synchronized = garante que o processo não será interrompido durante o multithreading
    private synchronized <T> void loadView(String pathView, Consumer<T> initializingAction) {
        try{
            //getResource() -> NÃO usa caminho do projeto, ele usa o classpath (Target/classes) depois que o projeto é compilado (caminho relativo)
            FXMLLoader loader = new FXMLLoader(getClass().getResource(pathView));
            VBox newVBox = loader.load();

            //trecho de código para manter o menuBar mesmo depois de carregar outra tela
            Scene mainScene = mainApplication.getMainScene();
            VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
            Node mainMenu = mainVBox.getChildren().getFirst();
            mainVBox.getChildren().clear();
            mainVBox.getChildren().add(mainMenu);
            mainVBox.getChildren().addAll(newVBox.getChildren());

            //linhas de código que executa o segundo parâmetro
            T controller = loader.getController();
            initializingAction.accept(controller);

        } catch (IOException error) {
            Alerts.showAlert("IO Exception", "Error Loading view", error.getMessage(), Alert.AlertType.ERROR);
            error.printStackTrace();
        }

    }
}