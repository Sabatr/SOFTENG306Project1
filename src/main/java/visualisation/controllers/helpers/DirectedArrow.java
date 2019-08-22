package visualisation.controllers.helpers;

import javafx.scene.Group;
import javafx.scene.shape.Polygon;

/**
 * WORK IN PROGRESS: Adding arrows to graph
 */
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

        int xWidth = Math.abs(edge.getStartingX() - edge.getEndingX());
        int yWidth = Math.abs(edge.getStartingY() - edge.getEndingY());
        double angle = Math.atan(xWidth/yWidth);
        //System.out.println("Angle is" + angle);
        if (angle == 0) {
            angle = 90.0;
            xWidth = 1;
        }
        double xBuffer = (xWidth * Math.tan(angle/2))/2;

        double yBuffer =  (xBuffer / Math.tan(angle/2))/4;


        if (edge.getStartingX() > edge.getEndingX()) {
            leftPointX = edge.getEndingX() + xBuffer/2;
            leftPointY = edge.getEndingY() - yBuffer;
            rightPointX = edge.getEndingX()  + xBuffer/1.5;
            rightPointY = edge.getEndingY() ;
        } else if (edge.getStartingX() < edge.getEndingX()) {
            leftPointX = edge.getEndingX() - xBuffer/2;
            leftPointY = edge.getEndingY() - yBuffer;
            rightPointX = edge.getEndingX() - xBuffer/1.5;
            rightPointY = edge.getEndingY();
        } else {
            leftPointX = edge.getEndingX() - xBuffer/2;
            leftPointY = edge.getEndingY() - yBuffer/2;
            rightPointX = edge.getEndingX() + xBuffer/2;
            rightPointY = edge.getEndingY() - yBuffer/2;
            System.out.println(yBuffer);
            System.out.println(leftPointY);
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
