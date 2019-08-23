package visualisation.controllers.helpers.tile;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class CustomCounter extends Pane {
    private Text text;
    public CustomCounter(double width, double height) {
        super();
        this.setPrefSize(width,height);
        text = new Text("00:00:000");
        text.setFill(Color.WHITE);
        text.setFont(new Font(35));
        text.setLayoutY(height/2 - text.getBoundsInLocal().getHeight() + 10);
        text.setLayoutX(width/2 - text.getBoundsInLocal().getWidth()/2 - 10);
        this.setLayoutY(0);
        this.setLayoutX(0);
       // this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        this.getChildren().add(text);

    }

    public void setText(Text text) {
        this.text = text;
    }

    public Text getText() {
        return text;
    }
}
