package visualisation.processor.helpers;

import javafx.collections.FXCollections;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import scheduler.ProcessorBlock;
import scheduler.State;
import visualisation.processor.ProcessChart;
import visualisation.AlgorithmDataStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This is a helper class to help build and extract the data needed for the process chart
 */
public class ProcessChartHelper {
    private final NumberAxis X_AXIS = new NumberAxis();
    private final CategoryAxis Y_AXIS = new CategoryAxis();
    private final String Y_AXIS_NAME = "Processor ";
    private final String X_AXIS_LABEL = "Time";
    private final String CHART_NAME = "Process Chart";
    private final String PROCESS_CHART_STYLESHEET = "visualisation/visualisationassets/ProcessChart.css";
    private final int X_AXIS_MINOR_TICK_COUNT = 5;
    private final int Y_AXIS_LABEL_GAP = 20;
    private final int Y_AXIS_BLOCK_HEIGHT = 350; //Larger the value the smaller the block heights
    private ProcessChart<Number,String> chart;
    private HashMap<Integer,XYChart.Series> seriesMap;
    private Pane processPane;
    private int numberOfProcessors;

    public ProcessChartHelper(Pane processPane) {
        seriesMap =  new HashMap();
        chart = new ProcessChart<>(X_AXIS, Y_AXIS);
        numberOfProcessors = AlgorithmDataStorage.getInstance().getNumberOfProcessors();
        this.processPane = processPane;
        setUpInitialData();
        setData();

    }

    private void clear(){
        chart.getData().forEach(series->{
            series.getData().forEach(item-> {
                chart.dataItemRemoved(item,series);
            });
        });
    }
    /**
     * Retrieves the data set by the algorithm and adds it to the chart
     * TODO: Make it so that theres a loading screen before the algo finishes
     */
    private void setData() {
        clear();
        State finalState = AlgorithmDataStorage.getInstance().getState();
        XYChart.Series series1 = new XYChart.Series();
        for (int i : seriesMap.keySet()) {
            List<ProcessorBlock> processBlocks = finalState.getProcessors().get(i).getProcessorBlockList();
            for (ProcessorBlock block : processBlocks) {
                getRandomColour();
                series1.getData().add(new XYChart.Data(block.getStartTime(),
                        Y_AXIS_NAME +(i+1),
                        new ChartData(block.getEndTime() - block.getStartTime(),getRandomColour(),block.getV().getId())
                ));
            }
        }
       chart.getData().add(series1);;
    }

    private String getRandomColour() {
        int rand = (int)Math.round(Math.random() * 4);
        switch (rand) {
            case 0:
                return "status-blue";
            case 1:
                return "status-red";
            case 2:
                return "status-green";
            default:
                return "status-yellow";
        }
    }
    private void setUpInitialData() {
        seriesMap.keySet().forEach(key-> chart.getData().add(seriesMap.get(key)));
        initialiseXAxis();
        initialiseYAxis();
        initialiseSettings();
    }
    /**
     * Retrieves the process chart that has been created by the helper
     * @return
     */
    public ProcessChart getProcessChart() {
        return chart;
    }

    /**
     * Sets the labels for the xaxis
     */
    private void initialiseXAxis() {
        X_AXIS.setLabel(X_AXIS_LABEL);
        X_AXIS.setMinorTickCount(X_AXIS_MINOR_TICK_COUNT);
    }

    /**
     * Sets the labels for the y axis
     * Also sets the height each processor uses
     */
    private void initialiseYAxis() {
        Y_AXIS.setTickLabelGap(Y_AXIS_LABEL_GAP);
        // The y category will just be the number of processors
        List<String> processors = new ArrayList<>();

        for(int processorNo = 0; processorNo < numberOfProcessors; processorNo++){
            processors.add(Y_AXIS_NAME + (processorNo+1));
            //Maps the processor to possible series
            seriesMap.put(processorNo,new XYChart.Series());
        }
        chart.setProcessorHeight((processPane.getPrefHeight()-Y_AXIS_BLOCK_HEIGHT)/numberOfProcessors);
        Y_AXIS.setCategories(FXCollections.observableArrayList(processors));

    }
    /**
     * Initialises the basic settings for the process chart
     */
    private void initialiseSettings() {
        chart.setTitle(CHART_NAME);
        chart.setLegendVisible(false);
        chart.setPrefHeight(processPane.getPrefHeight());
        chart.setPrefWidth(processPane.getPrefWidth());
        chart.getStylesheets().add(PROCESS_CHART_STYLESHEET);
    }
}
