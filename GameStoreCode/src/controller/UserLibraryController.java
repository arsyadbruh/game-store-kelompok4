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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class UserLibraryController {

    @FXML private Button backButton;
    @FXML private Pane paneOne, paneTwo;
    @FXML private ImageView thumbOne, thumbTwo;
    @FXML private Button DelButtonOne, DelButtonTwo;
    
    private Stage dialogStage;
    private ArrayList<Integer> id_games = new ArrayList<Integer>();
    private int user,id_library;

    /**
     * get user id from store and call method {@code getLibrary} and {@code visibility}
     * to show the user library from database
     * 
     * @param id_user
     * @throws Exception
     */
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
    private void handleBack(ActionEvent event) throws Exception{
        try {
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
        
        if (event.getSource() == DelButtonOne){
            lookupID(1);
            delLibrary();
            paneOne.setVisible(false);
        }

        if (event.getSource() == DelButtonTwo){
            lookupID(2);
            delLibrary();
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
            int game = rs.getInt("id_game");
            this.id_games.add(game);
        }

    }

    private void visibility() throws Exception {
        try {
            if(id_games.isEmpty()){
                paneOne.setVisible(false);
                paneTwo.setVisible(false);
                return;
            }else{
                // set the paneOne and panTwo to hidden before check the condition
                paneOne.setVisible(false);
                paneTwo.setVisible(false);
                
                if(id_games.contains(1)){
                    paneOne.setVisible(true);
                }
                
                if(id_games.contains(2)){
                    paneTwo.setVisible(true);
                }
            }
        } catch (Exception e) {
            System.out.println("Cek Visibility");
            System.out.println("Error cause by : " + e.getCause());
            System.out.println("Error Message : " + e.getMessage());
        }
    }

    // get the id of library user
    private void lookupID(int game) throws Exception{
        String query = "SELECT * FROM library WHERE id_user = ? AND id_game = ?";
        PreparedStatement stmt = ConnectDB.connect().prepareStatement(query);
        stmt.setInt(1, user);
        stmt.setInt(2, game);
        stmt.execute();
        if(stmt.getResultSet().next()){
            id_library = stmt.getResultSet().getInt("id_library");
            stmt.close();
        }
    }

    // del the library where id user
    private void delLibrary() throws Exception{
        try {
            String query = "DELETE FROM library WHERE library.id_library = ?";
            PreparedStatement stmt = ConnectDB.connect().prepareStatement(query);
            stmt.setInt(1, id_library);
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Delete Library");
            System.out.println("Error cause by : " + e.getCause());
            System.out.println("Error Message : " + e.getMessage());
        }
        
    }

}