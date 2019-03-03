package phoneBook;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Adam
 */
public class PhoneBook extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("View.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setTitle("Phone book");
        stage.setWidth(620);
        stage.setHeight(800);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
