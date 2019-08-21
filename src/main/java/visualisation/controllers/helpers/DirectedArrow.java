package visualisation.controllers.helpers;

import javafx.scene.Group;
import javafx.scene.shape.Polygon;

public class DirectedArrow extends Group{
    private VisualEdge edge;
    private int size;

    public DirectedArrow(VisualEdge edge, int size) {
        this.size = size;
        this.edge = edge;
        Polygon triangle = createArrowHead();
        System.out.println(triangle);
        this.getChildren().add(triangle);
        this.getChildren().add(edge);
    }

    private Polygon createArrowHead() {
        //System.out.println("powe");
        Polygon polygon = new Polygon();
        double headPointX = edge.getEndingX();
        double headPointY = edge.getEndingY();

        System.out.println(edge);
        double leftPointX;
        double leftPointY;

        double rightPointX;
        double rightPointY;

        if (edge.getStartingX() > edge.getEndingX()) {
            leftPointX = edge.getEndingX() + (size/4);
            leftPointY = edge.getEndingY() - (size/2);
            rightPointX = edge.getEndingX() + (size/2) ;
            rightPointY = edge.getEndingY() + (size/8);
        } else if (edge.getStartingX() < edge.getEndingX()) {
            leftPointX = edge.getEndingX() - (size/2);
            leftPointY = edge.getEndingY();
            rightPointX = edge.getEndingX();
            rightPointY = edge.getEndingY() - (size/2);
        } else {
            leftPointX = edge.getEndingX() - (size/2);
            leftPointY = edge.getEndingY() - (size/2);
            rightPointX = edge.getEndingX() + (size/2);
            rightPointY = edge.getEndingY() - (size/2);
        }
        polygon.getPoints().addAll(
                new Double[] {
                        headPointX,headPointY,
                        leftPointX,leftPointY,
                        rightPointX,rightPointY
                }
        );

        return polygon;
    }

}
