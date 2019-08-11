package application;

import algorithm.Algorithm;
import algorithm.AlgorithmChoice;
import algorithm.AlgorithmFactory;
import files.DotParser;
import algorithm.AStar;
import graph.Graph;
import files.OutputCreator;
import javafx.concurrent.Task;
import org.apache.commons.cli.*;
import scheduler.State;
import visualisation.AlgorithmListener;
import visualisation.Visualiser;
import visualisation.processor.listeners.SchedulerListener;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    private static Algorithm algorithm;
    private static String outputName;
    public static boolean isStringIsNumericAndPositive(String str) {
        try {
            if (Integer.parseInt(str) > 0) return true;
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return false;
    }

    private static String getFileName(String path){
        File f= new File(path);

        String fileNameWithOutExt = f.getName().replaceFirst("[.][^.]+$", "");

        return fileNameWithOutExt;
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
        String defaultFile = "data/Nodes_11_OutTree.dot";
        String defaultProcessors = "4";
        String defaultOutput = "output.dot";
        String defaultCores = "1";
        String defaultVisualize = "true";
        result[0] = defaultFile;
        result[1] = defaultProcessors;
        result[2] = defaultOutput;
        result[3] = defaultCores;
        result[4] = defaultVisualize;

        // Mandatory options
        if (args.length > 1) { // If both file path and number of processors are entered
            result[0] = args[0]; // File path
            result[2] = getFileName(args[0]) + "-" + defaultOutput;
            if (isStringIsNumericAndPositive(args[1])) result[1] = args[1]; // Number of processors
            else { System.err.println("Invalid value for number of processors, default value \"" + defaultProcessors + "\" chosen");}

        } else if (args.length == 1) { // If only file path is entered
            result[0] = args[0]; // File path
            result[2] = getFileName(args[0])  + "-" + defaultOutput;
            System.out.println("Options for number of processors missing. Default value \"" + defaultProcessors + "\" chosen");
        } else { // If no arguments are provided
           // throw new IllegalArgumentException("Missing mandatory argument: file path");
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
            else { System.out.println("Option -v not present, default value \"" + defaultVisualize + "\" chosen"); }

        } catch (ParseException e) { //Will be thrown if no value is provided
            System.err.println(e);
            System.out.println("Default values (Num. cores: \"" + defaultCores + "\", visualise: \"" +
                    defaultVisualize + ", output file: \"" + defaultOutput + "\") chosen");
        }

        return result;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        String[] result = cliParser(args); // result[0]: file path, result[1]: num. processors, result[2]: output, result[3]: num. cores, result[4]: visualise
        // result[] vil be an array of Strings, remember to parse value to correct type
        try {
            Graph g1 = new DotParser(new File(result[0])).parseGraph();
            algorithm = new AlgorithmFactory().createAlgorithm(AlgorithmChoice.ASTAR,result,g1);
           // outputName = args[2];
            outputName = "Test.dot";
            if (Boolean.parseBoolean(result[4]))  {
                //TODO: Currently, it creates the visual AFTER the algorithm is finished.
                //TODO: A better method would be to update the visual DURING the algorithm.

                // Once the application loads, all of the proceeding commands don't occur
                new Visualiser().startVisual(args);
            } else {

                createSolution();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createSolution() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                scheduler.State solution = algorithm.runAlgorithm();
                OutputCreator out = new OutputCreator(solution);
                out.createOutputFile(outputName);
                return null;
            }
        };
        new Thread(task).start();

    }
}

