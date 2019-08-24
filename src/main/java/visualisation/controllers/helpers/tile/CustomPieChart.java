package visualisation.controllers.helpers.tile;

import javafx.scene.chart.PieChart;

public class CustomPieChart extends PieChart {
    public CustomPieChart(double width, double height) {
        super();
        this.setPrefSize(width,height);
        this.setLegendVisible(false);
        this.setLayoutX(-25);
        this.setLayoutY(-50);
        this.getStylesheets().add(CustomPieChart.class.getResource("../../../visualisationassets/PieChart.css").toString());
}
}
