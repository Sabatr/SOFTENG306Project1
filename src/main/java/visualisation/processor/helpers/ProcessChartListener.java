package visualisation.processor.helpers;

import scheduler.State;
import visualisation.processor.listeners.SchedulerListener;

public class ProcessChartListener implements SchedulerListener {
    private int numberOfProcessors;
    private State state;
    @Override
    public void setNumberOfProcessors(int numberOfProcessors) {
        this.numberOfProcessors = numberOfProcessors;
    }

    public int getNumberOfProcessors(){
        return numberOfProcessors;
    }

    @Override
    public void setState(State state) {
        this.state = state;
    }
}
