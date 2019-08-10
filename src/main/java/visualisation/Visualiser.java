package visualisation;

import files.DotParser;
import graph.Graph;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import visualisation.controllers.GUIController;

import java.io.File;

public class Visualiser extends Application{

    private GUIController controller;
    private static final int SCENE_HEIGHT = 800;
    private static final int SCENE_WIDTH = 1200;
    private int coreNum;
    /**
     * This method is called to start the visualisation
     * @param args
     */
    public void startVisual(String[] args) {
        launch(args);
    }

    // Just to test if the application opens without running all of the code
    // TODO: Delete later and call startVisual instead when the arg is inputted
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Creates the GUI application
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(Visualiser.class.getResource("views/GUI.fxml"));
        Parent root=loader.load();
        Scene scene = new Scene(root);

        controller = loader.getController();

        stage.setHeight(SCENE_HEIGHT);
        stage.setWidth(SCENE_WIDTH);
        stage.setResizable(false);
        stage.setTitle("INSERT TITLE HERE");
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Stops application
     */
    @Override
    public void stop() {
        System.exit(1);
    }
}
