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

        FXMLLoader loadLayout = new FXMLLoader(getClass().getResource("view/loginLayout.fxml"));
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
        
    }

    public static void main(String[] args) throws Exception {
        Application.launch(args);
    }
}
