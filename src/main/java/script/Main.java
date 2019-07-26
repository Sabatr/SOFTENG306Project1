package script;

import java.io.*;

public class Main {

    private static Graph _graph;

    public static void main(String[] args) {
        File f = new File("data/input.dot");
        try{
            parse(f);
        } catch (Exception e){
            System.out.println("Some Error Occurred");
        }
    }

    /**
     * Takes the file input of correct syntax and reads the text of the file to be formatted
     * @param f
     * @throws FileNotFoundException
     * @throws IOException
     */
    private static void parse(File f)throws FileNotFoundException, IOException {

        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        String str;
        while ((str = br.readLine()) != null){
            processString(str);
        }


    }

    /**
     * Formats the input file into a graph object form, and populates the graph
     * @param str
     */
    private static void processString(String str){
        //if first line of .dot input
        if (str.contains("{")){
            _graph = new Graph(getGraphName(str));
        }
        //if edge
        else if (str.contains("-")){
            Edge e = processEdge(str);
            _graph.addEdge(e.getId(), e);
        }
        //if vertex
        else if (str.contains("[")){
            Vertex v = processVertex(str);
            _graph.addVertex(v.getId(), v);
        }
    }

    /**
     * reads the first line of the text input for the name of the graph
     * @param str - the first line of a .dot input with proper syntax
     * @return
     */
    private static String getGraphName(String str){
        return str.substring(str.indexOf("\"")+1, str.lastIndexOf("\""));
    }

    /**
     * formats the strings that represent vertices into vertex objects
     * @param str - a string that has been formatted to be a vertex, e.g. "a [weight=3];"
     * @return
     */
    private static Vertex processVertex(String str){
        String[] values = str.split("\\t");
        String name = values[0];
        int weight = getWeight(values[1]);
        Vertex v = new Vertex(name, weight);
        return v;
    }

    /**
     * formats the strings that represent edges into edge objects
     * @param str - a string that has been formatted to be an edge, e.g. "a->b [weight=3];"
     * @return
     */
    private static Edge processEdge(String str){
        String[] values = str.split("\\t");
        String name = values[0];
        int weight = getWeight(values[1]);

        String[] vertices = values[0].split("->");
        Vertex fromVertex = _graph.getVertex(vertices[0]);
        Vertex toVertex = _graph.getVertex(vertices[1]);

        Edge e = Edge.createEdge(name, weight, fromVertex, toVertex);
        return e;
    }

    /**
     * Only takes in strings of format "[Weight=x]" where x is an integer, and returns the integer
     * @return integer value of x
     */
    private static int getWeight(String str){
        str = str.replaceAll("\\D+","");
        int weight = Integer.parseInt(str);
        return weight;
    }

}

