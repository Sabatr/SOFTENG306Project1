package visualisation.processor.listeners;

import scheduler.State;

public interface SchedulerListener {
    void getNumberOfProcessors(int numberOfProcessors);
    void getState(State state);
}
