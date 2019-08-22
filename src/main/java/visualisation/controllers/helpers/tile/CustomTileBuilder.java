package visualisation.controllers.helpers.tile;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

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
//        tile.setSkinType(SkinType.TEXT);
//        tile.setText(DEFAULT_BRANCH_TEXT);
        tile.setBackgroundColor(Color.rgb(25, 34, 38));
        return tile;
    }

    private Tile createProcessTile() {
        tile.setBackgroundColor(Color.WHITE);
        return tile;
    }

    private Tile createTimerTile() {
        tile.setSkinType(SkinType.TEXT);
        tile.setText(DEFAULT_BRANCH_TEXT);
        tile.setBackgroundColor(Color.rgb(25, 34, 38));
        return tile;
    }

    private Tile createCPUTile() {
        tile.setBackgroundColor(Color.rgb(25, 34, 38));
        return tile;
    }

}
