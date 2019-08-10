package visualisation.processor.listeners;

import algorhithm.AlgorithmEvents;
import javafx.event.EventType;

public interface ObservableAlgorithm {
    void addListener(SchedulerListener listener);
    void removeListener(SchedulerListener listener);
    void fireEvent(AlgorithmEvents event);

}
