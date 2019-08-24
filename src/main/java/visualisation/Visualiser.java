package visualisation;

import algorithm.Algorithm;
import application.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.CliParser;
import visualisation.controllers.GUIController;
import visualisation.processor.helpers.GUIUpdater;

/**
 * This class generates the GUI to visualise our algorithm.
 */
public class Visualiser extends Application {
    private GUIController controller;
    private final int SCENE_HEIGHT = 850;
    private final int SCENE_WIDTH = 1200;
    private final String VISUALISATION_TITLE = "Visualisation";
    private final String SCENE_PATH = "views/GUI.fxml";
    public Visualiser() {
        super();
    }
    /**
     * This method is called to start the visualisation
     * @param args
     */
    public void startVisual(String[] args) {
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
        loader.setLocation(Visualiser.class.getResource(SCENE_PATH));
        Parent root=loader.load();
        Scene scene = new Scene(root);
        controller = loader.getController();
        GUIUpdater.getInstance().setController(controller);
        stage.setHeight(SCENE_HEIGHT);
        stage.setWidth(SCENE_WIDTH);
        stage.setResizable(false);
        stage.setTitle(VISUALISATION_TITLE);
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.show();
        CliParser.createSolution();
    }

    /**
     * Stops application
     */
    @Override
    public void stop() {
        System.exit(1);
    }

}
