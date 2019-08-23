
import algorithm.AStar;
import algorithm.DFS;
import files.DotParser;
import graph.Graph;
import org.junit.Assert;
import org.junit.Test;
import scheduler.State;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * A test class to test multiple input graphs with the DFS algorithm.
 */
public class DFSStateTests {
    /**
     * A method for creating a graph for the test cases.
     *
     * @param inputURL
     * @return
     */
    private Graph createGraph(String inputURL) {
        try {
            DotParser.getInstance().parseGraph(new File("data/" + inputURL));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Graph g1 = DotParser.getInstance().getGraph();
        return g1;
    }

    @Test
    public void testHashCode() {
        Graph createdGraph = createGraph("Nodes_8_Random.dot");
        State optimalState = new DFS(2, createdGraph).runAlgorithm();
        Graph createdGraph1 = createGraph("Nodes_8_Random.dot");
        State optimalState1 = new DFS(2, createdGraph1).runAlgorithm();
        Assert.assertTrue(optimalState.equals(optimalState1));
    }

    /**
     * This tests if the output is correct given a graph with random weights on two processors.
     */
    @Test
    public void testNodes8double() {
        Graph createdGraph = createGraph("Nodes_8_Random.dot");
        State optimalState = new DFS(2, createdGraph).runAlgorithm();
        Assert.assertEquals(optimalState.getCurrentCost(), 581);
        Assert.assertTrue(optimalState.isValid());
    }

    /**
     * This tests if the output is correct given a graph with large communication costs on two processors.
     */
    @Test
    public void testNodes9double() {
        Graph createdGraph = createGraph("Nodes_9_SeriesParallel.dot");
        State optimalState = new AStar(2, createdGraph).runAlgorithm();
        Assert.assertEquals(optimalState.getCurrentCost(), 55);
        Assert.assertTrue(optimalState.isValid());
    }

    /**
     * This tests if the output is correct given a graph with a mix of small and large communication
     * on two processors.
     */
    @Test
    public void testNodes10double() {
        Graph createdGraph = createGraph("Nodes_10_Random.dot");
        State optimalState = new DFS(2, createdGraph).runAlgorithm();
        Assert.assertEquals(optimalState.getCurrentCost(), 50);
        Assert.assertTrue(optimalState.isValid());
    }

    /**
     * This tests if the output is correct given a graph with large node weights on two processors.
     */
    @Test
    public void testNodes11double() {
        Graph createdGraph = createGraph("Nodes_11_OutTree.dot");
        State optimalState = new DFS(2, createdGraph).runAlgorithm();
        Assert.assertEquals(optimalState.getCurrentCost(), 350);
        Assert.assertTrue(optimalState.isValid());
    }

    /**
     * This tests if the output is correct given a graph with a set path on two processors.
     */
    @Test
    public void testNodes7double() {
        Graph createdGraph = createGraph("Nodes_7_OutTree.dot");
        State optimalState = new DFS(2, createdGraph).runAlgorithm();
        Assert.assertEquals(optimalState.getCurrentCost(), 28);
        Assert.assertTrue(optimalState.isValid());
    }

    /**
     * This tests if the output is correct given a graph with large node weights on four processors.
     */
    @Test
    public void testNodes11Quad() {
        Graph createdGraph = createGraph("Nodes_11_OutTree.dot");
        State optimalState = new DFS(4, createdGraph).runAlgorithm();
        Assert.assertEquals(optimalState.getCurrentCost(), 227);
        Assert.assertTrue(optimalState.isValid());
    }

    /**
     * This tests if the output is correct given a graph with random weights on four processors.
     */
    @Test
    public void testNodes8Quad() {
        Graph createdGraph = createGraph("Nodes_8_Random.dot");
        State optimalState = new DFS(4, createdGraph).runAlgorithm();
        Assert.assertEquals(optimalState.getCurrentCost(), 581);
        Assert.assertTrue(optimalState.isValid());
    }

    /**
     * This tests if the output is correct given a graph with a mix of small and large communication
     * on four processors.
     */
    @Test
    public void testNodes10Quad() {
        Graph createdGraph = createGraph("Nodes_10_Random.dot");
        State optimalState = new DFS(4, createdGraph).runAlgorithm();
        Assert.assertEquals(optimalState.getCurrentCost(), 50);
        Assert.assertTrue(optimalState.isValid());
    }

    /**
     * This tests if the output is correct given a graph with large communication costs on four processors.
     */
    @Test
    public void testNodes9Quad() {
        Graph createdGraph = createGraph("Nodes_9_SeriesParallel.dot");
        State optimalState = new DFS(4, createdGraph).runAlgorithm();
        Assert.assertEquals(optimalState.getCurrentCost(), 55);
        Assert.assertTrue(optimalState.isValid());
    }

    /**
     * This tests if the output is correct given a graph with a set path on four processors.
     */
    @Test
    public void testNodes7Quad() {
        Graph createdGraph = createGraph("Nodes_7_OutTree.dot");
        State optimalState = new DFS(4, createdGraph).runAlgorithm();
        Assert.assertEquals(optimalState.getCurrentCost(), 22);
        Assert.assertTrue(optimalState.isValid());
    }

    /**
     * This tests if the output is correct given a graph with large node weights on four processors.
     */
    @Test
    public void testNodes11() {
        Graph createdGraph = createGraph("Nodes_11_OutTree.dot");
        State optimalState = new DFS(1, createdGraph).runAlgorithm();
        Assert.assertEquals(optimalState.getCurrentCost(), 640);
        Assert.assertTrue(optimalState.isValid());
    }

    /**
     * This tests if the output is correct given a graph with random weights on one processor.
     */
    @Test
    public void testNodes8() {
        Graph createdGraph = createGraph("Nodes_8_Random.dot");
        State optimalState = new DFS(1, createdGraph).runAlgorithm();
        Assert.assertEquals(optimalState.getCurrentCost(), 969);
        Assert.assertTrue(optimalState.isValid());
    }

    /**
     * This tests if the output is correct given a graph with a mix of small and large communication
     * on one processor.
     */
    @Test
    public void testNodes10() {
        Graph createdGraph = createGraph("Nodes_10_Random.dot");
        State optimalState = new DFS(1, createdGraph).runAlgorithm();
        Assert.assertEquals(optimalState.getCurrentCost(), 63);
        Assert.assertTrue(optimalState.isValid());
    }

    /**
     * This tests if the output is correct given a graph with large communication costs on a processor.
     */
    @Test
    public void testNodes9() {
        Graph createdGraph = createGraph("Nodes_9_SeriesParallel.dot");
        State optimalState = new DFS(1, createdGraph).runAlgorithm();
        Assert.assertEquals(optimalState.getCurrentCost(), 55);
        Assert.assertTrue(optimalState.isValid());
    }

    /**
     * This tests if the output is correct given a graph with a set path on a processor.
     */
    @Test
    public void testNodes7() {
        Graph createdGraph = createGraph("Nodes_7_OutTree.dot");
        State optimalState = new DFS(1, createdGraph).runAlgorithm();
        Assert.assertEquals(optimalState.getCurrentCost(), 40);
        Assert.assertTrue(optimalState.isValid());
    }
}
