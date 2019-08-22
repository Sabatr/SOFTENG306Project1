package visualisation.controllers;

import algorithm.AlgorithmBranchDetails;
import eu.hansolo.tilesfx.Tile;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import visualisation.controllers.helpers.TreeGenerator;
import visualisation.controllers.helpers.tile.CustomPieChart;
import visualisation.controllers.helpers.tile.CustomTileBuilder;
import visualisation.processor.helpers.CustomProgressBar;
import visualisation.processor.helpers.ProcessChartHelper;

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

    private CustomPieChart pieChart;

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
            progressBar = new CustomProgressBar(processPane);
            tile.setGraphic(progressBar);
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
        text.setFont(new Font(30));
        text.setFill(Color.WHITE);
        tilesBox.getChildren().add(text);
    }

    private void createTimeTile() {
        Tile tile = tileBuilder.build(CustomTileBuilder.MyTileType.TIMER,tileWidth,tileHeight);
        tilesBox.getChildren().add(tile);
    }

    private void createBranchTile() {
        Pane pane = new Pane();
        pane.setPrefHeight(tileHeight);
        pane.setPrefWidth(tileWidth);
        pane.setLayoutY(0);
        pane.setLayoutX(0);
        Tile tile = tileBuilder.build(CustomTileBuilder.MyTileType.BRANCHES,tileWidth,tileHeight);

        pieChart = new CustomPieChart(tileWidth,tileHeight);
        PieChart.Data slice1 = new PieChart.Data("Seen", 1);
        PieChart.Data slice2 = new PieChart.Data("Pruned"  , 10);
        PieChart.Data slice3 = new PieChart.Data("Duplicate" , 10);
        pieChart.getData().add(slice1);
        pieChart.getData().add(slice2);
        pieChart.getData().add(slice3);
        pieChart.setCache(true);
        pieChart.setCacheShape(true);
        pieChart.setCacheHint(CacheHint.SPEED);
        pane.getChildren().add(pieChart);


      //  System.out.println(pieChart.getHeight());

        Text text = new Text("Total Branches: 0");
        text.setY(200);
        text.setFill(Color.WHITE);

        pane.getChildren().add(text);

        //group.getChildren().addAll(pieChart,text);


        tile.setGraphic(pane);
        tilesBox.getChildren().add(tile);
    }

    private void createCPUTile() {
        Tile tile = tileBuilder.build(CustomTileBuilder.MyTileType.CPU,tileWidth,tileHeight);
        tilesBox.getChildren().add(tile);

    }

    /**
     * Updates the branch counter
     * @param label
     */
    public void updateBranchCount(String label, AlgorithmBranchDetails algorithmBranchDetails) {

        Platform.runLater(() -> {
            Tile tile = ((Tile)tilesBox.getChildren().get(1));
            Pane nodePane = (Pane)tile.getGraphic();
            PieChart chart = (PieChart)nodePane.getChildren().get(0);
            PieChart.Data slice1 = chart.getData().get(0);
            slice1.setPieValue(algorithmBranchDetails.getBranchesSeen());
            ((Text)nodePane.getChildren().get(1)).setText(label);
        }
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
        pane.setLayoutX(graphPane.getLayoutX());
        pane.setLayoutY(graphPane.getLayoutY());
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
