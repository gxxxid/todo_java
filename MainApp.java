package ToDo.src;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        FXMLLoader eventLoader = new FXMLLoader(getClass().getResource("eventPage.fxml"));
        Parent eventRoot = eventLoader.load();
        EventPageController eventController = eventLoader.getController();

        
        eventController.setOnEditButtonClicked(() -> {
            try {
                FXMLLoader editLoader = new FXMLLoader(getClass().getResource("editPage.fxml"));
                Parent editRoot = editLoader.load();
                EditPageController editController = editLoader.getController();

                // Create a new scene for the Edit Page
                Scene editScene = new Scene(editRoot);

                // Set the scene on the primary stage
                primaryStage.setScene(editScene);
                primaryStage.setTitle("Edit Page");
                primaryStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Scene eventScene = new Scene(eventRoot);
        primaryStage.setScene(eventScene);
        primaryStage.setTitle("Event Page");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

