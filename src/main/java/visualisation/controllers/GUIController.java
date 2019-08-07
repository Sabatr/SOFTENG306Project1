package visualisation.controllers;

import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.graphstream.graph.Graph;
import org.graphstream.ui.swingViewer.ViewPanel;
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
    }

    /**
     * This method allows for the creation of the input graph visualisation.
     * It uses the InputGraphHelper class to add vertices/edges and also add styling.
     * This method puts the graphic created onto a pane.
     */
    private void createInputGraphVisual() {
        Graph graph = new InputGraphHelper().createInputGraph();
        Viewer viewer = new Viewer(graph,Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        viewer.enableAutoLayout();
        // Creates the panel on a section of the application
        ViewPanel view = viewer.addDefaultView(false);
        graphPane.setPrefSize(600,600);
        view.setPreferredSize(new Dimension(600,600));
        SwingNode node = new SwingNode();
        node.setContent(view);
        graphPane.getChildren().add(node);
    }

    /**
     * This method creates a visual for the process table.s
     */
    private void createProcessVisual() {
        /**
         * The basis:
         *  Create a table - Columns depend on the number of processors
         *  Rows will be added dynamically as more tasks are iterated through OR possibly just set the maximum possible as the rows
         *  Change colours and stuff
         *  Watch out for large inputs. Might screw over some layout.
         */
    }


    /**
     * Dummy method to test javafx XD
     */
    @FXML
    private void onClick() {
        System.out.println("clicked");
    }
}
