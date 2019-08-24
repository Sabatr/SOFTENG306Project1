package utils;
import algorithm.*;
import application.Main;
import files.DotParser;
import files.OutputCreator;
import graph.Graph;
import javafx.concurrent.Task;
import org.antlr.v4.runtime.misc.NotNull;
import org.apache.commons.cli.*;
import visualisation.Visualiser;
import visualisation.processor.listeners.ObservableAlgorithm;

import java.io.File;
import java.io.FileNotFoundException;

import static utils.HelpFunctions.*;

//TODO: Get rid of unnecessary static. Just make it a singleton.
public class CliParser {

    private static final String DEFAULT_OUTPUT = "output.dot";
    private static final String DEFAULT_CORES = "1";
    private static final String DEFAULT_VISUALIZE = "false";
    private static final String OUTPUT = "o";
    private static final String CORES = "p";
    private static final String VISUALIZE = "v";
    private static Algorithm algorithm;
    private static String outputName;
    private static boolean isVisualisation;
    public static void UI(String[] args) {
        args = new String[3];
        args[0] = "Nodes_11_OutTree.dot";
        args[1] = "2";
        args[2] = "-v";
        if (args.length == 0) { System.err.println("Error: No arguments provided. Program terminated. Run program with '-h' for help."); }
        else {
            if(!args[0].equals("-h")) {
                System.out.println("------ SOFTENG 306 : Project 1 ------");
                System.out.println("-------------------------------------");
                System.out.println("Please run program with parameter " +
                        "'-h'\nto display help message\n");
            }
            handleInput(args);
        }
    }

    public static boolean isVisualisation() {
        return isVisualisation;
    }

