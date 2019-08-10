package visualisation.controllers.helpers;

import javafx.beans.NamedArg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.Axis;
import javafx.scene.chart.XYChart;

public class ProcessChart<X,Y> extends XYChart<X,Y> {
    private double tileHeight = 1;
    public ProcessChart(@NamedArg("xAxis") Axis<X> xAxis, @NamedArg("yAxis") Axis<Y> yAxis) {
        super(xAxis,yAxis);
        setData(FXCollections.<Series<X,Y>>observableArrayList());
    }

    protected void setTileHeight(double tileHeight) {
        this.tileHeight = tileHeight;
    }


    @Override
    protected void dataItemAdded(Series<X, Y> series, int itemIndex, Data<X, Y> item) {

    }

    @Override
    protected void dataItemRemoved(Data<X, Y> item, Series<X, Y> series) {

    }

    @Override
    protected void dataItemChanged(Data<X, Y> item) {

    }

    @Override
    protected void seriesAdded(Series<X, Y> series, int seriesIndex) {

    }

    @Override
    protected void seriesRemoved(Series<X, Y> series) {

    }

    @Override
    protected void layoutPlotChildren() {

    }
}
