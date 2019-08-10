package visualisation.controllers;

import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import org.graphstream.graph.Graph;
import org.graphstream.ui.fx_viewer.FxDefaultView;
import org.graphstream.ui.fx_viewer.FxViewPanel;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.javafx.FxGraphRenderer;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.GraphRenderer;
import org.graphstream.ui.view.Viewer;
import visualisation.controllers.helpers.InputGraphHelper;

import java.awt.*;

public class GUIController {

    @FXML
    private Button button;
    @FXML
    private Pane graphPane;
    @FXML
    private Pane processPane;

    @FXML
    private void initialize() {
       createInputGraphVisual();
       createProcessVisual();
    }

    /**
     * This method allows for the creation of the input graph visualisation.
     * It uses the InputGraphHelper class to add vertices/edges and also add styling.
     * This method puts the graphic created onto a pane.
     */
    private void createInputGraphVisual() {
        GraphicGraph graph = new InputGraphHelper().createInputGraph();
        graph.setAttribute("ui.antialias");
        graph.setAttribute("ui.quality");
        FxViewer viewer = new FxViewer(graph);
        viewer.enableAutoLayout();
        FxViewPanel view = (FxViewPanel)viewer.addDefaultView(false, new FxGraphRenderer());
        //Base the view size on the graph pane.
        view.setPrefSize((int)graphPane.getPrefWidth(),(int)graphPane.getPrefHeight());
        graphPane.getChildren().add(view);
    }

    /**
     * This method creates a visual for the process table.s
     */
    private void createProcessVisual() {
        /**
         * The basis:
         *  Create a table - Columns depend on the number of processors
         *  Rows will be added dynamically as more tasks are iterated through OR possibly just set the maximum possible as the rows
         *  Change colours when a task is on that processor
         *  How am I gonna get the number of processors? A listener maybe?
         *  Watch out for large inputs. Might screw over some layout.
         */
//        processPane.setPrefSize(500,500);
//        TableView table = new TableView();
//        table.setPrefSize(500,500);
//        table.getColumns().add(new TableColumn("Test"));
//        processPane.getChildren().add(table);
    }


    /**
     * Dummy method to test javafx XD
     */
    @FXML
    private void onClick() {
        System.out.println("clicked");
    }
}
