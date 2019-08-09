import algorhithm.AStar;
import files.DotParser;
import graph.Graph;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import scheduler.State;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * A test class to test multiple input graphs with the AStar algorithm.
 */
public class AStarStateTests {
    /**
     * A method for creating a graph for the test cases.
     * @param inputURL
     * @return
     */
    private Graph createGraph(String inputURL) {
        DotParser dp = new DotParser(new File("data/" + inputURL));
        Graph g1 = null;
        try {
            g1 = dp.parseGraph();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return g1;
    }

    /**
     * This tests if the output is correct given a graph with random weights on two processors.
     */
    @Test
    public void testNodes8double() {
        Graph createdGraph = createGraph("Nodes_8_Random.dot");
        State optimalState = new AStar(2, createdGraph).runAlgorithm();
        Assert.assertEquals(optimalState.getCurrentCost(), 581);
    }

    /**
     * This tests if the output is correct given a graph with large communication costs on two processors.
     */
    @Test
    public void testNodes9double() {
        Graph createdGraph = createGraph("Nodes_9_SeriesParallel.dot");
        State optimalState = new AStar(2, createdGraph).runAlgorithm();
        Assert.assertEquals(optimalState.getCurrentCost(), 55);
    }

    /**
     * This tests if the output is correct given a graph with a mix of small and large communication
     * on two processors.
     */
    @Test
    public void testNodes10double() {
        Graph createdGraph = createGraph("Nodes_10_Random.dot");
        State optimalState = new AStar(2, createdGraph).runAlgorithm();
        Assert.assertEquals(optimalState.getCurrentCost(), 50);
    }

    /**
     * This tests if the output is correct given a graph with large node weights on two processors.
     */
    @Test
    public void testNodes11double() {
        Graph createdGraph = createGraph("Nodes_11_OutTree.dot");
        State optimalState = new AStar(2, createdGraph).runAlgorithm();
        Assert.assertEquals(optimalState.getCurrentCost(), 350);
    }

    /**
     * This tests if the output is correct given a graph with a set path on two processors.
     */
    @Test
    public void testNodes7double() {
        Graph createdGraph = createGraph("Nodes_7_OutTree.dot");
        State optimalState = new AStar(2, createdGraph).runAlgorithm();
        Assert.assertEquals(optimalState.getCurrentCost(), 28);
    }

    /**
     * This tests if the output is correct given a graph with large node weights on four processors.
     */
    @Test
    public void testNodes11Quad() {
        Graph createdGraph = createGraph("Nodes_11_OutTree.dot");
        State optimalState = new AStar(4, createdGraph).runAlgorithm();
        Assert.assertEquals(optimalState.getCurrentCost(), 227);
    }

    /**
     * This tests if the output is correct given a graph with random weights on four processors.
     */
    @Test
    public void testNodes8Quad() {
        Graph createdGraph = createGraph("Nodes_8_Random.dot");
        State optimalState = new AStar(4, createdGraph).runAlgorithm();
        Assert.assertEquals(optimalState.getCurrentCost(), 581);
    }

    /**
     * This tests if the output is correct given a graph with a mix of small and large communication
     * on fourW processors.
     */
    @Test
    public void testNodes10Quad() {
        Graph createdGraph = createGraph("Nodes_10_Random.dot");
        State optimalState = new AStar(4, createdGraph).runAlgorithm();
        Assert.assertEquals(optimalState.getCurrentCost(), 50);
    }

    /**
     * This tests if the output is correct given a graph with large communication costs on four processors.
     */
    @Test
    public void testNodes9Quad() {
        Graph createdGraph = createGraph("Nodes_9_SeriesParallel.dot");
        State optimalState = new AStar(4, createdGraph).runAlgorithm();
        Assert.assertEquals(optimalState.getCurrentCost(), 55);
    }

    /**
     * This tests if the output is correct given a graph with a set path on four processors.
     */
    @Test
    public void testNodes7Quad() {
        Graph createdGraph = createGraph("Nodes_7_OutTree.dot");
        State optimalState = new AStar(4, createdGraph).runAlgorithm();
        Assert.assertEquals(optimalState.getCurrentCost(), 22);
    }

    /**
     * This tests if the output is correct given a graph with large node weights on four processors.
     */
    @Test
    public void testNodes11() {
        Graph createdGraph = createGraph("Nodes_11_OutTree.dot");
        State optimalState = new AStar(1, createdGraph).runAlgorithm();
        Assert.assertEquals(optimalState.getCurrentCost(), 640);
    }

    /**
     * This tests if the output is correct given a graph with random weights on one processor.
     */
    @Test
    public void testNodes8() {
        Graph createdGraph = createGraph("Nodes_8_Random.dot");
        State optimalState = new AStar(1, createdGraph).runAlgorithm();
        Assert.assertEquals(optimalState.getCurrentCost(), 969);
    }

    /**
     * This tests if the output is correct given a graph with a mix of small and large communication
     * on one processor.
     */
    @Test
    public void testNodes10() {
        Graph createdGraph = createGraph("Nodes_10_Random.dot");
        State optimalState = new AStar(1, createdGraph).runAlgorithm();
        Assert.assertEquals(optimalState.getCurrentCost(), 63);
    }

    /**
     * This tests if the output is correct given a graph with large communication costs on a processor.
     */
    @Test
    public void testNodes9() {
        Graph createdGraph = createGraph("Nodes_9_SeriesParallel.dot");
        State optimalState = new AStar(1, createdGraph).runAlgorithm();
        Assert.assertEquals(optimalState.getCurrentCost(), 55);
    }

    /**
     * This tests if the output is correct given a graph with a set path on a processor.
     */
    @Test
    public void testNodes7() {
        Graph createdGraph = createGraph("Nodes_7_OutTree.dot");
        State optimalState = new AStar(1, createdGraph).runAlgorithm();
        Assert.assertEquals(optimalState.getCurrentCost(), 40);
    }
}
