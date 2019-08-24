import application.Main;
import org.junit.Test;

public class testMain {
    @Test
    public void testRandomParameter() {
        Main.main("data/input.dot 4 z".split(" "));
    }

    @Test
    public void testMissingParameter() {
        Main.main("data/input.dot".split(" "));
    }

    @Test
    public void testInvalidNumber() {
        Main.main("data/input.dot a".split(" "));
    }

    @Test
    public void test1core() {
        Main.main("data/input.dot 1".split(" "));
    }

    @Test
    public void testInvalidInput() {
        Main.main("data/inputnoExist.dot 4".split(" "));
    }

    @Test
    public void testMultipleCores() {
        Main.main("data/input.dot 4".split(" "));
    }


    @Test
    public void testOutput() {
        Main.main("data/input.dot 4 -v -o 2.dot ".split(" "));
    }
    @Test
    public void testTwoCore() {
        Main.main("data/input.dot 4 -p 2".split(" "));
    }
}
