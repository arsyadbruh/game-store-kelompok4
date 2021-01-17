package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;



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
        if(event.getSource() == loginButton){
            System.out.println("Login");
            String usern = textUsername.getText();
            String passw = textPassword.getText();
            System.out.println(textUsername.getText());
            System.out.println(textPassword.getText());
            if(usern.equalsIgnoreCase("admin") && passw.equalsIgnoreCase("1234")){
                System.out.println("Login Sukses");
                storeMain();
            }else{
                System.out.println("login gagal");
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Login Failed");
                alert.setContentText("Invalid Login Credential");
                alert.showAndWait();
            }
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
