package controller;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import core.ConnectDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.ArrayList;


public class UserLibraryController{
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

    private int user;
    private ArrayList<Integer> id_librarys = new ArrayList<Integer>();
    private ArrayList<Integer> id_games = new ArrayList<Integer>();

    public void setUser(int id_user) throws Exception {
        this.user = id_user;
        getLibrary();
        visibility();
        for (Integer i : id_games) {
            setImgView(i);
        }
    }

    void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void handleBack(ActionEvent event) throws Exception {
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

    @FXML
    void handleDelete(ActionEvent event) {
        System.out.println("Delete button");
        for (int i : id_librarys){
            System.out.println(i);
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
        } catch (Exception e) {
            System.out.println("storeMain");
            System.out.println("Error cause by : " + e.getCause());
            System.out.println("Error Message : " + e.getMessage());
        }
    }

    private void setImgView(int indexPath) throws Exception {
        String path = Integer.toString(indexPath);
        try {
            InputStream inputStream = getClass().getResourceAsStream("../img/Library/thumbnail/" + path + ".jpg");
            Image thumbs = new Image(inputStream);
            thumbOne.setImage(thumbs);
        } catch (Exception e) {
            System.out.println("Set Image View");
            System.out.println("Error cause by : " + e.getCause());
            System.out.println("Error Message : " + e.getMessage());
        }
    }

    private void visibility() throws Exception {
        try {
            if(id_games.isEmpty()){
                System.out.println("Empty");
                paneOne.setVisible(false);
                paneTwo.setVisible(false);
                return;
            }else{
                System.out.println("masuk else");
                System.out.println(id_games.get(0));
                System.out.println(id_games.contains(1));
                if(id_games.contains(1)){
                    System.out.println("one visible");
                    paneOne.setVisible(true);
                }

                if(id_games.contains(2)){
                    System.out.println("two visible");
                    paneTwo.setVisible(true);
                }
            }
        } catch (Exception e) {
            System.out.println("Cek Visibility");
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
            int lib = rs.getInt("id_library");
            int game = rs.getInt("id_game");
            this.id_librarys.add(lib);
            this.id_games.add(game);
        }

    }

}
