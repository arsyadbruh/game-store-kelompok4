package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;

import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import core.ConnectDB;

public class LoginController implements Initializable{
    private Stage dialogStage;

    @FXML
    private Button loginButton;

    @FXML
    private Button signupButton;

    @FXML
    private TextField textUsername;

    @FXML
    private PasswordField textPassword;

    @FXML
    private TextField showpassw;

    @FXML
    private CheckBox toggleShow;

    // set dialogstage dari main start
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.toggleShowSelect(null);
    }

    // login connect to database
    @FXML
    private void handleSign(ActionEvent event) throws Exception {
        System.out.println("user login"); // hanya untuk debug, bisa dihapus
        String passw = getPassword();

        try {
            String query = "select * from user_login where username = ? and password = ? ";
            PreparedStatement stmt = ConnectDB.connect().prepareStatement(query);
            stmt.setString(1, textUsername.getText());
            stmt.setString(2, passw);
            stmt.execute();
            if (stmt.getResultSet().next()) {
                ((Node) event.getSource()).getScene().getWindow().hide();
                System.out.println("Login Sukses");
                int id = stmt.getResultSet().getInt("id_user"); // untuk menampilkan username yang login
                stmt.close();
                storeMain(id);
            } else {
                System.out.println("login gagal");
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Username atau password salah");
                alert.showAndWait();
            }
        } catch (Exception e) {
            System.out.println("Error handleSign");
            System.out.println("Error cause by : " + e.getCause());
            System.out.println("Error Message : " + e.getMessage());
        }

    }

    @FXML
    private void handleSignup(ActionEvent event) throws Exception {
        System.out.println("user signup"); // hanya untuk debug, bisa dihapus
        try {
            FXMLLoader loadLayout = new FXMLLoader(getClass().getResource("../view/formLayout.fxml"));
            AnchorPane formPage = (AnchorPane) loadLayout.load();
            Scene scene = new Scene(formPage);
            Stage regisStage = new Stage();
            regisStage.setTitle("Resgistration");
            regisStage.setResizable(false);
            regisStage.setScene(scene);
            regisStage.showAndWait();
        } catch (Exception e) {
            System.out.println("Error handleSignup");
            System.out.println("Error cause by : " + e.getCause());
            System.out.println("Error Message : " + e.getMessage());
        }
    }

    @FXML
    private void toggleShowSelect(ActionEvent event){
        try {
            if (toggleShow.isSelected()) {
                System.out.println("Show Password");  // hanya untuk debug, bisa dihapus
                showpassw.setText(textPassword.getText());
                textPassword.setVisible(false);
                showpassw.setVisible(true);
                return;
            }
            System.out.println("Hide Password Status : True"); // hanya untuk debug, bisa dihapus
            textPassword.setText(showpassw.getText());
            showpassw.setVisible(false);
            textPassword.setVisible(true);

        } catch (Exception e) {
            System.out.println("Error toggleShowSelect");
            System.out.println("Error cause by : " + e.getCause());
            System.out.println("Error Message : " + e.getMessage());
        }
        
    }

    // method untuk get password
    private String getPassword() {
        if (toggleShow.isSelected()) {
            return showpassw.getText();
        } else {
            return textPassword.getText();
        }
    }

    // method untuk memanggil store layout
    private void storeMain(int id_user) throws Exception {
        try {
            FXMLLoader loadLayout;
            loadLayout = new FXMLLoader(getClass().getResource("../view/storeLayout.fxml"));
            AnchorPane storePage = (AnchorPane) loadLayout.load();
            Scene scene = new Scene(storePage);
            dialogStage.setTitle("Game Store");
            dialogStage.setResizable(false);
            dialogStage.setScene(scene);
            dialogStage.show();

            // store controller
            GameStoreController control = loadLayout.getController();
            control.setData(id_user);
        }catch (Exception e){
            System.out.println("storeMain");
            System.out.println("Error cause by : " + e.getCause());
            System.out.println("Error Message : " + e.getMessage());
        }

    }

}
