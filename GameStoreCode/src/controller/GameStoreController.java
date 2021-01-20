package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import core.ConnectDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Node;

public class GameStoreController {
    /**
     * TODO Buat tabel game pada database
     * kolom : id_game, judul, price
     * read tabel game
     */
    private int id_user = 0;
    private int userSaldo= 0;
    private int price_wdTwo = 500; // harga sementera
    private int price_wdLegion = 10000; //harga sementara
    private int updateSaldo = 0;
    
    @FXML
    private Button libraryButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Label user;

    @FXML
    private Label saldo;

    @FXML
    private Button wdTwo;

    @FXML
    private Button wdLegion;

    // mendapatkan username yang lagin berdasarkan id
    public void userinfo() throws Exception{
        String query = "select * from user_login where id_user = ?";
        PreparedStatement stmt = ConnectDB.connect().prepareStatement(query);
        stmt.setInt(1,id_user);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            userSaldo = rs.getInt("saldo");
            String wallet = Integer.toString(userSaldo);
            user.setText(rs.getString("username"));
            saldo.setText(wallet);
        }
        stmt.close();
    }

    public void setData(int id_user) throws Exception {
        this.id_user = id_user;
        userinfo();
    }

    @FXML
    void handleGame(ActionEvent event) throws Exception {
        /**
         * TODO update database tabel user_login
         * saldo user update berdasarkan price game
         * konfirmasi pembelian
         * verifikasi pembelian
         * 
         * QUERY for SQL
         * UPDATE `user_login` SET `saldo` = 'updateSaldo' WHERE `user_login`.`id_user` = id_user;
         */
        System.out.println("game button");
        if (event.getSource() == wdTwo) {
            setSaldo(price_wdTwo);
            saldo.setText(Integer.toString(updateSaldo));
        }
        
        if (event.getSource() == wdLegion){
            setSaldo(price_wdLegion);
            saldo.setText(Integer.toString(updateSaldo));
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
            dialogStage.show();
        } catch (Exception e) {
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
            System.out.println("Error cause by : "+ e.getCause());
            System.out.println("Error Message : "+e.getMessage());
        }
        
    }

    // method untuk update saldo
    // ini cuma sementara untuk demo
    private void setSaldo(int price){
        this.updateSaldo = userSaldo - price;
        this.userSaldo = updateSaldo;
    }
}
