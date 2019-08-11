package visualisation.processor.helpers;

import visualisation.controllers.GUIController;

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

    public void setController(GUIController controller) {
        this.controller = controller;
    }

    public void updateBranchLabel(int count) {
        String label = "Branches searched: " + count;
        controller.updateBranchCount(label);
    }

    public void updateTimeLabel(String time) {
        controller.updateTimer(time);
    }
}
