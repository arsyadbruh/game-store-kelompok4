import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GameStoreController {
    @FXML
    private void handleSign() throws Exception{
        FXMLLoader loadLayout = new FXMLLoader(getClass().getResource("layout/Store.fxml"));
        AnchorPane page = (AnchorPane) loadLayout.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Game Store");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.show();
    }
}
