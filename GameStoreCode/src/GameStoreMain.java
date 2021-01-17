import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class GameStoreMain extends Application{
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        /**
         * Semua Layout di taruh pada direktori /layout
         * Login lyout dibuat menjadi layout yang pertama muncul
         */

        FXMLLoader loadLayout = new FXMLLoader(getClass().getResource("layout/loginLayout.fxml"));
        AnchorPane loginLayout = (AnchorPane) loadLayout.load();
        Scene scene = new Scene (loginLayout);

        primaryStage.setTitle("GameStore | Login");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }

    public static void main(String[] args) throws Exception {
        Application.launch(args);
    }
}
