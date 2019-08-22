package visualisation.controllers;

import eu.hansolo.tilesfx.Tile;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import visualisation.controllers.helpers.TreeGenerator;
import visualisation.processor.helpers.CustomProgressBar;
import visualisation.processor.helpers.ProcessChartHelper;
import visualisation.processor.helpers.tile.CustomTileBuilder;

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
        instantiateStatTiles();
        createProcessGraphTile();
        createInputGraphVisual();
    }

    private void createProcessGraphTile() {
        Tile tile = tileBuilder.build(CustomTileBuilder.MyTileType.PROCESS_CHART,
                processPane.getPrefWidth(),processPane.getPrefHeight());

        Platform.runLater(() -> {
            // placeHolder = new Circle(processPane.getLayoutX(),processPane.getLayoutY(),100);
            progressBar = new CustomProgressBar(processPane);
            tile.setGraphic(progressBar);
            //processPane.getChildren().add(progressBar);
        });
        processPane.getChildren().add(tile);
    }

    private void instantiateStatTiles() {

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
        Tile tile = tileBuilder.build(CustomTileBuilder.MyTileType.INPUT_GRAPH,
                graphPane.getPrefWidth(),graphPane.getPrefHeight());
        Pane pane  = new Pane();
        pane.setPrefSize(graphPane.getPrefWidth(),graphPane.getPrefHeight());
        TreeGenerator generator = new TreeGenerator(pane);
        tile.setGraphic(generator.generate());
        graphPane.getChildren().add(tile);
    }

    /**
     * Create the ProcessChart when the algorithm has been complete
     */
    public void createChart() {
        Platform.runLater(()-> {
            Tile tile = (Tile)processPane.getChildren().get(0);

            if (helper == null) {
                helper = new ProcessChartHelper(processPane);
            }
            tile.setGraphic(helper.getProcessChart());
        });
    }

}
