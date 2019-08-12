package visualisation;

import javafx.application.Platform;
import javafx.concurrent.Task;
import scheduler.State;
import visualisation.processor.helpers.GUIUpdater;
import visualisation.processor.listeners.SchedulerListener;

/**
 * A listener which is used to update data when certain events occur
 */
public class AlgorithmListener implements SchedulerListener {
    private int numberOfProcessors;
    private State state;
    private String path;
    private long timeElapsed;
    private int branchCounter;

    @Override
    public void setNumberOfProcessors(int numberOfProcessors) {
        this.numberOfProcessors = numberOfProcessors;
    }

    @Override
    public int getNumberOfProcessors(){
        return numberOfProcessors;
    }

    @Override
    public void setState(State state) {
        this.state = state;
        GUIUpdater.getInstance().updateProcessChart();
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

    @Override
    public void updateTimeElapsed(long time) {
        timeElapsed = time;
        String timeFormatted;
        StringBuilder builder = new StringBuilder();
        builder.append(timeElapsed);
        builder.append("ms");
        GUIUpdater.getInstance().updateTimeLabel(builder.toString());
    }

    @Override
    public long getTimeElapsed() {
        return timeElapsed;
    }

    @Override
    public void updateBranchCounter() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                branchCounter++;
                GUIUpdater.getInstance().updateBranchLabel(branchCounter);
                return null;
            }
        };
        new Thread(task).start();

    }

    @Override
    public int getBranchCounter(){
        return branchCounter;
    }
}
