package visualisation.processor.listeners;

import algorithm.AlgorithmEvents;
import scheduler.State;
/**
 * This interface describes the observable object for the listeners
 */
public interface ObservableAlgorithm {

    /**
     * Adds a listener to the object
     * @param listener
     */
    void addListener(SchedulerListener listener);

    /**
     * Removes a listener from the object
     * @param listener
     */
    void removeListener(SchedulerListener listener);

    /**
     * These methods set off a method whenever an object has been updated
     * @param event
     */
    void fireEvent(AlgorithmEvents event);
    void fireEvent(AlgorithmEvents event, State state);

}
