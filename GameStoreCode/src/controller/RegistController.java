package controller;

import java.sql.PreparedStatement;
import core.ConnectDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.Node;

public class RegistController {
    @FXML
    private TextField setUsername;

    @FXML
    private TextField setPassword;

    @FXML
    private Button signupButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label msgRegist; //label message di atas form

    @FXML
    private void handleCancel(ActionEvent event) {
        System.out.println("cancel resgist"); // hanya untuk debug, bisa dihapus
        try {
            System.out.println("cancel succes"); // hanya untuk debug, bisa dihapus
            ((Node)event.getSource()).getScene().getWindow().hide();
        } catch (Exception e) {
            System.out.println("Error cause by " + e.getCause());
            System.out.println("Error cause by : "+ e.getCause());
            System.out.println("Error Message : "+e.getMessage());
        }
    }

    /** QUERY FOR SQL
     * INSERT INTO `colum user` (`id_user`, `username`, `password`, `saldo`) VALUES ('username', 'password');
     * id_user auto increment
     * saldo default 0
     */

    @FXML
    private void handleSignUp(ActionEvent event) {
        try {
            if (setUsername.getText().isEmpty() || setPassword.getText().isEmpty()) {
                msgRegist.setText("Isi username atau passwordnya");
            } else {
                System.out.println("Regist"); // hanya untuk debug, bisa dihapus
                String query = "insert into user_login (username, password) values (?, ?)";
                PreparedStatement stmt = ConnectDB.connect().prepareStatement(query);
                stmt.setString(1, setUsername.getText());
                stmt.setString(2, setPassword.getText());
                stmt.executeUpdate();
                stmt.close();
                msgRegist.setText("Resgistration succes, close the windows and try sign in with new account");
                setUsername.clear();
                setPassword.clear();    
            }

        } catch (Exception e) {
            System.out.println("Error cause by : "+ e.getCause());
            System.out.println("Error Message : "+e.getMessage());
        }
    }
}
