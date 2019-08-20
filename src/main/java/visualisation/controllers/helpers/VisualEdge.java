package visualisation.controllers.helpers;

import graph.Vertex;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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

    public Text getEdgeText(Vertex fromVertex, Vertex toVertex, int size) {
        Text text = new Text(""+toVertex.getEdgeWeightFrom(fromVertex));
        text.setFont(new Font(size/2));
        int x = (this.startingX + this.endingX) / 2;
        int y = (this.startingY + this.endingY) / 2;
        text.setX(x);
        text.setY(y);
        return text;
    }
}
