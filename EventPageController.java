package ToDo.src;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;

public class EventPageController implements Initializable {

    @FXML
    private Label titleLabel;

    @FXML
    private Label detailLabel;

    @FXML
    private Button editButton; 

    private Runnable onEditButtonClicked;
      
    public void setOnEditButtonClicked(Runnable handler) {
        this.onEditButtonClicked = handler;
    
    }

    @FXML
    private void handleEditButtonClicked(ActionEvent event) {
        if (onEditButtonClicked != null) {
            onEditButtonClicked.run();
        }
        
        System.out.println("Edit button clicked!");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        titleLabel.setText("ToDo Event List");
        detailLabel.setText("If you wish to enter event, click edit.");
    }


}
