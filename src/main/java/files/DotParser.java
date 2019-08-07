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

//TODO: Should probably make this a single ton. We're creating a graph each time this is called,
// when we should just run it once- Brian
public class DotParser {
    File f;

    public DotParser(File f) {
        this.f = f;
    }

    /**
     * Main method in charge of parsing the graph
     * @return
     * @throws FileNotFoundException
     */
    public Graph parseGraph() throws FileNotFoundException {
        GraphParser graphParser = new GraphParser(new FileInputStream(f));
        Graph g = new Graph(graphParser.getGraphId());

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

        return g;

    }
}
