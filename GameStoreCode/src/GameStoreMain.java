/**
 * GAME STORE
 * Dibuat untuk memenuhi tugas final project mata kuliah PBO
 * Dibuat dengan java 15 dan javaFX 8
 * 
 * TODO Kalau bisa tambah comment
 * jika ingin menambah method baru atau yang lain 
 * kalau bisa berikan komentar penjelas apa itu 
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import controller.LoginController;


public class GameStoreMain extends Application{
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        /**
         * Try {mencoba baris kode didalam bracket ini} 
         * catch {jika ada eror menjalankan baris kode yang ada di bracket cacth}
         */
        try {
            /**
             * loadlayout funginya untuk me-load loginLayout dari view
             * anchorpane disini digunakan karena layout kita pakai anchor pane
             * stage adalah windows yang nantinya akan memuat semua objek kita
             * setTittle() adalah judul yang ada diatas window kita
             * initModality() menentukan apakah stage akan memblokir window lainya yang dibuka sama javafx
             *      - intiModality() secara deafult bernilai NONE 
             *      - dan yang lain adalah APPLICATION_MODAL (memblokir semua stage lain)
             * scene() memuat semua objek layout
             * .show() untuk menampilan stage kita
             **/
            FXMLLoader loadLayout = new FXMLLoader(getClass().getResource("view/loginLayout.fxml"));
            AnchorPane loginPage = (AnchorPane) loadLayout.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("GameStore | Login");
            dialogStage.initModality(Modality.WINDOW_MODAL); //WINDOW_MODAL berarti hanya memblokir stage owner tidak semuanya
            dialogStage.setResizable(false);
            Scene scene = new Scene (loginPage);
            dialogStage.setScene(scene);

            // login controller
            LoginController control = loadLayout.getController();
            control.setDialogStage(dialogStage);
            dialogStage.show();
        } catch (Exception e) {
            System.out.println("Error Main > start");
            System.out.println("Error cause by : "+ e.getCause());
            System.out.println("Error Message : "+e.getMessage());
        }
        
    }

    public static void main(String[] args) throws Exception {
        Application.launch(args); // run app javafx kita
    }
}
