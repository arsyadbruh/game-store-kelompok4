package controller;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import core.ConnectDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class UserLibraryController {
    private Stage dialogStage;

    @FXML
    private Button backButton;
    @FXML
    private Pane paneOne;

    @FXML
    private Pane paneTwo;

    @FXML
    private ImageView thumbOne;

    @FXML
    private ImageView thumbTwo;

    @FXML
    private Button DelButtonOne;

    @FXML
    private Button DelButtonTwo;

private ArrayList<Integer> id_games = new ArrayList<Integer>();

    private int user,id_library;


    public void setUser(int id_user){
        this.user = id_user;
        getLibrary();
        for (Integer i : id_games) {
            setImgView(i);
        }
    }

    void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    @FXML
    private void handleBack(ActionEvent event) throws Exception{
        try {
            System.out.println("back to store"); // hanya untuk debug, bisa dihapus
            dialogStage.close();
            storeMain(user);
        } catch (Exception e) {
            System.out.println("Error handleBack");
            System.out.println("Error cause by : " + e.getCause());
            System.out.println("Error Message : " + e.getMessage());
        }
    }

    private void storeMain(int id_user) throws Exception {
        try {
            FXMLLoader loadLayout = new FXMLLoader(getClass().getResource("../view/storeLayout.fxml"));
            AnchorPane storePage = (AnchorPane) loadLayout.load();
            Scene scene = new Scene(storePage);
            Stage dialogStage = new Stage();
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

    @FXML
    void handleDelete(ActionEvent event) throws Exception {
        System.out.println("Delete button");
        
        if (event.getSource() == DelButtonOne){
            paneOne.setVisible(false);
                
        }

        if (event.getSource() == DelButtonTwo){
            paneTwo.setVisible(false);
        }
    }

    private void setImgView(int indexPath) throws Exception {
        String path = Integer.toString(indexPath);
        try {
            if (indexPath == 1){
                InputStream inputStream = getClass().getResourceAsStream("../img/Library/thumbnail/" + path + ".jpg");
                Image thumbs = new Image(inputStream);
                thumbOne.setImage(thumbs);
            }else{
                InputStream inputStream = getClass().getResourceAsStream("../img/Library/thumbnail/" + path + ".jpg");
                Image thumbs = new Image(inputStream);
                thumbTwo.setImage(thumbs);
            }
            
        } catch (Exception e) {
            System.out.println("Set Image View");
            System.out.println("Error cause by : " + e.getCause());
            System.out.println("Error Message : " + e.getMessage());
        }
    }
    private void getLibrary() throws Exception {
        String query = "SELECT * FROM library WHERE id_user = ?";
        PreparedStatement stmt = ConnectDB.connect().prepareStatement(query);
        stmt.setInt(1, user);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()) {
            System.out.println("ada game");
            int game = rs.getInt("id_game");
            this.id_games.add(game);
        }

    }

}