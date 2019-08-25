package visualisation.controllers.helpers;

import files.DotParser;
import graph.Graph;
import graph.Vertex;
import javafx.scene.layout.Pane;
import java.util.*;

public class TreeGenerator {
    private Pane graphPane;
    private Graph inputGraph;
    private HashMap<Integer,List<Vertex>> layerNode;
    private HashMap<Vertex,VisualNode> vertexToNode = new HashMap<>();
    private int size;
    private final int DEFAULT_NODE_SIZE = 100;
    private final int PANE_CENTER_DIVIDER = 2;
    private final int INCREASED_AMOUNT_MULTIPLIER = 2;
    private final int Y_POSITION_INCREASE = 1;
    public TreeGenerator(Pane parentPane) {
        this.graphPane = parentPane;
        inputGraph = DotParser.getInstance().getGraph();
        layerNode = new HashMap<>();
    }

    /**
     * Creates a node and places it on the pane
     * @param vertices
     * @param currentLayer
     */
    private void createNode(List<Vertex> vertices,int currentLayer) {
        int current = 0;
        double paneWidth = graphPane.getPrefWidth();
        double paneHeight = graphPane.getPrefHeight();
        size = DEFAULT_NODE_SIZE / layerNode.size();
        for (Vertex vertex : vertices) {
            // Guarantees the positioning of the nodes
            int x = (int) Math.ceil(paneWidth/vertices.size()/PANE_CENTER_DIVIDER +
                    (INCREASED_AMOUNT_MULTIPLIER * current * paneWidth/vertices.size()/PANE_CENTER_DIVIDER) - 10);
            int y = (int) Math.ceil( paneHeight / (layerNode.size()+Y_POSITION_INCREASE) * currentLayer - 40);
            VisualNode node = new VisualNode(x,y,size,vertex);
            vertexToNode.put(vertex,node);
            graphPane.getChildren().add(node);
            graphPane.getChildren().add(node.getNodeName());
            graphPane.getChildren().add(node.getNodeWeight());
            current++;
        }
    }

    /**
     * Creates an edge and places it in place on the pane
     * @param vertices
     */
    private void createEdge(List<Vertex> vertices) {
        for (Vertex vertex : vertices) {
            List<Vertex> incomingVertices = vertex.getIncomingVerticies();
            VisualNode toNode = vertexToNode.get(vertex);
            for (Vertex fromVertex : incomingVertices) {
                VisualNode fromNode = vertexToNode.get(fromVertex);
                int getFromX;
                int getFromY;
                int getToX;
                int getToY ;
                if (fromNode.getX() < toNode.getX()) {
                    getFromX = fromNode.getX() + size;
                    getFromY = fromNode.getY();


                } else if (fromNode.getX() > toNode.getX()){
                    getFromX = fromNode.getX() - size;
                    getFromY = fromNode.getY();
                } else {
                    getFromX = fromNode.getX();
                    getFromY = fromNode.getY() + size;
                }

                getToX = toNode.getX();
                getToY = toNode.getY() - size;

                VisualEdge edge = new VisualEdge(getFromX,getFromY,getToX,getToY);
                DirectedArrow dArrow = new DirectedArrow(edge,size);
                graphPane.getChildren().add(dArrow);
                graphPane.getChildren().add(edge);
                graphPane.getChildren().add(edge.getEdgeText(fromVertex,vertex, size));
            }
        }
    }


    /**
     * This method separates the tasks onto different layers
     * These are stored in a hashmap.
     */
    private void mapLayerToTasks() {
        HashMap<String,Vertex> vertices = inputGraph.getVertexHashMap();
        Iterator vertexIt = vertices.entrySet().iterator();
        int currentLayer = 1;
        List<Vertex> layeredVertices = new ArrayList<>();
        // Loops through all of the vertices and checks if it is a root.
        // This is done to handle multiple roots.
        while (vertexIt.hasNext()) {
            Map.Entry<String,Vertex> pair = (Map.Entry)vertexIt.next();
            String key = pair.getKey();
            Vertex vertex = pair.getValue();
            if (vertex.isRoot()) {
                layeredVertices.add(vertex);
            }
        }
        layerNode.put(currentLayer,layeredVertices);
        currentLayer++;
        for (Vertex vertex : layeredVertices) {
            addNodesToMap(vertex,currentLayer);
        }

        while (layerNode.containsKey(currentLayer)) {
            List<Vertex> test = layerNode.get(currentLayer);
            for (int i = 0; i < test.size() ; i++) {
                Vertex v = test.get(i);
                addNodesToMap(v,currentLayer+1);
            }
            currentLayer++;
        }
    }

    /**
     * Maps the vertex to visual node.
     * This is done so it is easier and faster to access the visual node.
     * @param vertex
     * @param currentLayer
     */
    private void addNodesToMap(Vertex vertex, int currentLayer) {
        for (Vertex outGoing : vertex.getOutgoingVerticies()) {
            checkIfSeenBefore(outGoing);
            if (!layerNode.containsKey(currentLayer)) {
                List<Vertex> layeredVertices = new ArrayList<>();
                layeredVertices.add(outGoing);
                layerNode.put(currentLayer,layeredVertices);
            } else {
                List<Vertex> layeredVertices = layerNode.get(currentLayer);
                if (!layeredVertices.contains(outGoing)) {
                    layeredVertices.add(outGoing);
                    layerNode.put(currentLayer,layeredVertices);
                }
            }
        }
    }

    /**
     * This method prevents duplicate vertices to be added
     * @param v
     */
    private void checkIfSeenBefore(Vertex v) {
        for (int layer : layerNode.keySet()) {
            List<Vertex> vertices = layerNode.get(layer);
            if (vertices.contains(v)) {
                vertices.remove(v);
                layerNode.put(layer,vertices);
            }
        }
    }

    /**
     * Generates nodes and edges for the graph
     */
    private void generateNodePositions() {
        mapLayerToTasks();
        Iterator nodeIterator = layerNode.entrySet().iterator();
        while (nodeIterator.hasNext()) {
            Map.Entry<Integer,List<Vertex>> pair = (Map.Entry)nodeIterator.next();
            int currentLayer = pair.getKey();
            List<Vertex> vertices = pair.getValue();
            createNode(vertices,currentLayer);
            createEdge(vertices);

        }
    }

    /**
     * Generates the graph
     * @return
     */
    public Pane generate() {
        generateNodePositions();
        return graphPane;
    }
}
