import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;



public class GameStoreController {
    @FXML
    private Button loginButton;

    @FXML
    private TextField textUsername;

    @FXML
    private PasswordField textPassword;

    @FXML
    private void handleSign(ActionEvent event) throws Exception{
        FXMLLoader loadLayout = new FXMLLoader(getClass().getResource("layout/Store.fxml"));
        AnchorPane page = (AnchorPane) loadLayout.load();

        if(event.getSource() == loginButton){
            System.out.println("Login");
            String usern = textUsername.getText();
            String passw = textPassword.getText();
            System.out.println(textUsername.getText());
            System.out.println(textPassword.getText());
            if(usern.equalsIgnoreCase("admin") && passw.equalsIgnoreCase("1234")){
                
                System.out.println("Login Sukses");
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Game Store");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);
                dialogStage.show();
            
            }else{
                System.out.println("login gagal");
            }
        }        
    }
}
