package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import core.ConnectDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GameStoreController {
    private int id_user = 0;
    
    @FXML
    private Button libraryButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Label user;

    // mendapatkan username yang lagin berdasarkan id
    public void userinfo() throws Exception{
        String query = "select * from user_login where id_user = ?";
        PreparedStatement stmt = ConnectDB.connect().prepareStatement(query);
        stmt.setInt(1,id_user);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            user.setText(rs.getString("username"));
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
        System.out.println("logout button");
    }
}
