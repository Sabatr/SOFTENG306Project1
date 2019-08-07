package visualisation.controllers;

import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
       createProcessVisual();
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
        graphPane.setPrefSize(500,500);
        view.setPreferredSize(new Dimension(500,500));
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
         *  Change colours when a task is on that processor
         *  How am I gonna get the number of processors? A listener maybe?
         *  Watch out for large inputs. Might screw over some layout.
         */
        processPane.setPrefSize(500,500);
        TableView table = new TableView();
        table.setPrefSize(500,500);
        table.getColumns().add(new TableColumn("Test"));
        processPane.getChildren().add(table);
    }


    /**
     * Dummy method to test javafx XD
     */
    @FXML
    private void onClick() {
        System.out.println("clicked");
    }
}
