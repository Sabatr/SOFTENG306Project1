package visualisation.controllers.helpers;

import javafx.scene.shape.Line;

public class VisualEdge extends Line {
    private int startingX;
    private int startingY;
    private int endingX;
    private int endingY;
    public VisualEdge(int startingX, int startingY, int endingX, int endingY) {
        super(startingX,startingY,endingX,endingY);
        this.startingX = startingX;
        this.startingY = startingY;
        this.endingX = endingX;
        this.endingY = endingY;
    }
}
