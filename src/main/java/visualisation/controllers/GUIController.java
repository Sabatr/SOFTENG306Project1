package visualisation.controllers;

import eu.hansolo.tilesfx.Tile;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Circle;
import visualisation.controllers.helpers.TreeGenerator;
import visualisation.processor.helpers.CustomProgressBar;
import visualisation.processor.helpers.ProcessChartHelper;

public class GUIController {
    @FXML
    private Pane graphPane;
    @FXML
    private Pane processPane;
    @FXML
    private Label timeElapsed;
    @FXML
    private Label branchesVisited;
    @FXML
    private TilePane timeTile;
    @FXML
    private TilePane branchTile;
    @FXML
    private TilePane CPUTile;

    private CustomProgressBar progressBar;
    private ProcessChartHelper helper;

    /**
     * Dummy method to check for GUI responsiveness
     */
    @FXML
    private void onClick() {
        System.out.println("clicked");
    }

    /**
     * When the application starts, run this.
     */
    @FXML
    private void initialize() {
       createInputGraphVisual();
       createGraphLoader();
       setTimeLabel();
       setBranchesLabel();
       createTimeTile();
       createBranchTile();
        createCPUTile();
    }

    private void createTimeTile() {
        createTile(timeTile);
    }

    private void createBranchTile() {
        createTile(branchTile);
    }

    private void createCPUTile() {
        createTile(CPUTile);
    }

    private void createTile(Pane pane) {
        Tile tile = new Tile();
        tile.setPrefHeight(pane.getPrefHeight());
        tile.setPrefWidth(pane.getPrefWidth());
        tile.setLayoutX(pane.getLayoutX());
        tile.setLayoutY(pane.getLayoutY());
        pane.getChildren().add(tile);
    }
    /**
     * Updates the branch counter
     * @param label
     */
    public void updateBranchCount(String label) {
        Platform.runLater(() -> branchesVisited.setText(label));
    }

    /**
     * Updates the time elapsed
     * @param time
     */
    public void updateTimer(String time){
        Platform.runLater(() -> timeElapsed.setText(time));
    }

    /**
     * The initial time label
     */
    private void setTimeLabel() {
        timeElapsed.setText("0ms");
    }

    /**
     * The initial label for the branches
     */
    private void setBranchesLabel() {
        branchesVisited.setText("0");
    }
    /**
     * This method allows for the creation of the input graph visualisation.
     * It uses the InputGraphHelper class to add vertices/edges and also add styling.
     * This method puts the graphic created onto a pane.
     */
    private void createInputGraphVisual() {
        TreeGenerator generator = new TreeGenerator(graphPane);
        generator.generate();
    }

    /**
     * This method creates a loader while the solution is running
     */
    public void createGraphLoader() {
        // Loading should go here
        Platform.runLater(() -> {
           // placeHolder = new Circle(processPane.getLayoutX(),processPane.getLayoutY(),100);
            progressBar = new CustomProgressBar(processPane);
            processPane.getChildren().add(progressBar);
        });

    }

    /**
     * Create the ProcessChart when the algorithm has been complete
     */
    public void createChart() {
        Platform.runLater(()-> {
            processPane.getChildren().remove(progressBar);

            if (helper == null) {
                helper = new ProcessChartHelper(processPane);
            }
            processPane.getChildren().add(helper.getProcessChart());
        });
    }

}
