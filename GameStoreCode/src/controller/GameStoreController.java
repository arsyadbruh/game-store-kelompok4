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

    @FXML private Button libraryButton, logoutButton, wdTwo, wdLegion;
    @FXML private Label user, saldo;

    private int id_user = 0;
    private int userSaldo = 0;
    private int tempSaldo = 0;

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
            stmt.close();
        }
        stmt.close();
    }

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

    /**
     * method {@code setData} is to set the user id
     * and call method {@code userinfo} and {@code setBuybtn}
     * 
     * @param id_user
     * @throws Exception
     */
    public void setData(int id_user) throws Exception {
        this.id_user = id_user;
        userinfo();
        setBuybtn();
    }

    @FXML
    void handleGame(ActionEvent event) throws Exception {        
        /**
         * event.getSource() get source event base on fxid on fxml layout
         */

        if (event.getSource() == wdTwo) {
            updateSaldo(1);
            addToLibrary(1);
            wdTwo.setDisable(true);
            wdTwo.setText("Purchased");
        }

        if (event.getSource() == wdLegion) {
            updateSaldo(2);
            addToLibrary(2);
            wdLegion.setDisable(true);
            wdLegion.setText("Purchased");
        }
        
    }

    @FXML
    private void handleLibrary(ActionEvent event) throws Exception {
        try {
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
    private void handleLogout(ActionEvent event) throws Exception {
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
            
            LoginController control = loadLayout.getController();
            control.setDialogStage(dialogStage);
            dialogStage.showAndWait();
        } catch (Exception e) {
            System.out.println("Error handleLogout");
            System.out.println("Error cause by : "+ e.getCause());
            System.out.println("Error Message : "+e.getMessage());
        }
        
    }

    /**
     * the method {@code validation} is to valdiation payment user.
     * 
     * @param btn_id
     * @return true if payment success | false if doesn't
     * @throws Exception
     */
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

    private void updateSaldo(int btn_id) throws Exception{
        
        if (validation(btn_id)) {
            String query = "UPDATE user_login SET saldo = ? WHERE id_user = ?";
            PreparedStatement stmt = ConnectDB.connect().prepareStatement(query);
            stmt.setInt(1, tempSaldo);
            stmt.setInt(2, id_user);
            stmt.executeUpdate();
            stmt.close();
            saldo.setText(Integer.toString(tempSaldo));
        } else {
            System.out.println("Pembelian gagal");
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
        stmt.close();
        System.out.println("Add library success");
    }
    
    /**
     * method {@code isPurchased} is to check whether the user has the game or not on database.
     * 
     * @return the return {@code isPurchased} is true if user has a game and false if doesn't
     * @throws Exception
     */
    private boolean isPurchased(int id_game) throws Exception{
	    String query = "SELECT * FROM library WHERE id_user = ? AND id_game = ?";
        PreparedStatement stmt = ConnectDB.connect().prepareStatement(query);
        stmt.setInt(1, id_user);
        stmt.setInt(2, id_game);
        stmt.execute();
        if(stmt.getResultSet().next()){
            stmt.close();
            return true;
        }
        return false;
    }

    /**
     * method {@code setBuybtn} is to disable button buy on the store.
     * 
     * <p>the method {@code setBuybtn} check if user have a game on database. 
     * this method will call a method {@code isPurchased} to check if true or not
     * @throws Exception
     */
    public void setBuybtn() throws Exception{        
        try {

            if (isPurchased(1)){
                wdTwo.setDisable(true);
                wdTwo.setText("Purchased");
            }

            if (isPurchased(2)){
                wdLegion.setDisable(true);
                wdLegion.setText("Purchased");
            }

        } catch (Exception e) {
            System.out.println("Error cause by : "+ e.getCause());
            System.out.println("Error Message : "+e.getMessage());
        }
    }

} // end class