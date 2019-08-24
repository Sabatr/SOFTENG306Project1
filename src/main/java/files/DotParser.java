package files;

import graph.Graph;
import com.paypal.digraph.parser.GraphEdge;
import com.paypal.digraph.parser.GraphNode;
import com.paypal.digraph.parser.GraphParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import graph.Vertex;
import graph.Edge;

/**
 * A singleton class to help deal with file input, and generating a corresponding graph from this input
 */
public class DotParser {
    private static DotParser parser = new DotParser();
    private static File f;

    // ONLY WANT ONE INSTANCE OF A GRAPH
    private static Graph g;
    private DotParser() {

    }

    /**
     *
     * @return the instance of the dot parser
     */
    public static DotParser getInstance() {
        return parser;
    }
    /**
     * application.Main method in charge of parsing the graph
     * @return
     * @throws FileNotFoundException
     */
    public void parseGraph(File f) throws FileNotFoundException {
        GraphParser graphParser = new GraphParser(new FileInputStream(f));
        g = new Graph(graphParser.getGraphId());

        //Set up the vertices
        for (GraphNode node : graphParser.getNodes().values()) {
            String nodeName = node.getId();
            String weight = node.getAttribute("Weight").toString();
            Integer weightInt = Integer.valueOf(weight);
            Vertex toAdd = new Vertex(nodeName, weightInt);
            g.addVertex(nodeName, toAdd);
        }

        //Set up the edges
        for (GraphEdge e : graphParser.getEdges().values()) {
            String fromVertex = e.getNode1().getId();
            String toVertex = e.getNode2().getId();
            String weight = e.getAttribute("Weight").toString();
            Integer weightInt = Integer.valueOf(weight);
            Vertex from = g.getVertex(fromVertex);
            Vertex to = g.getVertex(toVertex);

            Edge toAdd = new Edge(from, to, weightInt);
            from.addOutgoingEdge(toAdd);
            g.addEdge(toAdd.getId(),toAdd);
        }
    }

    public Graph getGraph() {
        return g;
    }
}
