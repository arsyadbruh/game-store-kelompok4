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
    private Stage dialogStage;
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

    void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        System.out.println("cancel resgist"); // hanya untuk debug, bisa dihapus
        try {
            System.out.println("cancel succes"); // hanya untuk debug, bisa dihapus
            dialogStage.close();
        } catch (Exception e) {
            System.out.println("Error cause by " + e.getCause());
            System.out.println("Error cause by : "+ e.getCause());
            System.out.println("Error Message : "+e.getMessage());
        }
    }

    /** QUERY FOR SQL
     * INSERT INTO `user_login` (`id_user`, `username`, `password`, `saldo`) VALUES ('username', 'password');
     * id_user auto increment
     * saldo default 0
     */

    @FXML
    private void handleSignUp(ActionEvent event) {
        try {
            // cek apakah field username atau password kosong 
            // jika kosong maka registrasi gagal
            if (setUsername.getText().isEmpty() || setPassword.getText().isEmpty()) {
                msgRegist.setText("Fill username or password!"); // ubah text label untuk menampilkan pesan
            } else {
                System.out.println("Regist"); // hanya untuk debug, bisa dihapus
                String query = "insert into user_login (username, password) values (?, ?)"; // query SQL
                PreparedStatement stmt = ConnectDB.connect().prepareStatement(query);
                stmt.setString(1, setUsername.getText());
                stmt.setString(2, setPassword.getText());
                stmt.executeUpdate();
                stmt.close();
                msgRegist.setText("Registration succes, close the windows and try sign in with new account"); // ubah text label untuk menampilkan pesan
                setUsername.clear(); // menghapus text yang aada di field
                setPassword.clear();    
            }

        } catch (Exception e) {
            msgRegist.setText("Registration Failed!");
            msgRegist.setStyle("-fx-text-fill: #f51f1f"); // set warna text label ke merah
            System.out.println("Error cause by : "+ e.getCause());
            System.out.println("Error Message : "+e.getMessage());
        }
    }
}
