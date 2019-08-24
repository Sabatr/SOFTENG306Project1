package visualisation.controllers.helpers.tile;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.chart.TilesFXSeries;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

import static eu.hansolo.tilesfx.Tile.SkinType;

/**
 * A factory method to build custom tiles
 */
public class CustomTileBuilder {
    private double height;
    private double width;
    private Tile tile;

    /**
     * An enunm to represent which tile to build
     */
    public enum MyTileType  {
            BRANCHES, TIMER, CPU, MEMORY, PROCESS_CHART, INPUT_GRAPH
    }

    /**
     * The builder method which creates a Tile depending on user specification
     * @param tileType
     * @param width
     * @param height
     * @return
     */
    public Tile build(MyTileType tileType, double width, double height) {
        this.width = width;
        this.height = height;

        tile = TileBuilder.create().skinType(SkinType.CUSTOM)
                .prefSize(width, height - 50)
                .build();
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

        tile.setBackgroundColor(Color.rgb(238, 238, 238));
        tile.setInnerShadowEnabled(true);
        return tile;
    }
    private Tile createBranchTile() {
        tile.setBackgroundColor(Color.rgb(25, 34, 38));
        return tile;
    }

    private Tile createProcessTile() {
        tile.setBackgroundColor(Color.rgb(238, 238, 238));
        return tile;
    }

    private Tile createTimerTile() {
        tile.setSkinType(SkinType.CUSTOM);
        tile.setBackgroundColor(Color.rgb(25, 34, 38));
        return tile;
    }

    /**
     * The CPU tile is different as it needs to
     * @return
     */
    private Tile createCPUTile() {
        XYChart.Series<String, Number> cpu = new XYChart.Series();
        XYChart.Series<String, Number> memory = new XYChart.Series();
        Tile tile2 = TileBuilder.create()
                .skinType(SkinType.SMOOTHED_CHART)
                .chartType(Tile.ChartType.AREA)
                .title("CPU USAGE")
                .prefSize(width,height)
                .maxValue(100)
                .minValue(0)
                .chartGridColor(Color.TRANSPARENT)
                //.animated(true)
                .smoothing(true)
                .valueVisible(false)

                .tilesFxSeries(new TilesFXSeries<>(memory,Color.rgb(54,66,137),
                        new LinearGradient(0, 0, 0, 1,
                                true, CycleMethod.NO_CYCLE,
                                new Stop(0, Color.rgb(74,86,157)),
                                new Stop(1, Color.TRANSPARENT))),
                        new TilesFXSeries<>(cpu, Color.rgb(93,18,13),
                        new LinearGradient(0, 0, 0, 1,
                                true, CycleMethod.NO_CYCLE,
                                new Stop(0, Color.rgb(93,18,13,0.5)),
                                new Stop(1, Color.TRANSPARENT)))
                )
                .build();
       tile2.setBackgroundColor(Color.rgb(25, 34, 38));
        return tile2;
    }

}
