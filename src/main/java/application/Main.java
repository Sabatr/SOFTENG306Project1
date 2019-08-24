package application;

import algorithm.Algorithm;
import algorithm.AlgorithmChoice;
import algorithm.AlgorithmFactory;
import files.DotParser;
import graph.Graph;
import files.OutputCreator;
import javafx.concurrent.Task;
import visualisation.Visualiser;

import java.io.File;
import java.io.FileNotFoundException;

import static utils.CliParser.UI;

public class Main {
    /**
     * @param args
     * Input values when executing the program in console
     */
    public static void main(String[] args) {

        UI(args);
    }

}
