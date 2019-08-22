package visualisation.controllers.helpers;

import graph.Vertex;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * A representation of the visual node component for the input graph
 */
public class VisualNode extends Circle{
    private int x;
    private int y;
    private int size;
    private Vertex vertex;

    public VisualNode(int x, int y, int size, Vertex vertex) {
        super(x,y,size);
        this.x = x;
        this.y = y;
        this.size =size;
        this.vertex = vertex;
        this.getStyleClass().add("node");
    }

    /**
     * A builder to create the text in the centre of the node
     * @param inputText
     * @param fontSize
     * @return
     */
    private Text buildText(String inputText, int fontSize) {
        Text text = new Text(inputText);
        text.setFont(new Font(fontSize));
        text.setX(this.x - text.getLayoutBounds().getWidth()/2);
        text.setY(this.y);
        return text;
    }

    /**
     * Wrapper buildText if the user wants to also input a y position
     * @param inputText
     * @param fontSize
     * @param y
     * @return
     */
    private Text buildText(String inputText, int fontSize, int y) {
        Text text = this.buildText(inputText,fontSize);
        text.setFont(new Font(25));
        text.setY(y);
        return text;
    }

    /**
     * Retrieves the Text object for the name
     * @return
     */
    public Text getNodeName() {
        return buildText(vertex.getId(),this.size);
    }

    /**
     * Retrieves the Text object for the node weight
     * @return
     */
    public Text getNodeWeight() {
        return buildText(vertex.getCost()+"",this.size/2,this.y + this.size/2);
    }

    /**
     * Returns x position of the node
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * Returns y position of the node
     * @return
     */
    public int getY() {
        return y;
    }
}
