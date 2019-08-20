package application;

import algorithm.Algorithm;
import algorithm.AlgorithmChoice;
import algorithm.AlgorithmFactory;
import files.DotParser;
import graph.Graph;
import files.OutputCreator;
import javafx.concurrent.Task;
import org.apache.commons.cli.*;
import scheduler.State;
import visualisation.Visualiser;

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

    /**
     * Help Menu
     */
    public static void printHelp() {
        System.out.println("Java -jar scheduler.jar INPUT.dot P [OPTION]");
        System.out.println("INPUT.dot\ta task graph with integer weights in dot format.");
        System.out.println("P\t\tnumber of processors to schedule the INPUT graph on");

        System.out.println("\nOptional:");
        System.out.println("-p N\t\tuse N cores for execution in parallel default is sequential (not yet implemented)");
        System.out.println("-v\t\tvisualise the search (not yet implemented)");
        System.out.println("-o OUTPUT\toutput file is named OUTPUT (default is INPUT-output.dot)");
        System.out.println();
        System.out.println("Type \"-h\" to show this help menu");
        System.out.println();
    }

    public static void main(String[] args) {

        //TODO: Delete later
        args = new String[3];
        args[0] = "data/input.dot";
        args[1] = "2";
        args[2] = "-v";
        if (args.length == 0) {
            System.err.println("No arguments found, please try again. Please use the flag -h for help");
            System.exit(1);
        } else if (args[0].equals("-h")) {
            printHelp();
        } // Checks for help command
        else {
            // Default values for parser
            String defaultOutput = "output.dot";
            int defaultCores = 1;
            boolean defaultVisualize = true;

            if (args.length > 0) {
                File f = new File(args[0]);
                if (!f.exists()) {
                    System.err.println("The file was not found. Please check your inputs again. Type -h for help");
                    System.exit(1);
                }
            }

            // Mandatory options
            if (args.length > 1) { // If both file path and number of processors are entered
                if (args[0].endsWith(".dot")) {
                    defaultOutput = getFileName(args[0]) + "-" + defaultOutput;
                } else {
                    System.err.println("Invalid file path ending, needs to be a \".dot\" file. Please type your " +
                            "inputs " +
                            "again. Type -h for help.");
                    System.exit(1);
                }

                if (isStringIsNumericAndPositive(args[1])) {
                    //defaultCores = Integer.valueOf(args[1]);
                } else {
                    System.err.println("Invalid value for number of processors, please type your inputs again. Type " +
                            "-h for help.");
                    System.exit(1);
                }

            } else { // If no arguments are provided
                System.err.println("Missing mandatory argument file path and/or number of processors. Type -h for " +
                        "help.");
                System.exit(1);
            }
            for (int i = 2; i < args.length; i++) {
                String cmd = args[i];
                String value = "";
                //This approach can be followed for Options with values
                if (cmd.equals("-p")) {
                    if (isStringIsNumericAndPositive(value)) {
                        checkForValue(i, args, cmd);
                    }
                    i++;
                    System.out.println("-p is currently unavailable as this program has not been implemented to run on" +
                            " multiple cores.\n The program will run sequentially");
                } else if (cmd.equals("-o")) {
                    defaultOutput = checkForValue(i, args, cmd);
                    i++;

                } else if (cmd.equals("-v")) {
                    defaultVisualize = true;
                    System.out.println("-v is currently unavailable as the visualisation of the search has not been implemented " +
                            "yet. The result will be shown in this command line." );
                } // handles -v flag (visualization) option
                else {
                    System.err.println("Unknown optional parameter " + cmd);
                    System.err.println("Please type -h for help");
                    System.exit(1);
                }
            }
            System.out.println("The graph will be stored as " + defaultOutput);
            System.out.println("This will be processed on one thread as it has not been implemented yet.");
            System.out.println();
            System.out.println("Please wait, it may take a while to return result.");
            System.out.println();


            try { // This is where the calculation is done
                Graph g1 = new DotParser(new File(args[0])).parseGraph();
                algorithm = new AlgorithmFactory().createAlgorithm(AlgorithmChoice.DFS,args,g1);
                outputName = defaultOutput;
                if (defaultVisualize) {
                    new Visualiser().startVisual(args);
                } else {
                    createSolution();
                }
            } catch (FileNotFoundException e) { // If the file is not found, the error will be caught here
                System.err.println("The file was not found. Please check your inputs again. Type -h for help");
            } catch (Exception e) {
                System.err.println("Sorry, something went wrong.");
            }
        }
    }

    /**
     * Grabs the filename without extension.
     */
    private static String getFileName(String path) {
        File f = new File(path);

        String fileNameWithOutExt = f.getName().replaceFirst("[.][^.]+$", "");

        return fileNameWithOutExt;
    }

    /**
     * Checks if a value has been provided for a parameter
     */
//    public static void main(String[] args) {
//        String[] result = cliParser(args); // result[0]: file path, result[1]: num. processors, result[2]: output, result[3]: num. cores, result[4]: visualise
//        // result[] vil be an array of Strings, remember to parse value to correct type
//        try {
//            Graph g1 = new DotParser(new File(result[0])).parseGraph();
//            algorithm = new AlgorithmFactory().createAlgorithm(AlgorithmChoice.ASTAR,result,g1);
//           // outputName = args[2];
//            outputName = "Test.dot";
//            if (Boolean.parseBoolean(result[4]))  {
//                //TODO: Currently, it creates the visual AFTER the algorithm is finished.
//                //TODO: A better method would be to update the visual DURING the algorithm.
//
//                // Once the application loads, all of the proceeding commands don't occur
//                new Visualiser().startVisual(args);
//            } else {
//
//                createSolution();
//            }
//
//           // out.displayOutputOnConsole();
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    public static String checkForValue(int i, String[] args, String cmd) {
        if (i + 1 < args.length) {
            return args[i + 1];
        } else {
            System.err.println("Missing value for parameter " + cmd);
            System.err.println("Please type -h for help");
            System.exit(1);
        }

        return null;
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

