package files;

import graph.Edge;
import graph.Graph;
import graph.Vertex;
import scheduler.State;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This class is used for creating the output file in the same structure outlined within the brief
 */
public class OutputCreator {

    private State state;
    private Graph graph;

    public OutputCreator(State state){
        this.state = state;
        state.outputFormat();
        graph = state.getG();
        createOutputDirectory();
    }

    /**
     * Displays output string on the console
     */
    public void displayOutputOnConsole() {
        System.out.println(constructOutputLine());
    }

    private void createOutputDirectory(){
        String path = "data/";

        File newFolder = new File(path);
        if (!newFolder.exists()) {
            newFolder.mkdir();
        }
    }

    /**
     * Creates a file within the /data/ directory for output
     * @param name
     */

    public void createOutputFile(String name) {
        String filePath = "data/" + name;
        String output = constructOutputLine();
        try (PrintWriter out = new PrintWriter(filePath)) {
            out.println(output);
        } catch (FileNotFoundException fnfe){
            fnfe.printStackTrace();
        }

    }

    /**
     * Constructs a string for the output with the same structure as that which is in the brief
     * @return String which represents the constructed output
     */
    private String constructOutputLine() {
        HashMap<String, Vertex> vertices = graph.getVertexHashMap();
        HashMap<String, Edge> edges = graph.getEdgeHashMap();
        Iterator it = vertices.entrySet().iterator();

        StringBuilder output = new StringBuilder("digraph " + graph.getName() + " {");

        for(Vertex v : vertices.values()){
            output.append("\n\t").append(v.toStringSolution());
        }

        for(Edge e:edges.values()){
            output.append("\n\t").append(e.toString());
        }

        output.append("\n}");

        return output.toString();
    }

}
