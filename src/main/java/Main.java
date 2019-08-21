import files.DotParser;
import algorithm.AStar;
import graph.Graph;
import files.OutputCreator;
import org.antlr.v4.runtime.misc.NotNull;
import org.apache.commons.cli.*;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {

    private static boolean isStringNumericAndPositive(String str) {
        try {
            if (Integer.parseInt(str) > 0) return true;
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return false;
    }

    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().startsWith("windows");
    }

    private static void printHelp() {
        System.out.println("--------------- Help ---------------");
        System.out.println("Java -jar schdeuler.jar INPUT.dot P [OPTION]");
        System.out.println("INPUT.dot	\t a task graph with integer weigh;ts in dot format.");
        System.out.println("P\t\t number of processors to schedule the INPUT graph on");

        System.out.println("Optional:");
        System.out.println("-p N\t use N cores for execution in paralleldefulat is sequential)(not yet implemented)");
        System.out.println("-v\t\t visualise the serach");
        System.out.println("-o OUTPUT\t output file is named OUTPUT (defsult is INPUT-output.dot)");
        System.out.println();
        System.out.println("Run program with '-h' to show this help menu");
        System.out.println();
    }

    private static void UI(String[] args) {
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

    private static void handleInput(String[] args) {
        if (args[0].equals("-h")) { printHelp(); } // Checks for help command
        else {
            String[] result = cliParser(args); // result[0]: file path, result[1]: num. processors, result[2]: output file, result[3]: num. cores, result[4]: visualize
            if (result != null) {
                try { // This is where the calculation is done
                    System.out.println("Calculating, please wait...\n");
                    Graph g1 = new DotParser(new File(result[0])).parseGraph();
                    OutputCreator out = new OutputCreator(new AStar(Integer.parseInt(result[1]),g1).runAlgorithm());
                    out.createOutputFile(result[2]);
                    System.out.println("Output file available as: '" + result[2] + "'");
                    if (Boolean.parseBoolean(result[4])) out.displayOutputOnConsole();
                } catch (FileNotFoundException e) { // If the file is not found, the error will be caught here
                    System.err.println("Error: The file was not found. Run program with '-h' parameter for help");
                }
            }
        }
    }

    private static String[] cliParser(@NotNull String[] args) {
        String[] result = new String[5];

        Options options = new Options(); //Adding option values, e.g. -a -f -g etc., which will be parsed
        options.addOption("p", true, "Number of cores");
        options.addOption("v", false, "Visualise the search");
        options.addOption("o", true, "Choose output file name");

        // parser is used for the parsing of the input, here args
        CommandLineParser parser = new DefaultParser();

        // Default values for parser
        String defaultOutput = "output.dot";
        String defaultCores = "1";
        String defaultVisualize = "false";
        result[2] = defaultOutput;
        result[3] = defaultCores;
        result[4] = defaultVisualize;

        // Mandatory options
        if (args.length > 1) { // If both file path and number of processors are entered
            result[0] = args[0]; // File path
            if (result[0].endsWith(".dot")) {
                if (result[0].contains("/") || result[0].contains("\\")) { // This removes the folder part of the file path
                    if (!isWindows()) {
                        defaultOutput = result[0].substring(result[0].lastIndexOf('/') + 1, result[0].lastIndexOf('.')) + "-" + defaultOutput;
                    } else {
                        defaultOutput = result[0].substring(result[0].lastIndexOf('\\') + 1, result[0].lastIndexOf('.')) + "-" + defaultOutput;
                    }
                } else {
                    defaultOutput = result[0].substring(0, result[0].lastIndexOf('.')) + "-" + defaultOutput;
                }
                result[2] = defaultOutput;
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

                //This approach can be followed for Options with values
                if (cmd.hasOption("p")) { // handles -p (number of cores) option
                    if (isStringNumericAndPositive(cmd.getOptionValue("p"))) {
                        result[3] = cmd.getOptionValue("p");
                    }
                    else {
                        throw new IllegalArgumentException("Option -p (" + cmd.getOptionValue("p") + ") invalid. Needs to be a positive int value");
                    }
                } else {
                    System.out.println("Option -p not present, default value \"" + defaultCores + "\" chosen");
                }

                if (cmd.hasOption("o")) { // handles -o (output file name) option
                    if (cmd.getOptionValue("o").endsWith(".dot")) {
                        result[2] = cmd.getOptionValue("o");
                    }
                    else {
                        throw new IllegalArgumentException("Option -o (" + cmd.getOptionValue("o") + ") invalid. Needs to be a '.dot' file");
                    }
                } else {
                    System.out.println("Option -o not present, default \"" + defaultOutput + "\" chosen");
                }

                //This approach can be followed for Options without values (flags)
                if (cmd.hasOption("v")) { // handles -v flag (visualization) option
                    result[4] = "true";
                }
                else {
                    System.out.println("Option -v not present, default value \"" + defaultVisualize + "\" chosen");
                }

            } catch (Exception e) { //Will be thrown if no value is provided
                System.err.print("Error: " + e.getMessage());
                result = null;
            }
            System.out.println();
        }
        if (result == null) {
            System.err.println("\nProgram terminated. See error above.");
        }
        return result;
    }

    /**
     * @param args
     * Input values when executing the program in console
     */
    public static void main(String[] args) {
        UI(args);
    }
}

