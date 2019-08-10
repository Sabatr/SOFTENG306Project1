package visualisation.processor.listeners;

import scheduler.State;

public interface SchedulerListener {
    void setNumberOfProcessors(int numberOfProcessors);
    void setState(State state);
    int getNumberOfProcessors();
    State getState();
    void setFileName(String path);
    String getFileName();
}
