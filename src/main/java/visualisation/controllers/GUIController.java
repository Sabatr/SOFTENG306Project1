package visualisation.controllers;

import eu.hansolo.tilesfx.Tile;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import visualisation.controllers.helpers.TreeGenerator;
import visualisation.processor.helpers.CustomProgressBar;
import visualisation.processor.helpers.ProcessChartHelper;
import visualisation.processor.helpers.tile.CustomTileBuilder;

import java.util.ArrayList;
import java.util.List;

public class GUIController {
    @FXML
    private Pane graphPane;
    @FXML
    private Pane processPane;
    @FXML
    private Pane tilesPane;
    @FXML
    private VBox tilesBox;

    private CustomTileBuilder tileBuilder;
    private CustomProgressBar progressBar;
    private ProcessChartHelper helper;

    private double tileHeight;
    private double tileWidth;

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
        tileBuilder = new CustomTileBuilder();
        instantiateTiles();
        createInputGraphVisual();
        createGraphLoader();

    }

    private void instantiateTiles() {

        tileHeight = tilesBox.getPrefHeight()/3;
        tileWidth = tilesBox.getPrefWidth();
        createTileHeader();
        // Position 1 in the VBox
        createBranchTile();

        // Position 2 in the VBox
        createTimeTile();

        // Position 3 in the VBox
        createCPUTile();
    }

    private void createTileHeader() {
        Text text = new Text("Statistics");
        tilesBox.getChildren().add(text);
    }

    private void createTimeTile() {
        Tile tile = tileBuilder.build(CustomTileBuilder.MyTileType.TIMER,tileWidth,tileHeight);
        tilesBox.getChildren().add(tile);
    }

    private void createBranchTile() {
      //  createTile(branchTile);
        Tile tile = tileBuilder.build(CustomTileBuilder.MyTileType.BRANCHES,tileWidth,tileHeight);
        tilesBox.getChildren().add(tile);
     //   branchTile.getChildren().add(tile);

    }

    private void createCPUTile() {
        Tile tile = tileBuilder.build(CustomTileBuilder.MyTileType.CPU,tileWidth,tileHeight);
        tilesBox.getChildren().add(tile);
    }

    /**
     * Updates the branch counter
     * @param label
     */
    public void updateBranchCount(String label) {
        Platform.runLater(() ->
                ((Tile)tilesBox.getChildren().get(1)).setText(label)
        );
    }

    /**
     * Updates the time elapsed
     * @param time
     */
    public void updateTimer(String time){
        Platform.runLater(() ->((Tile)tilesBox.getChildren().get(2)).setText(time));
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
