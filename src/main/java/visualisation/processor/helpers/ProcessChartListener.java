package visualisation.processor.helpers;

import scheduler.State;
import visualisation.processor.listeners.SchedulerListener;

public class ProcessChartListener implements SchedulerListener {
    @Override
    public void getNumberOfProcessors(int numberOfProcessors) {
        System.out.println(numberOfProcessors);
    }

    @Override
    public void getState(State state) {
        System.out.println(state);
    }
}
