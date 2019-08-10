package visualisation;

import scheduler.State;
import visualisation.processor.listeners.SchedulerListener;

public class AlgorithmListener implements SchedulerListener {
    private int numberOfProcessors;
    private State state;
    private String path;
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

    @Override
    public State getState() {
        return state;
    }

    @Override
    public void setFileName(String path) {
        this.path = path;
    }

    @Override
    public String getFileName() {
        return path;
    }


}
