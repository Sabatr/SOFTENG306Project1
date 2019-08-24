package visualisation.processor.helpers;

import javafx.scene.CacheHint;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;

public class CustomProgressBar extends ProgressBar {
    public CustomProgressBar(Pane pane) {
        super();

        this.setCache(true);
        this.setCacheShape(true);
        this.setCacheHint(CacheHint.SPEED);
        this.setLayoutX(pane.getPrefWidth()/2);
        //this.setLayoutY(pane.getPrefHeight()/2);
        this.setLayoutY(pane.getPrefHeight()/2);
    }
}
