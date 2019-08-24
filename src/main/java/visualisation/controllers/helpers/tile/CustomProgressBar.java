package visualisation.controllers.helpers.tile;

import javafx.scene.CacheHint;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;

/**
 * A custom component for the progress bar. This shows before the graph is displayed
 */
public class CustomProgressBar extends ProgressBar {
    public CustomProgressBar(Pane pane) {
        super();

        this.setCache(true);
        this.setCacheShape(true);
        this.setCacheHint(CacheHint.SPEED);
        this.setPrefWidth(pane.getPrefWidth());
        this.setLayoutY(pane.getPrefHeight()/2);

    }
}
