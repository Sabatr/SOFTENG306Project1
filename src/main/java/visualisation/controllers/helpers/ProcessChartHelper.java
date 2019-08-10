package visualisation.controllers.helpers;


import javafx.collections.FXCollections;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Singleton class for creating a process chart.
 */
public class ProcessChartHelper {
    private final NumberAxis xAxis = new NumberAxis();
    private final CategoryAxis yAxis = new CategoryAxis();
    private final ProcessChart<Number,String> chart = new ProcessChart<>(xAxis,yAxis);
    private HashMap<Integer,XYChart.Series> seriesMap = new HashMap();
    private Pane processPane;
    //TODO: Make this apply to whatever the user inputs
    private int numberOfProcessors = 2;
    public ProcessChartHelper(Pane processPane) {
        this.processPane = processPane;
        initialiseXAxis();
        initialiseYAxis();
        initialiseSettings();

        test();

       // seriesMap.keySet().forEach(key-> chart.getData().add(seriesMap.get(key)));
    }

    private void test() {
        XYChart.Series series1 = new XYChart.Series();
        for (int i : seriesMap.keySet()) {
            series1.getData().add(new XYChart.Data(0,"Processor 1",new ProcessChart.ExtraData(10,"status-red")));
        }
        chart.getData().add(series1);
    }

    public ProcessChart getProcessChart() {
        return chart;
    }

    private void initialiseXAxis() {
        xAxis.setLabel("Time");
        xAxis.setMinorTickCount(4);
    }

    private void initialiseYAxis() {
        yAxis.setTickLabelGap(20);

        // The y category will just be the number of processors
        List<String> processors = new ArrayList<>();

        for(int i = 0; i < numberOfProcessors; i++){
            processors.add("Processor " + (i+1));
            //processors.add(i+"");
            //Maps the processor to possible series
            seriesMap.put(i,new XYChart.Series());
        }
        yAxis.setCategories(FXCollections.observableArrayList(processors));

    }

    private void initialiseSettings() {
        chart.setTitle("Process Table");
        chart.setLegendVisible(false);
        chart.setBlockHeight((processPane.getPrefHeight()-200)/numberOfProcessors);
        chart.setPrefHeight(processPane.getPrefHeight());
        chart.setPrefWidth(processPane.getPrefWidth());
        chart.getStylesheets().add("visualisation/visualisationassets/ProcessChart.css");
     //   chart.getStylesheets().add(getClass().getResource("./ProcessChart.css").toExternalForm());
       // System.out.println(getClass().getResource(""));
    }


}
