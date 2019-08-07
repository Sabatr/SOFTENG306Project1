package visualisation.controllers;

import files.DotParser;
import graph.Edge;
import graph.Graph;
import graph.Vertex;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GUIController {

    @FXML
    private Button button;
    @FXML
    private Pane graphPane;

    @FXML
    private void initialize() {
        createInputGraph();
    }

    /**
     * Creates the graph for the input.
     * TODO: Move location? Make this look prettier/refactor
     */
    private void createInputGraph() {
        SingleGraph graph = new SingleGraph("test");
        graph.addAttribute("ui.stylesheet","url('visualisation/graphassets/inputGraph.css')");
        Graph inputGraph = retrieveInputGraph("input.dot");
        HashMap<String, Edge> edges = inputGraph.getEdgeHashMap();
        HashMap<String, Vertex> vertices = inputGraph.getVertexHashMap();

        Iterator vertexIt = vertices.entrySet().iterator();
        while (vertexIt.hasNext()) {
            Map.Entry<String,Vertex> pair = (Map.Entry)vertexIt.next();
            String key = pair.getKey();
            Vertex vertex = pair.getValue();
            graph.addNode(key);
            Node n = graph.getNode(key);
            n.setAttribute("ui.label",key);
            n.setAttribute("weight",vertex.getCost());
        }

        Iterator edgeIt = edges.entrySet().iterator();
        while (edgeIt.hasNext()) {
            Map.Entry<String,Edge> pair = (Map.Entry) edgeIt.next();
            String key = pair.getKey();
            Edge edge = pair.getValue();
            graph.addEdge(key,edge.getFromVertex().getId(),edge.getToVertex().getId(),true);
            org.graphstream.graph.Edge e = graph.getEdge(key); //Can't just use import because there's two diff edges
            e.setAttribute("ui.label",edge.getSwitchCost());
        }



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
     * Retrieving the input graph.
     * @return
     */
    private Graph retrieveInputGraph(String path) {
        Graph inputGraph = null;
        try {
            inputGraph = new DotParser(new File("data/"+path)).parseGraph();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return inputGraph;
    }


    /**
     * Dummy method to test javafx XD
     */
    @FXML
    private void onClick() {
        System.out.println("clicked");
    }
}
