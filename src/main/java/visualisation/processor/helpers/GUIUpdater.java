package visualisation.processor.helpers;

import algorithm.AlgorithmBranchDetails;
import visualisation.controllers.GUIController;

/**
 * A singleton which updates the GUI when needed.
 * It contains the controller which can manipulate the GUI components
 */
public class GUIUpdater {
    private static GUIUpdater guiUpdater;
    private GUIController controller;
    private GUIUpdater() {
    }

    public static GUIUpdater getInstance() {
        if (guiUpdater == null) {
            guiUpdater = new GUIUpdater();
        }
        return guiUpdater;
    }

    /**
     * Sets the controller to the GUI
     * @param controller
     */
    public void setController(GUIController controller) {
        this.controller = controller;
    }

    /**
     * Updates the label which tells the branch
     * @param
     */
    public void updateBranchLabel(AlgorithmBranchDetails details) {
       String label = "Total branches: " + details.getBranchesSeen();
        controller.updateBranchCount(label, details);
    }

    /**
     * Updates the label for time elapsed
     * @param time
     */
    public void updateTimeLabel(String time) {
        controller.updateTimer(time);
    }

    /**
     * Updates the process chart when algorithm is complete
     */
    public void updateProcessChart() {
        controller.createChart();
    }
}
