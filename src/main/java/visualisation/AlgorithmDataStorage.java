package visualisation;

import visualisation.processor.listeners.SchedulerListener;
import scheduler.State;
public class AlgorithmDataStorage {
    private static AlgorithmDataStorage storage;
    private SchedulerListener listener;
    private String inputFileName;
    private AlgorithmDataStorage() {

    }

    public int getNumberOfProcessors() {
        return listener.getNumberOfProcessors();
    }

    public static AlgorithmDataStorage getInstance() {
        if (storage == null) {
            storage = new AlgorithmDataStorage();
        }
        return storage;
    }

    public void setListener(SchedulerListener listener) {
        this.listener = listener;
    }

    public State getState() {
        return listener.getState();
    }

    public String getInputFileName() {
        return listener.getFileName();
    }

}
