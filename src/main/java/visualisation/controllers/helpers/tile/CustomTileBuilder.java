package visualisation.controllers.helpers.tile;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.chart.TilesFXSeries;
import javafx.geometry.Insets;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

import static eu.hansolo.tilesfx.Tile.SkinType;

public class CustomTileBuilder {
    private final String DEFAULT_BRANCH_TEXT = "0";
    private double height;
    private double width;
    private Tile tile;


    public enum MyTileType  {
            BRANCHES, TIMER, CPU, MEMORY, PROCESS_CHART, INPUT_GRAPH
    }
    public Tile build(MyTileType tileType, double width, double height) {
        this.width = width;
        this.height = height;

        tile = TileBuilder.create().skinType(SkinType.CUSTOM)
                .prefSize(width, height - 50)
                .build();
        //tile.getStylesheets().add(CustomTileBuilder.class.getResource("../../../visualisationassets/Tiles.css").toString());

        switch(tileType) {
            case BRANCHES:
                return createBranchTile();
            case TIMER:
                return createTimerTile();
            case CPU:
                return createCPUTile();
            case PROCESS_CHART:
                return createProcessTile();
            case INPUT_GRAPH:
                return createInputGraphTile();
            default:
                return null;
        }
    }
    private Tile createInputGraphTile() {
        tile.setBackgroundColor(Color.WHITE);
        tile.setInnerShadowEnabled(true);
        return tile;
    }
    private Tile createBranchTile() {
        tile.setBackgroundColor(Color.rgb(25, 34, 38));
        return tile;
    }

    private Tile createProcessTile() {
        tile.setBackgroundColor(Color.WHITE);
        return tile;
    }

    private Tile createTimerTile() {
        tile.setSkinType(SkinType.CUSTOM);
        tile.setBackgroundColor(Color.rgb(25, 34, 38));
        return tile;
    }

    private Tile createCPUTile() {
        XYChart.Series<String, Number> series1 = new XYChart.Series();
        Tile tile2 = TileBuilder.create()
                .skinType(SkinType.SMOOTHED_CHART)
                .chartType(Tile.ChartType.AREA)
                .title("CPU USAGE")
                .prefSize(width,height)
                .maxValue(100)
                .minValue(0)
                .unit("Unit")
                .tooltipText("DBFF")
                .chartGridColor(Color.TRANSPARENT)
                //.animated(true)
                .smoothing(true)
                .tilesFxSeries(new TilesFXSeries<>(series1,
                        Tile.RED,
                        new LinearGradient(0, 0, 0, 1,
                                true, CycleMethod.NO_CYCLE,
                                new Stop(0, Tile.RED),
                                new Stop(1, Color.TRANSPARENT))))
                .build();
        tile2.setBackgroundColor(Color.rgb(25, 34, 38));
        return tile2;
    }

}
