package visualisation.controllers.helpers;

import files.DotParser;
import graph.Graph;
import graph.Vertex;
import javafx.scene.layout.Pane;
import visualisation.AlgorithmDataStorage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TreeGenerator {
    private Pane graphPane;
    private Graph inputGraph;
    private HashMap<Integer,List<Vertex>> layerNode;
    private HashMap<Vertex,VisualNode> vertexToNode = new HashMap<>();
    private int size;
    private final int DEFAULT_NODE_SIZE = 200;
    public TreeGenerator(Pane parentPane) {
        this.graphPane = parentPane;
        this.inputGraph = retrieveInputGraph(AlgorithmDataStorage.getInstance().getInputFileName());
        layerNode = new HashMap<>();
        size = DEFAULT_NODE_SIZE / inputGraph.getVertexHashMap().size();
    }

    private void createNode(List<Vertex> vertices,int currentLayer) {
        int current = 0;
        double paneWidth = graphPane.getPrefWidth();
        double paneHeight = graphPane.getPrefHeight();
        for (Vertex vertex : vertices) {
            int x = (int) Math.ceil(paneWidth/vertices.size()/2 + (2 *current * paneWidth/vertices.size()/2));
            int y = (int) Math.ceil( paneHeight / (layerNode.size()+1) * currentLayer);
            VisualNode node = new VisualNode(x,y,size,vertex);
            vertexToNode.put(vertex,node);
            graphPane.getChildren().add(node);
            graphPane.getChildren().add(node.getNodeName());
            graphPane.getChildren().add(node.getNodeWeight());
            current++;
        }
    }

    private void createEdge(List<Vertex> vertices) {
        for (Vertex vertex : vertices) {
            List<Vertex> incomingVertices = vertex.getIncomingVerticies();
            VisualNode toNode = vertexToNode.get(vertex);
            for (Vertex fromVertex : incomingVertices) {
                VisualNode fromNode = vertexToNode.get(fromVertex);
                VisualEdge edge = new VisualEdge(fromNode.getX(),fromNode.getY(),toNode.getX(),toNode.getY());
                graphPane.getChildren().add(edge);
                graphPane.getChildren().add(edge.getEdgeText(fromVertex,vertex, size));
            }
        }
    }


    //TODO: Make this work if given a graph with no edges
    private void mapLayerToTasks() {
        HashMap<String,Vertex> vertices = inputGraph.getVertexHashMap();
        Iterator vertexIt = vertices.entrySet().iterator();
        int currentLayer = 1;

        // Gets the root node
        List<Vertex> layeredVertices = new ArrayList<>();
        //List<Vertex> possibleRoots = new ArrayList<>();
        //Vertex root = new Vertex("",1);
        while (vertexIt.hasNext()) {
            Map.Entry<String,Vertex> pair = (Map.Entry)vertexIt.next();
            String key = pair.getKey();
            Vertex vertex = pair.getValue();
            if (vertex.isRoot()) {
                layeredVertices.add(vertex);
            }
        }
        //layeredVertices.add(root);
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
//            for (Vertex v : layerNode.get(currentLayer)) {
//                addNodesToMap(v,currentLayer+1);
//            }
            currentLayer++;
        }
    }

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

    private void checkIfSeenBefore(Vertex v) {
        for (int layer : layerNode.keySet()) {
            List<Vertex> vertices = layerNode.get(layer);
            if (vertices.contains(v)) {
                vertices.remove(v);
                layerNode.put(layer,vertices);
            }
        }
    }

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
    public void generate() {
        generateNodePositions();
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
