package controller;

import java.sql.PreparedStatement;
import core.ConnectDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistController {
    @FXML private TextField setUsername,setPassword;
    @FXML private Button signupButton,cancelButton;
    @FXML private Label msgRegist;

    private Stage dialogStage;

    void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        try {
            dialogStage.close();
        } catch (Exception e) {
            System.out.println("Error cause by : "+ e.getCause());
            System.out.println("Error Message : "+e.getMessage());
        }
    }

    @FXML
    private void handleSignUp(ActionEvent event) {
        try {
            if (setUsername.getText().isEmpty() || setPassword.getText().isEmpty()) {
                msgRegist.setText("Fill username or password!");
            } else {
                String query = "insert into user_login (username, password) values (?, ?)";
                PreparedStatement stmt = ConnectDB.connect().prepareStatement(query);
                stmt.setString(1, setUsername.getText());
                stmt.setString(2, setPassword.getText());
                stmt.executeUpdate();
                stmt.close();
                msgRegist.setText("Registration succes, close the windows and try sign in with new account");
                setUsername.clear(); setPassword.clear();    
            }
        } catch (Exception e) {
            msgRegist.setText("Registration Failed!");
            msgRegist.setStyle("-fx-text-fill: #f51f1f");
            
            System.out.println("Error cause by : "+ e.getCause());
            System.out.println("Error Message : "+e.getMessage());
        }
    }
}
