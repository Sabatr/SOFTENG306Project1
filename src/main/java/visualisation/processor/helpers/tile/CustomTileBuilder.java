package visualisation.processor.helpers.tile;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
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
        tile = TileBuilder.create().skinType(SkinType.CUSTOM)
                .prefSize(width, height)
                .build();
        return tile;
    }
    private Tile createBranchTile() {
        Tile tile = TileBuilder.create().skinType(SkinType.TEXT)
                .text(DEFAULT_BRANCH_TEXT)
                .prefSize(width, height)
                .build();
        tile.setBackgroundColor(Color.RED);
        return tile;
    }

    private Tile createProcessTile() {
        tile = TileBuilder.create().skinType(SkinType.CUSTOM)
                .prefSize(width, height)
                .build();
        return tile;
    }

    private Tile createTimerTile() {
        return TileBuilder.create().skinType(SkinType.TEXT)
                .text(DEFAULT_BRANCH_TEXT)
                .prefSize(width, height)
                .build();
    }

    private Tile createCPUTile() {
        Tile tile = TileBuilder.create().skinType(SkinType.TEXT)
                .text(DEFAULT_BRANCH_TEXT)
                .prefSize(width, height)
                .build();
        return tile;
    }

}
