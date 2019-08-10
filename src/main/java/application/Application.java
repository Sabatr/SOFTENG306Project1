package application;

import algorhithm.AStar;
import files.DotParser;
import files.OutputCreator;
import graph.Graph;
import org.apache.commons.cli.*;
import scheduler.State;
import visualisation.Visualiser;
import visualisation.AlgorithmListener;
import visualisation.processor.listeners.SchedulerListener;

import java.io.File;
import java.io.FileNotFoundException;

public class Application {
    private static Application application;
    private SchedulerListener listener;
    private static AStar astar;
    public static Application getInstance() {
        if (application == null) {
            application = new Application();
        }
        return application;
    }
    public static boolean isStringIsNumericAndPositive(String str) {
        try {
            if (Integer.parseInt(str) > 0) return true;
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return false;
    }

    private static String[] cliParser(String[] args) {

        String[] result = new String[5];

        Options options = new Options(); //Adding option values, e.g. -a -f -g etc., which will be parsed
        options.addOption("p", true, "Number of cores");
        options.addOption("v", false, "Visualise the search");
        options.addOption("o", true, "Choose output file name");

        // parser is used for the parsing of the input, here args
        CommandLineParser parser = new DefaultParser();

        // Default values for parser
        String defaultFile = "data/input.dot";
        String defaultProcessors = "2";
        String defaultOutput = "output.dot";
        String defaultCores = "1";
        String defaultVisulize = "false";
        result[0] = defaultFile;
        result[1] = defaultProcessors;
        result[2] = defaultOutput;
        result[3] = defaultCores;
        result[4] = defaultVisulize;

        // Mandatory options
        if (args.length > 1) {
            result[0] = args[0]; // File path
            if (isStringIsNumericAndPositive(args[1])) result[1] = args[1]; // Number of processors
            else { System.err.println("Invalid value for number of processors, default value \"" + defaultProcessors + "\" chosen");}

        } else {
            System.out.println("Options for file path and number of processors missing. Default values (File path: \"" + defaultFile + "\", Num. processors: \"" +
                    defaultProcessors + ") chosen");
        }

        // Optional options
        try {
            CommandLine cmd = parser.parse(options, args);

            //This approach can be followed for Options with values
            if(cmd.hasOption("p")) {
                if (isStringIsNumericAndPositive(cmd.getOptionValue("P"))) { result[3] = cmd.getOptionValue("P"); } // handles -p (number of cores) option
            } else { System.out.println("Option -p not present or invalid, default value \"" + defaultCores + "\" chosen"); }

            if(cmd.hasOption("o")) { result[2] =  cmd.getOptionValue("o"); } // handles -o (output file name) option
            else { System.out.println("Option -o not present, default \"" + defaultOutput + "\" chosen"); }

            //This approach can be followed for Options without values (flags)
            if(cmd.hasOption("v")) { result[4] = "true"; } // handles -v flag (visualization) option
            else { System.out.println("Option -v not present, default value \"" + defaultVisulize + "\" chosen"); }

        } catch (ParseException e) { //Will be thrown if no value is provided
            System.err.println(e);
            System.out.println("Default values (Num. cores: \"" + defaultCores + "\", visualise: \"" +
                    defaultVisulize + ", output file: \"" + defaultOutput + "\") chosen");
        }

        return result;
    }

    /**
     * TODO: Refactor all of these statements in the main. We want main to be quite short.
     * @param args
     */
    public static void main(String[] args) {
        //  System.out.println(g);

        String[] result = cliParser(args); // result[0]: file path, result[1]: num. processors, result[2]: output, result[3]: num. cores, result[4]: visualise
        // result[] vil be an array of Strings, remember to parse value to correct type

        DotParser dp = new DotParser(new File(result[0]));

        Graph g1 = null;
        try {
            g1 = dp.parseGraph();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        astar = new AStar(Integer.parseInt(result[1]),g1);
        AlgorithmListener listener = new AlgorithmListener();
        listener.setFileName(result[0]);
        listener.setNumberOfProcessors(Integer.parseInt(result[1]));
        astar.addListener(listener);
        State solution = astar.runAlgorithm();
        new Visualiser(listener).startVisual(args);


        //State solution = new DFS(2,g1).runDFS();


        System.out.println(solution);
        OutputCreator out = new OutputCreator(solution);
        out.displayOutputOnConsole();
    }

    public static void addListener(SchedulerListener listener ) {
        astar.addListener(listener);
    }
}
