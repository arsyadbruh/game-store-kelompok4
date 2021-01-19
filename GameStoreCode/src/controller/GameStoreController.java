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
    private int id_user = 0;
    
    @FXML
    private Button libraryButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Label user;

    @FXML
    private Label saldo;

    // mendapatkan username yang lagin berdasarkan id
    public void userinfo() throws Exception{
        String query = "select * from user_login where id_user = ?";
        PreparedStatement stmt = ConnectDB.connect().prepareStatement(query);
        stmt.setInt(1,id_user);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            int userSaldo = rs.getInt("saldo");
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
        System.out.println("game button");
    }

    @FXML
    void handleLibrary(ActionEvent event) throws Exception {
        System.out.println("library button");
    }

    @FXML
    void handleLogout(ActionEvent event) throws Exception {
        System.out.println("user Logout");
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

            // login controller
            LoginController control = loadLayout.getController();
            control.setDialogStage(dialogStage);
            dialogStage.showAndWait();
        } catch (Exception e) {
            System.out.println("Error cause by : "+ e.getCause());
            System.out.println("Error Message : "+e.getMessage());
        }
        
    }
}
