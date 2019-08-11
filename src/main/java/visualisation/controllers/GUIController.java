package visualisation.controllers;

import files.OutputCreator;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.graphstream.ui.fx_viewer.FxViewPanel;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.javafx.FxGraphRenderer;
import visualisation.AlgorithmDataStorage;
import visualisation.controllers.helpers.InputGraphHelper;
import visualisation.processor.helpers.ProcessChartHelper;

public class GUIController {
    @FXML
    private Pane graphPane;
    @FXML
    private Pane processPane;
    @FXML
    private Label timeElapsed;
    @FXML
    private Label branchesVisited;
    @FXML
    private Button button;

    private ProcessChartHelper helper;
    @FXML
    private void onClick() {
        System.out.println("clicked");
    }
    /**
     * When the application starts, run this.
     */
    @FXML
    private void initialize() {
       createInputGraphVisual();

       createProcessVisual();
       setTimeLabel();
       setBranchesLabel();
    }

    public void updateBranchCount(String label) {
        Platform.runLater(() -> branchesVisited.setText(label));
    }

    public void updateTimer(String time){
        Platform.runLater(() -> timeElapsed.setText(time));
    }

    private void setTimeLabel() {
        timeElapsed.setText("0ms");
     //   timeElapsed.setText(AlgorithmDataStorage.getInstance().getTimeElapsed() + "ms");
    }

    private void setBranchesLabel() {
        branchesVisited.setText("0");
     //   branchesVisited.setText(AlgorithmDataStorage.getInstance().getBranchesVisited()+" branches visited.");
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
    public void createProcessVisual() {
        /**
         * The basis:
         *  Create a table - Columns depend on the number of processors - done
         *  Rows will be added dynamically as more tasks are iterated through OR possibly just set the maximum possible as the rows
         *  Change colours when a task is on that processor
         *  How am I gonna get the number of processors? A listener maybe? - done
         *  Watch out for large inputs. Might screw over some layout.
         */
        if (helper == null) {
            helper = new ProcessChartHelper(processPane);
        }
         processPane.getChildren().add(helper.getProcessChart());
    }

    public void updateChart() {
        Platform.runLater(() -> helper.updateChart());

    }

}
