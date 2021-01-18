package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;

import java.sql.PreparedStatement;
import core.ConnectDB;


public class LoginController {
    private Stage dialogStage;

    @FXML
    private Button loginButton;

    @FXML
    private TextField textUsername;

    @FXML
    private PasswordField textPassword;

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }

    @FXML
    private void handleSign(ActionEvent event) throws Exception{
        String query = "select * from user_login where username = ? and password = ? ";
        PreparedStatement stmt = ConnectDB.connect().prepareStatement(query);
        stmt.setString(1, textUsername.getText());
        stmt.setString(2, textPassword.getText());
        stmt.execute();
        if(stmt.getResultSet().next()){
            System.out.println("Login Sukses");
            // int id = stmt.getResultSet().getInt("id");
            stmt.close();
            storeMain();
        }else{
            System.out.println("login gagal");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Username atau password salah");
            alert.showAndWait();
        }
    }

    private void storeMain() throws Exception{
        FXMLLoader loadLayout = new FXMLLoader(getClass().getResource("../view/storeLayout.fxml"));
        AnchorPane storePage = (AnchorPane) loadLayout.load();
        Scene scene = new Scene(storePage);
        dialogStage.setTitle("Game Store");
        dialogStage.setScene(scene);
        dialogStage.show();
    }
}
