package visualisation.processor.listeners;

import scheduler.State;

/**
 * This interface defines the methods needed for a schedule listener.
 */
public interface SchedulerListener {

    /**
     * Tells the listener to store the number of processors
     * @param numberOfProcessors
     */
    void setNumberOfProcessors(int numberOfProcessors);

    /**
     * Retrieves the number of processors stored by the listener
     * @return
     */
    int getNumberOfProcessors();

    /**
     * Tells the listener to store the state
     * @param state
     */
    void setState(State state);

    /**
     * Retrieve the state from the listener
     * @return
     */
    State getState();

    /**
     * Tells the listener to store the file name
     * @param path
     */
    void setFileName(String path);

    /**
     * Retrieves the file name from the listener
     * @return
     */
    String getFileName();

    void updateTimeElapsed(String time);

    long getTimeElapsed();

    void updateBranchCounter();
    int getBranchCounter();
}
