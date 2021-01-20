package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Node;


public class UserLibraryController {
    @FXML
    private Button backButton;

    private int user;

    public void setUser(int id_user){
        this.user = id_user;
    }

    @FXML
    private void handleBack(ActionEvent event) throws Exception{
        try {
            System.out.println("back to store"); // hanya untuk debug, bisa dihapus
            ((Node) event.getSource()).getScene().getWindow().hide();
            storeMain(user);
        } catch (Exception e) {
            System.out.println("Error cause by : " + e.getCause());
            System.out.println("Error Message : " + e.getMessage());
        }
    }

    private void storeMain(int id_user) throws Exception {
        FXMLLoader loadLayout = new FXMLLoader(getClass().getResource("../view/storeLayout.fxml"));
        AnchorPane storePage = (AnchorPane) loadLayout.load();
        Scene scene = new Scene(storePage);
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Game Store");
        dialogStage.setResizable(false);
        dialogStage.setScene(scene);
        dialogStage.show();

        // store controller
        GameStoreController control = loadLayout.getController();
        control.setData(id_user);
    }
}
