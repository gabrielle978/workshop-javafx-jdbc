package util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {
    public static Stage currentStage (ActionEvent event){
        Node node = (Node) event.getSource();
        return (Stage) node.getScene().getWindow();
    }

    public static Integer tryParseToInt(String str){
       try {
           return Integer.parseInt(str);
       } catch (NumberFormatException e){
           return null;
       }

    }
}
