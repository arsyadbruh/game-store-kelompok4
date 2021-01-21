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
    private PasswordField textPassword; // untuk password yang di hide

    @FXML
    private TextField showpassw; // untuk password yang di show

    @FXML
    private CheckBox toggleShow; // toggle show atau hide password

    // set dialogstage dari main start
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    // initialisasi() dijalankan pertama kali saat controll ini dipanggil
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.toggleShowSelect(null); // set password menjadi hidden pertama kali saat di run
    }

    // login connect to database
    @FXML
    private void handleSign(ActionEvent event) throws Exception {
        System.out.println("user login"); // hanya untuk debug, bisa dihapus
        String passw = getPassword();

        try {
            String query = "select * from user_login where username = ? and password = ? "; // query SQL
            PreparedStatement stmt = ConnectDB.connect().prepareStatement(query);
            stmt.setString(1, textUsername.getText());
            stmt.setString(2, passw);
            stmt.execute();
            if (stmt.getResultSet().next()) {
                ((Node) event.getSource()).getScene().getWindow().hide(); // jika login berhasil, hide window login
                System.out.println("Login Sukses");
                int id = stmt.getResultSet().getInt("id_user"); // untuk menampilkan username yang login
                stmt.close();
                storeMain(id);
            } else {
                System.out.println("login gagal");
                Alert alert = new Alert(AlertType.ERROR); // jika login gagal menampilkan window alert
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Username atau password salah");
                alert.showAndWait(); //showAndWait() menunggu respon dari user
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

    /** toggleShowSelect()
     * adalah method untuk show atau hide password pada login layout
     * disini untuk show and hide digunakan trik dua field yang di tumpuk
     * TEXTFIELD as showpassw untuk password show
     * PASSWORDFIELD as textPassword untuk password hide
     * saat toogle selected maka PASSWORDFIELD akan hilang dan TEXTFIELD akan muncul
     * method ini akan dijalankan saat initialisasi
     */
    @FXML
    private void toggleShowSelect(ActionEvent event){
        try {
            if (toggleShow.isSelected()) { // cek apakah checkbox selected atau tidak
                System.out.println("Show Password");  // hanya untuk debug, bisa dihapus
                showpassw.setText(textPassword.getText()); // set text dari TEXTFIELD sama dengan dari PASSWORDFIELD
                textPassword.setVisible(false);
                showpassw.setVisible(true);
                return;
            }
            // setVisible() => jika false maka objeknya disembunyikan dan sebaliknya
            System.out.println("Hide Password Status : True"); // hanya untuk debug, bisa dihapus
            textPassword.setText(showpassw.getText()); // update PASSWORDFIELD agar sama dengan yang ada di  TEXTFIELD
            showpassw.setVisible(false);
            textPassword.setVisible(true);

        } catch (Exception e) {
            System.out.println("Error toggleShowSelect");
            System.out.println("Error cause by : " + e.getCause());
            System.out.println("Error Message : " + e.getMessage());
        }
        
    }

    /**
     * getPassword() adaalah method untuk mendapatkan password dari TEXTFIELD ataupun PASSWORDFIELD
     * jadi controller dapat mendapatkan password dari field manapun itu
     * tergantung dari kondisi checkbox
     */
    private String getPassword() {
        /**
         * aku ganti if ... else sebelumnya dengan ternary
         * ternary => (kondisi) ? <nilai jika true> : <nilai jika false>;
         * lebih singkat jadi lebih enak bacanya.
         * lebih lanjut bisa baca di https://www.w3schools.com/java/java_conditions.asp
         */
        return (toggleShow.isSelected()) ? showpassw.getText() : textPassword.getText();
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
