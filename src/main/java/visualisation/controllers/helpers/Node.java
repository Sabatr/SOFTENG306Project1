package visualisation.controllers.helpers;

import javafx.scene.shape.Circle;

public class Node extends Circle{

    public Node(int x, int y, int size) {
        super(x,y,size);
    }

    private void generateNodePositions() {

    }


    private Circle createCircle() {
        return new Circle(0,0,20);
    }
}
