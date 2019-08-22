package visualisation.processor.helpers.tile;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import static eu.hansolo.tilesfx.Tile.SkinType;

public class CustomTileBuilder {
    private final String DEFAULT_BRANCH_TEXT = "0";
    private int height;
    private int width;

    public enum MyTileType  {
            BRANCHES, TIMER, CPU, MEMORY
    }
    public Tile build(MyTileType tileType, int width, int height) {
        this.width = width;
        this.height = height;
        switch(tileType) {
            case BRANCHES:
                return createBranchTile();
            case TIMER:
                return createTimerTile();
            default:
                return null;
        }
    }

    private Tile createBranchTile() {
        return TileBuilder.create().skinType(SkinType.TEXT)
                .text(DEFAULT_BRANCH_TEXT)
                .prefSize(width, height)
                .build();
    }

    private Tile createTimerTile() {
        return TileBuilder.create().skinType(SkinType.TEXT)
                .text(DEFAULT_BRANCH_TEXT)
                .prefSize(width, height)
                .build();
    }
}
