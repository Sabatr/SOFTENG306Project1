import files.DotParser;
import algorithm.AStar;
import graph.Graph;
import files.OutputCreator;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static boolean isStringIsNumericAndPositive(String str) {
        try {
            if (Integer.parseInt(str) > 0) return true;
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return false;
    }

    public static boolean isWindows() {
        if (System.getProperty("os.name").toLowerCase().startsWith("windows")) return true;
        return false;
    }

    public static void printHelp() {
        System.out.println("Java -jar schdeuler.jar INPUT.dot P [OPTION]");
        System.out.println("INPUT.dot	\t a task graph with integer weigh;ts in dot format.");
        System.out.println("P\t\t number of processors to schedule the INPUT graph on");

        System.out.println("Optional:");
        System.out.println("-p N\t use N cores for execution in paralleldefulat is sequential)(not yet implemented)");
        System.out.println("-v\t\t visualise the serach");
        System.out.println("-o OUTPUT\t output file is named OUTPUT (defsult is INPUT-output.dot)");
        System.out.println();
        System.out.println("Type \"-h\" to show this help menu and \"-q\" to quit");
        System.out.println();
    }

    public static void UI(String[] args) {
        System.out.println("-- SOFTENG 306 : Project 1 --");
        System.out.println("-----------------------------");

        Scanner sc = new Scanner(System.in);
        if (args.length > 0) {
            System.out.println("Obs: Run without parameters to enter continuous mode\n");
            handleInput(args);
        } else {
            System.out.println("\nType \"-h\" for help and \"-q\" to quit\n");
            while (sc.hasNextLine()) { // Reads inputs from console
                String input = sc.nextLine();
                String[] inputArray = input.split(" ");
                if (!handleInput(inputArray)) {
                    break;
                }
            }
        }
    }

    public static boolean handleInput(String[] args) {
        if (args[0].equals("-q")) { System.out.println("Exiting programme..."); return false; } // Checks for quit command
        else if (args[0].equals("-h")) { printHelp(); } // Checks for help command
        else {
            String[] result = cliParser(args); // result[0]: file path, result[1]: num. processors, result[2]: output file, result[3]: num. cores, result[4]: visualize
            if (result != null) {
                try { // This is where the calculation is done
                    System.out.println("Calculating, please wait…\n");
                    Graph g1 = new DotParser(new File(result[0])).parseGraph();

                    long start = System.nanoTime();

                    OutputCreator out = new OutputCreator(new AStar(Integer.parseInt(result[1]),g1).runAlgorithm());

                    long end = System.nanoTime();
                    System.out.println(end-start);


                    out.createOutputFile(result[2]);
                    if (Boolean.parseBoolean(result[4])) out.displayOutputOnConsole();
                } catch (FileNotFoundException e) { // If the file is not found, the error will be caught here
                    System.err.println("The file was not found. Please type your inputs again. Type -h for help");
                }
            }
        }
        return true;
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
            } else { System.err.println("Invalid file path ending, needs to be a \".dot\" file. Please type your inputs again. Type -h for help."); result = null; }

            if (result == null) { /* do nothing */ }
            else if (isStringIsNumericAndPositive(args[1])) result[1] = args[1]; // Number of processors
            else { System.err.println("Invalid value for number of processors, please type your inputs again. Type -h for help."); result = null; }

        } else { // If no arguments are provided
            System.err.println("Missing mandatory argument file path and/or number of processors. Type -h for help.");
            result = null;
        }
        // Optional options
        if (result != null) {
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
            System.out.println();
        }

        return result;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        UI(args);
    }
}

