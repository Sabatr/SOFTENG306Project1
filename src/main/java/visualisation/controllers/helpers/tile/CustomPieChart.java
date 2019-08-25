package visualisation.controllers.helpers.tile;

import javafx.scene.chart.PieChart;

/**
 * A custom piechart which allows for
 */
public class CustomPieChart extends PieChart {
    private final String PIECHART_STYLESHEET = "../../../visualisationassets/PieChart.css";
    public CustomPieChart(double width, double height) {
        super();
        this.setPrefSize(width,height);
        this.setLegendVisible(false);
        this.setLayoutX(-25);
        this.setLayoutY(-50);
        this.getStylesheets().add(CustomPieChart.class.getResource(PIECHART_STYLESHEET).toString());
}
}
