package visualisation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Visualiser extends Application{

    /**
     * This method is called to start the visualisation
     * @param args
     */
    public void startVisual(String[] args) {
        launch(args);
    }

    // Just to test if the application opens without running all of the code
    // TODO: Delete later and call startVisual instead
    public void main(String[] args) {
        launch(args);
    }

    /**
     * Creates the GUI application
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    /**
     * Stops application
     */
    @Override
    public void stop() {
        System.exit(1);
    }
}
