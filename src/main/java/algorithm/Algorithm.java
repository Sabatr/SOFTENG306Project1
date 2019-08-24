package algorithm;

import scheduler.State;
import visualisation.processor.listeners.SchedulerListener;

/**
 * Interface to represent an algorithm
 */
public interface Algorithm {
    void addListener(SchedulerListener listener);
    public State runAlgorithm();
}
