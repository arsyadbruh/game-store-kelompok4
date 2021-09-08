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

    @FXML private Button loginButton, signupButton;
    @FXML private TextField textUsername, showpassw;
    @FXML private PasswordField textPassword;
    @FXML private CheckBox toggleShow;

    private Stage dialogStage;

    /**
     * set the dialog stage for login page
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.toggleShowSelect(null);
    }

    @FXML
    private void handleSign(ActionEvent event) throws Exception {
        String passw = getPassword();

        try {
            String query = "select * from user_login where username = ? and password = ? ";
            PreparedStatement stmt = ConnectDB.connect().prepareStatement(query);
            stmt.setString(1, textUsername.getText());
            stmt.setString(2, passw);
            stmt.execute();
            if (stmt.getResultSet().next()) {
                ((Node) event.getSource()).getScene().getWindow().hide();
                int id = stmt.getResultSet().getInt("id_user");
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
        System.out.println("user signup");
        try {
            FXMLLoader loadLayout = new FXMLLoader(getClass().getResource("../view/formLayout.fxml"));
            AnchorPane formPage = (AnchorPane) loadLayout.load();
            Scene scene = new Scene(formPage);
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Resgistration");
            dialogStage.setResizable(false);
            dialogStage.setScene(scene);

            RegistController control = loadLayout.getController();
            control.setDialogStage(dialogStage);
            dialogStage.showAndWait();
            
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
                showpassw.setText(textPassword.getText());
                textPassword.setVisible(false);
                showpassw.setVisible(true);
                return;
            }

            textPassword.setText(showpassw.getText());
            showpassw.setVisible(false);
            textPassword.setVisible(true);

        } catch (Exception e) {
            System.out.println("Error toggleShowSelect");
            System.out.println("Error cause by : " + e.getCause());
            System.out.println("Error Message : " + e.getMessage());
        }
        
    }

    /**
     * getPassword() adalah method untuk mendapatkan password dari TEXTFIELD ataupun PASSWORDFIELD
     * jadi controller dapat mendapatkan password dari field manapun itu
     * tergantung dari kondisi checkbox
     */
    private String getPassword() {
        return (toggleShow.isSelected()) ? showpassw.getText() : textPassword.getText();
    }

    /**
     * storeMain() untuk memanggil store layout berdasarkan id user
     * @param id_user
     * @throws Exception
     */
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

            GameStoreController control = loadLayout.getController();
            control.setData(id_user);
        }catch (Exception e){
            System.out.println("storeMain");
            System.out.println("Error cause by : " + e.getCause());
            System.out.println("Error Message : " + e.getMessage());
        }
    }
} // end class
