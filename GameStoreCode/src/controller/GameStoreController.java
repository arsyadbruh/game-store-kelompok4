package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import core.ConnectDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Node;

public class GameStoreController {
    private int id_user = 0;
    private int userSaldo = 0;
    private int tempSaldo = 0;

    @FXML
    private Button libraryButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Label user;

    @FXML
    private Label saldo; // label saldo pada layout

    @FXML
    private Button wdTwo; // tombol buy Watch Dog 2

    @FXML
    private Button wdLegion; // tombol buy Watch Dog : Legion

    // mendapatkan username dan saldo yang lagin berdasarkan id
    private void userinfo() throws Exception {
        String query = "select * from user_login where id_user = ?";
        PreparedStatement stmt = ConnectDB.connect().prepareStatement(query);
        stmt.setInt(1, id_user);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            userSaldo = rs.getInt("saldo");
            String wallet = Integer.toString(userSaldo);
            user.setText(rs.getString("username"));
            saldo.setText(wallet);
        }
        stmt.close();
    }

    // method untuk mendapatkan harga game dari database
    private int getPriceGame(int btn_id) throws Exception {
        String query = "select * from games where id_game = ?";
        PreparedStatement stmt = ConnectDB.connect().prepareStatement(query);
        stmt.setInt(1, btn_id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            int priceGame = rs.getInt("harga");
            System.out.println(rs.getInt("harga"));
            return priceGame;
        }
        System.out.println("Gagal dapat harga");
        return 0;
    }

    public void setData(int id_user) throws Exception {
        this.id_user = id_user;
        userinfo();
    }

    @FXML
    void handleGame(ActionEvent event) throws Exception {        
        /**
         * event.getSource() untuk sumber event yang lagi berjalan
         * contoh getSource() == wdTwo  berarti cek event yang lagi berjalan berasal dari tombol wdTwo
         * event bisa berasal dari tombol yang ditekan atau lainya.
         */

        if (event.getSource() == wdTwo) { //jika buy watch dog 2 ditekan
            updateSaldo(1);
            addToLibrary(1);
        }

        if (event.getSource() == wdLegion) { // jika buy watch dog legion ditekan
            updateSaldo(2);
            addToLibrary(2);
        }
        
    }

    @FXML
    void handleLibrary(ActionEvent event) throws Exception {
        try {
            System.out.println("enter Library"); // hanya untuk debug, bisa dihapus
            ((Node) event.getSource()).getScene().getWindow().hide();
            FXMLLoader loadLayout = new FXMLLoader(getClass().getResource("../view/Library.fxml"));
            AnchorPane libPage = (AnchorPane) loadLayout.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("GameStore | My Library");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setResizable(false);
            Scene scene = new Scene (libPage);
            dialogStage.setScene(scene);

            UserLibraryController control = loadLayout.getController();
            control.setUser(id_user);
            control.setDialogStage(dialogStage);
            dialogStage.show();
        } catch (Exception e) {
            System.out.println("Error handleLibrary");
            System.out.println("Error cause by : "+ e.getCause());
            System.out.println("Error Message : "+e.getMessage());
        }
    }

    @FXML
    void handleLogout(ActionEvent event) throws Exception {
        System.out.println("user Logout"); // hanya untuk debug, bisa dihapus
        try {
            ((Node)event.getSource()).getScene().getWindow().hide();
            FXMLLoader loadLayout = new FXMLLoader(getClass().getResource("../view/loginLayout.fxml"));
            AnchorPane loginPage = (AnchorPane) loadLayout.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("GameStore | Login");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setResizable(false);
            Scene scene = new Scene (loginPage);
            dialogStage.setScene(scene);

            System.out.println("logout sukses"); // hanya untuk debug, bisa dihapus
            
            // login controller
            LoginController control = loadLayout.getController();
            control.setDialogStage(dialogStage);
            dialogStage.showAndWait();
        } catch (Exception e) {
            System.out.println("Error handleLogout");
            System.out.println("Error cause by : "+ e.getCause());
            System.out.println("Error Message : "+e.getMessage());
        }
        
    }

    // method untuk validasi pembelian
    private boolean validation(int btn_id) throws Exception {
        int price = getPriceGame(btn_id);
        
        if (!(userSaldo < price)) {
            System.out.println("Pembelian berhasil"); // hanya untuk debug, bisa dihapus
            this.tempSaldo = userSaldo - price;
            this.userSaldo = tempSaldo;
            return true;
        }
        
        return false;
    }

    // method untuk update saldo ke database
    private void updateSaldo(int btn_id) throws Exception{
        
        if (validation(btn_id)) {
            String query = "UPDATE user_login SET saldo = ? WHERE id_user = ?";
            PreparedStatement stmt = ConnectDB.connect().prepareStatement(query);
            stmt.setInt(1, tempSaldo);
            stmt.setInt(2, id_user);
            stmt.executeUpdate();

            saldo.setText(Integer.toString(tempSaldo));
        } else {
            System.out.println("Pembelian gagal"); // hanya untuk debug, bisa dihapus
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Pembelian Gagal");
            alert.setHeaderText(null);
            alert.setContentText("saldo tidak cukup");
            alert.showAndWait();
        }        
    }

    private void addToLibrary(int id_game) throws Exception{
        String query = "INSERT INTO library (id_user, id_game) VALUES (?, ?)";
        PreparedStatement stmt = ConnectDB.connect().prepareStatement(query);
        stmt.setInt(1, id_user);
        stmt.setInt(2, id_game);
        stmt.executeUpdate();
        System.out.println("Add library success");
    }
}
