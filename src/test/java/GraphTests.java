//
//import algorithm.AStar;
//import files.DotParser;
//import graph.Graph;
//import org.junit.Assert;
//import org.junit.Test;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//
//public class GraphTests {
//
//    /**
//     * This method is helps test different input files.
//     * @param inputURL
//     */
//    private Graph createGraph(String inputURL) {
//        try {
//            DotParser.getInstance().parseGraph(new File("data/" + inputURL));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        Graph g1 = DotParser.getInstance().getGraph();
//        return g1;
//    }
//
//
//    /**
//     * This tests if the calculation of the bottom level is correct with the default graph
//     */
//    @Test
//    public void testCalculationOfBottomLevel() {
//        Graph createdGraph = createGraph("input.dot");
//        Assert.assertEquals(7,createdGraph.calculateBottomLevel());
//    }
//
//    /**
//     * This tests if the calculation of the bottom level is correct for a large graph
//     */
//    @Test
//    public void testCalculationOfBottomLevelForSecondary() {
//        Graph createdGraph = createGraph("input2.dot");
//        Assert.assertEquals(14,createdGraph.calculateBottomLevel());
//    }
//
//    /**
//     * This tests if the calculation of the bottom level is correct for a linear graph
//     */
//    @Test
//    public void testCalculationOfBottomLevelForLinearGraph() {
//        Graph createdGraph = createGraph("input3.dot");
//        Assert.assertEquals(10,createdGraph.calculateBottomLevel());
//    }
//
//
//    /**
//     * This tests if the calculation of the bottom level is correct for a graph with a single node
//     */
//    @Test
//    public void testCalculationOfBottomLevelForSingleNode() {
//        Graph createdGraph = createGraph("singleNode.dot");
//        Assert.assertEquals(2,createdGraph.calculateBottomLevel());
//    }
//
//    /**
//     *  This tests if the algorithm can handle inputs with multiple roots and exits
//     */
//    @Test
//    public void testGraphWithMultipleEntriesAndExits(){
//        Graph createdGraph = createGraph("input4.dot");
//        Assert.assertEquals(15, new AStar(2, createdGraph).runAlgorithm().getCurrentCost());
//    }
//}