    private static void handleInput(String[] args) {
        if (args[0].equals("-h")) { printHelp(); } // Checks for help command
        else {
            String[] result = cliParser(args); // result[0]: file path, result[1]: num. processors, result[2]: output file, result[3]: num. cores, result[4]: visualize
            if (result != null) {
                try { // This is where the calculation is done
                    System.out.println("Calculating, please wait...\n");
                      AlgorithmFactory factory = new AlgorithmFactory();
                     DotParser.getInstance().parseGraph(new File("data/" + result[0]));
                     Graph g1 = DotParser.getInstance().getGraph();
                    algorithm = factory.createAlgorithm(AlgorithmChoice.DFS_PARALLEL,args,g1);
                    outputName = result[2];
                    if (Boolean.parseBoolean(result[4])) {
                        isVisualisation = true;
                        startVisualisation(args);
                    } else {
                       // createSolution();
                        isVisualisation = false;
                        createOutputFile();
                    }
                  //  OutputCreator out = new OutputCreator(new AStar(Integer.parseInt(result[1]),g1).runAlgorithm());
                   // out.createOutputFile(result[2]);

                   // if (Boolean.parseBoolean(result[4])) out.displayOutputOnConsole();
                } catch (FileNotFoundException e) { // If the file is not found, the error will be caught here
                    System.err.println("Error: The file was not found. Run program with '-h' parameter for help");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String[] cliParser(@NotNull String[] args) {
        String[] result = new String[5];

        Options options = createOptions(); //Adding option values, e.g. -a -f -g etc., which will be parsed

        // parser is used for the parsing of the input, here args
        CommandLineParser parser = new DefaultParser();

        // Default values for result returned
        result[2] = DEFAULT_OUTPUT;
        result[3] = DEFAULT_CORES;
        result[4] = DEFAULT_VISUALIZE;

        // Mandatory options
        if (args.length > 1) { // If both file path and number of processors are entered
            if (args[0].endsWith(".dot")) {
                result[0] = args[0]; // File path
                result[2] = createOutputFileName(result[0], DEFAULT_OUTPUT);
            } else {
                System.err.println("Error: Invalid file path ending, needs to be a '.dot' file. Run program with '-h' for help.");
                result = null;
            }

            if (result == null) { /* do nothing */ }
            else if (isStringNumericAndPositive(args[1])) result[1] = args[1]; // Number of processors
            else {
                System.err.println("Error: Invalid value for number of processors. Run program with '-h' for help.");
                result = null;
            }
        } else { // If no arguments are provided
            System.err.println("Error: Missing mandatory argument file path and/or number of processors. Run program with '-h' for help.");
            result = null;
        }
        // Optional options
        if (result != null) {
            try {
                CommandLine cmd = parser.parse(options, args);
                // Check for too many arguments
                if (checkForExtraArguments(cmd)) throw new IllegalArgumentException("Too many arguments. Didn't expect: " + cmd.getArgList().subList(2, cmd.getArgList().size()));

                // This approach can be followed for Options with values
                if (cmd.hasOption("p")) { // Handles -p (number of cores) option
                    if (isStringNumericAndPositive(cmd.getOptionValue("p"))) {
                        result[3] = cmd.getOptionValue("p");
                    }
                    else {
                        throw new IllegalArgumentException("Option -p (" + cmd.getOptionValue("p") + ") invalid. Needs to be a positive int value");
                    }
                } else {
                    System.out.println("Option -p not present, default value '" + DEFAULT_CORES + "' chosen");
                }

                if (cmd.hasOption("o")) { // Handles -o (output file name) option
                    if (cmd.getOptionValue("o").endsWith(".dot")) {
                        result[2] = cmd.getOptionValue("o");
                    }
                    else {
                        throw new IllegalArgumentException("Option -o (" + cmd.getOptionValue("o") + ") invalid. Needs to be a '.dot' file");
                    }
                } else {
                    System.out.println("Option -o not present, default '" + result[2] + "' chosen");
                }

                //This approach can be followed for Options without values (flags)
                if (cmd.hasOption("v")) { // Handles -v flag (visualization) option
                    result[4] = "true";
                }
                else {
                    System.out.println("Option -v not present, default value '" + DEFAULT_VISUALIZE + "' chosen");
                }

            } catch (Exception e) { // Will be thrown if no value is provided
                System.err.println("Error: " + e.getMessage());
                new HelpFormatter().printHelp("Optional arguments", options);
                result = null;
            }
        }
        if (result == null) { // Some input error occurred and the program calculation should not be completed
            System.err.println("\nProgram terminated. See error above.");
        }
        return result;
    }


    private static void printHelp() {
        System.out.println("--------------- Help ---------------");
        System.out.println("Java -jar scheduler.jar INPUT.dot P [OPTION]");
        System.out.println("INPUT.dot	\t a task graph with integer weights in .dot format");
        System.out.println("P\t\t number of processors to schedule the INPUT graph on");

        System.out.println("Optional:");
        System.out.println("-p N\t use N cores for execution in parallel. Default is sequential");
        System.out.println("-v\t\t visualise the search");
        System.out.println("-o OUTPUT\t output file is named OUTPUT (default is INPUT-output.dot)");
        System.out.println();
        System.out.println("Run program with '-h' to show this help menu");
        System.out.println();
    }

    private static boolean checkForExtraArguments(CommandLine cmd) {
        return (cmd.getArgList().size() > 2);
    }

    private static Options createOptions() {
        Options options = new Options();

        options.addOption(getCoreOption())
                .addOption(getVisualsOption())
                .addOption(getOutputOption());

        return options;
    }

    private static Option getCoreOption() {
        return Option.builder(CORES)
                .required(false)
                .hasArg()
                .numberOfArgs(1)
                .argName("Number of cores")
                .desc("Number of cores to run computation on")
                .build();
    }

    private static Option getVisualsOption() {
        return Option.builder(VISUALIZE)
                .required(false)
                .hasArg(false)
                .desc("Flag for if visualization should be run")
                .build();
    }

    private static Option getOutputOption() {
        return Option.builder(OUTPUT)
                .required(false)
                .hasArg()
                .numberOfArgs(1)
                .argName("Output file")
                .desc("File path for output file. Must be a '.dot' file")
                .build();
    }



    public static void startVisualisation(String[] args) {
        new Visualiser().startVisual(args);
    }


    public static void createSolution() {
        ((AlgorithmHandler)algorithm).startTimer();
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                System.out.println("Output file available as: '" + outputName + "'");
                createOutputFile();;
                return null;
            }
        };
        new Thread(task).start();
    }

    private static void createOutputFile() {
        scheduler.State solution = algorithm.runAlgorithm();
        ((AlgorithmHandler)algorithm).fireEvent(AlgorithmEvents.ALGORITHM_FINISHED,solution);
        OutputCreator out = new OutputCreator(solution);
        out.createOutputFile(outputName);
    }
}

