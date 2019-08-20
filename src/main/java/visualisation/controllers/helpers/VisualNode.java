package visualisation.controllers.helpers;

import javafx.scene.shape.Circle;

public class VisualNode extends Circle{

    private int x;
    private int y;
    private int size;
    public VisualNode(int x, int y, int size) {
        super(x,y,size);

        this.x = x;
        this.y = y;
        this.size =size;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
