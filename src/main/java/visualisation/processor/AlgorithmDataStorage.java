package visualisation.processor;

import visualisation.processor.listeners.SchedulerListener;

public class AlgorithmDataStorage {
    private static AlgorithmDataStorage storage;
    private SchedulerListener listener;
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
}
