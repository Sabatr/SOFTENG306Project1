package algorithm;
import scheduler.State;
import visualisation.processor.listeners.ObservableAlgorithm;
import visualisation.processor.listeners.SchedulerListener;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles the methods by the ObservableAlgorithm interface.
 * It prevents code duplication and all algorithms should extend from this.
 */
public abstract class AlgorithmHandler implements ObservableAlgorithm {
    private List<SchedulerListener> listeners = new ArrayList<>();
    private State state;
    private AlgorithmEvents eventType;

    @Override
    public void addListener(SchedulerListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(SchedulerListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void fireEvent(AlgorithmEvents event) {
        this.eventType = event;
        fire();
    }

    @Override
    public void fireEvent(AlgorithmEvents event, State state) {
        this.eventType = event;
        this.state = state;
        fire();

    }

    /**
     * Updates the listeners whenever an event occurs
     */
    private void fire() {
        switch(eventType) {
            case ALGORITHM_FINISHED:
                for (SchedulerListener listener : listeners) {
                    listener.setState(state);
                }
                return;
        }
    }
}
