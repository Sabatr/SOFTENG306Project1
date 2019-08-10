package visualisation.processor.helpers;

/**
 * This class acts as extra data to give to the process chart.
 */
public class ChartData {
    private long length;
    private String styleClass;
    public ChartData(long lengthMs, String styleClass) {
        this.length = lengthMs;
        this.styleClass = styleClass;
    }
    public long getLength() {
        return length;
    }
    public String getStyleClass() {
        return styleClass;
    }
}
