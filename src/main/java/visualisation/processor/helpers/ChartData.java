package visualisation.processor.helpers;

/**
 * This class acts as extra data to give to the process chart.
 */
public class ChartData {
    private long length;
    private String styleClass;
    private String text;
    public ChartData(long lengthMs, String styleClass, String text) {
        this.length = lengthMs;
        this.styleClass = styleClass;
        this.text = text;
    }
    public long getLength() {
        return length;
    }
    public String getStyleClass() {
        return styleClass;
    }
    public String getText() {return text; }
}
