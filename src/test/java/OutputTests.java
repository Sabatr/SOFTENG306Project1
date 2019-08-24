import files.DotParser;
import files.OutputCreator;
import graph.Graph;
import org.junit.Assert;
import org.junit.Test;
import scheduler.State;

import java.io.File;
import java.io.FileNotFoundException;

public class OutputTests {


    /**
     * This method is helps test different input files.
     * @param inputURL
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

    /**
     * Tests if the output file is correct.
     * This one is tricky to test as there are multiple correct answers.
     * Assume the output will be the same as what the brief says.
     */
//    @Test
//    public void testOutputFile() {
//        try {
//            DotParser dp = new DotParser(new File("data/" + "input3.dot"));
//            Graph g = dp.parseGraph();
//            State s = new State(1, g);
//            OutputCreator oc = new OutputCreator(s);
//            oc.createOutputFile("output3.dot");
//
//            DotParser dp2 = new DotParser(new File("data/" + "output3.dot"));
//            Graph g1 = dp2.parseGraph();
//            Assert.assertEquals(g, g1);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//    }
}
