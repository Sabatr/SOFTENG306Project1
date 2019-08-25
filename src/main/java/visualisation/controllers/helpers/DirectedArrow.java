package visualisation.controllers.helpers;

import javafx.scene.Group;
import javafx.scene.shape.Polygon;

/**
 * A class for making arrow heads on the end of the directed edges
 * Takes an edge and an arrow head size as input
 */
public class DirectedArrow extends Group{
    private VisualEdge edge;
    private int size;

    public DirectedArrow(VisualEdge edge, int size) {
        this.size = 7*size/12;
        this.edge = edge;
        Polygon triangle = createArrowHead();
        this.getChildren().add(triangle);
        this.getChildren().add(edge);
    }

    private Polygon createArrowHead() {
        Polygon polygon = new Polygon();
        double headPointX = edge.getEndingX();
        double headPointY = edge.getEndingY();

        double leftPointX;
        double leftPointY;

        double rightPointX;
        double rightPointY;

        double xWidth = Math.abs(edge.getStartingX() - edge.getEndingX());
        double yWidth = Math.abs(edge.getStartingY() - edge.getEndingY());

        double arrowScale = 8;

        double angle;
        if (xWidth != 0) { angle = Math.atan(yWidth/xWidth); }
        else { angle = Math.PI/2; }

        if (edge.getStartingX() < edge.getEndingX()) { // For edges pointing to the right
            leftPointX = edge.getEndingX() - (size * Math.cos(angle + Math.PI/arrowScale));
            leftPointY = edge.getEndingY() - (size * Math.sin(Math.PI - angle - Math.PI/arrowScale));
            rightPointX = edge.getEndingX() - (size * Math.cos(angle - Math.PI/arrowScale));
            rightPointY = edge.getEndingY() - (size * Math.sin(Math.PI - angle + Math.PI/arrowScale));
        } else { // For edges pointing to the left
            leftPointX = edge.getEndingX() + (size * Math.cos(angle + Math.PI/arrowScale));
            leftPointY = edge.getEndingY() - (size * Math.sin(Math.PI - angle - Math.PI/arrowScale));
            rightPointX = edge.getEndingX() + (size * Math.cos(angle - Math.PI/arrowScale));
            rightPointY = edge.getEndingY() - (size * Math.sin(Math.PI - angle + Math.PI/arrowScale));
        }

        polygon.getPoints().addAll(
                        headPointX,headPointY,
                        leftPointX,leftPointY,
                        rightPointX,rightPointY
        );

        return polygon;
    }
}
