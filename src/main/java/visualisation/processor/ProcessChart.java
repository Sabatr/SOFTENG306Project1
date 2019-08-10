package visualisation.processor;

import javafx.beans.NamedArg;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import visualisation.processor.helpers.ChartData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * https://stackoverflow.com/questions/27975898/gantt-chart-from-scratch
 * @param <X>
 * @param <Y>
 */
public class ProcessChart<X,Y> extends XYChart<X,Y> {
    private double processorHeight = 10;

    public ProcessChart(@NamedArg("xAxis") Axis<X> xAxis, @NamedArg("yAxis") Axis<Y> yAxis) {
        super(xAxis,yAxis);
        setData(FXCollections.observableArrayList());
    }

    /**
     * Determines how to plot the tasks.
     */
    @Override
    protected void layoutPlotChildren() {
        for (int index = 0; index < getData().size(); index++) {
            Series<X,Y> series = getData().get(index);
            Iterator<Data<X,Y>> seriesIterator = getDisplayedDataIterator(series);
            while(seriesIterator.hasNext()) {
                Data<X,Y> item = seriesIterator.next();
                double x = getXAxis().getDisplayPosition(item.getXValue());
                double y = getYAxis().getDisplayPosition(item.getYValue());

                //If either value is not present, skip over it.
                if (Double.isNaN(x) || Double.isNaN(y)) {
                    continue;
                }
                Node block = item.getNode();
                Rectangle taskVisual = new Rectangle( getLength( item.getExtraValue()), getProcessorHeight());;
                StackPane region = (StackPane)item.getNode();
                taskVisual.setWidth( getLength( item.getExtraValue()) * ((getXAxis() instanceof NumberAxis) ? Math.abs(((NumberAxis)getXAxis()).getScale()) : 1));
                taskVisual.setHeight(getProcessorHeight() * ((getYAxis() instanceof NumberAxis) ? Math.abs(((NumberAxis)getYAxis()).getScale()) : 1));
                y -= getProcessorHeight() / 2.0;
                setRegionInfo(region,taskVisual);

                block.setLayoutX(x);
                block.setLayoutY(y);
            }
        }
    }

    /**
     *  Basic settings for the task pane
     * @param region
     * @param taskVisual
     */
    private void setRegionInfo(StackPane region,Rectangle taskVisual) {
        region.setShape(null);
        region.setShape(taskVisual);
        region.setScaleShape(false);
        region.setCenterShape(false);
        region.setCacheShape(false);
    }

    /**
     * Retrieve the height for a processor visual
     * @return
     */
    public double getProcessorHeight() {
        return processorHeight;
    }

    /**
     * Sets the processor height visual
     * @param processorHeight
     */
    public void setProcessorHeight(double processorHeight) {
        this.processorHeight = processorHeight;
    }

    @Override
    protected  void seriesAdded(Series<X,Y> series, int seriesIndex) {
        for (int j=0; j<series.getData().size(); j++) {
            Data<X,Y> item = series.getData().get(j);
            Node container = createContainer(item);
            getPlotChildren().add(container);
        }
    }


    /**
     * Creates a container for the data items to sit in
     * @param item
     * @return
     */
    private Node createContainer(Data<X,Y> item) {
        Node container = item.getNode();

        if (container == null) {
            container = new StackPane();
            item.setNode(container);
        }
        container.getStyleClass().add( getStyleClass( item.getExtraValue()));

        return container;
    }

    @Override
    /**
     * Updates the axis range, which depends on the values given for the task
     */
    protected void updateAxisRange() {
        Axis<X> xAxis = getXAxis();
        Axis<Y> yAxis = getYAxis();
        List<X> xData = null;
        List<Y> yData = null;
        if(xAxis.isAutoRanging()) {
            xData = new ArrayList<X>();
        }
        if(yAxis.isAutoRanging()) {
            yData = new ArrayList<Y>();
        }

        if(xData != null || yData != null) {
            for(Series<X,Y> series : getData()) {
                for(Data<X,Y> data: series.getData()) {
                    if(xData != null) {
                        xData.add(data.getXValue());
                        // Increases the xaxis based on the length of each task
                        xData.add(xAxis.toRealValue(xAxis.toNumericValue(data.getXValue()) + getLength(data.getExtraValue())));
                    }
                    if(yData != null){
                        yData.add(data.getYValue());
                    }
                }
            }
            if(xData != null) {
                xAxis.invalidateRange(xData);
            }
            if(yData != null) {
                yAxis.invalidateRange(yData);
            }
        }
    }

    /**
     * Retrieves the style class from a chartdata object
     * @param obj
     * @return
     */
    private String getStyleClass( Object obj) {
        return ((ChartData) obj).getStyleClass();
    }

    /**
     * Retrieves length of a task from a chartdata object
     * @param obj
     * @return
     */
    private double getLength( Object obj) {
        return ((ChartData) obj).getLength();
    }


    @Override
    protected  void dataItemRemoved(Data<X,Y> item,Series<X,Y> series) {
    }
    @Override
    protected void dataItemChanged(Data<X, Y> item) {
    }
    @Override
    protected void dataItemAdded(Series<X,Y> series, int itemIndex, Data<X,Y> item) {
    }
    @Override
    protected void seriesRemoved(Series<X,Y> series) {
    }
}
