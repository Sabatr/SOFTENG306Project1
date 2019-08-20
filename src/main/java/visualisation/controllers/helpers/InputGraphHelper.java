package visualisation.controllers.helpers;

import files.DotParser;
import graph.Edge;
import graph.Graph;
import graph.Vertex;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.GridGenerator;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.graphicGraph.GraphicGraph;
import visualisation.AlgorithmDataStorage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This class creates the initial input graph.
 * Currently, it does not follow the same layout as the shown graph in the example.
 */

@Deprecated
public class InputGraphHelper {
    private GraphicGraph graph;
    private final String GRAPH_ID = "input";
    private final String STYLESHEET_ATTRIBUTE = "ui.stylesheet";
    private final String STYLESHEET_PATH = "url('visualisation/visualisationassets/InputGraph.css')";
    private final boolean IS_DIRECTED = true;
    /**
     * Creates the graph which is going to be used for the input
     * TODO: Right now it just creates a new one, but this could be too slow. Possibly find a way to use the one
     * TODO: that has been already generated.
     * @return
     */
    public GraphicGraph createInputGraph() {
        graph = new GraphicGraph(GRAPH_ID);
        graph.setAttribute(STYLESHEET_ATTRIBUTE,STYLESHEET_PATH);
        Graph inputGraph = retrieveInputGraph(AlgorithmDataStorage.getInstance().getInputFileName());
        HashMap<String, Edge> edges = inputGraph.getEdgeHashMap();
        HashMap<String, Vertex> vertices = inputGraph.getVertexHashMap();
        addNodes(vertices);
        addEdges(edges);
        return graph;
    }

    private void addEdges(HashMap<String,Edge> edges) {
        Iterator edgeIt = edges.entrySet().iterator();
        while (edgeIt.hasNext()) {
            Map.Entry<String,Edge> pair = (Map.Entry) edgeIt.next();
            String key = pair.getKey();
            Edge edge = pair.getValue();
            graph.addEdge(key,edge.getFromVertex().getId(),edge.getToVertex().getId(),IS_DIRECTED);
            org.graphstream.graph.Edge e = graph.getEdge(key); //Can't just use import because there's two diff edges
        }
    }

    /**
     * Adds a node to the graph that is to be displayed
     * @param vertices
     */
    private void addNodes(HashMap<String, Vertex> vertices) {
        Iterator vertexIt = vertices.entrySet().iterator();
        while (vertexIt.hasNext()) {
            Map.Entry<String,Vertex> pair = (Map.Entry)vertexIt.next();
            String key = pair.getKey();
            Vertex vertex = pair.getValue();
            graph.addNode(key);
            Node n = graph.getNode(key);
            n.setAttribute("ui.label",key);
        }
    }

    /**
     * Retrieving the input graph.
     * @return
     */
    private Graph retrieveInputGraph(String path) {
        Graph inputGraph = null;
        try {
            inputGraph = new DotParser(new File(path)).parseGraph();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return inputGraph;
    }

}
