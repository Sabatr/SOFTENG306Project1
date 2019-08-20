package visualisation.controllers.helpers;

import graph.Vertex;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class VisualNode extends Circle{

    private int x;
    private int y;
    private int size;
    private Vertex vertex;
    public VisualNode(int x, int y, int size, Vertex vertex) {
        super(x,y,size);
        this.setFill(Color.RED);
        this.x = x;
        this.y = y;
        this.size =size;
        this.vertex = vertex;
    }

    private Text buildText(String inputText, int fontSize) {
        Text text = new Text(inputText);
        text.setFont(new Font(fontSize));
       // text.getLayoutBounds().getWidth();
        text.setX(this.x - text.getLayoutBounds().getWidth()/2);
        text.setY(this.y);
        //System.out.println(text);
        return text;
    }

    private Text buildText(String inputText, int fontSize, int y) {
        Text text = this.buildText(inputText,fontSize);
        text.setY(y);
        return text;
    }

    public Text getNodeName() {
        return buildText(vertex.getId(),this.size);
    }

    public Text getNodeWeight() {
        return buildText(vertex.getCost()+"",this.size/2,this.y + this.size/2);
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
