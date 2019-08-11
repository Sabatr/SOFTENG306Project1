package algorithm;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import scheduler.State;
import visualisation.processor.listeners.ObservableAlgorithm;
import visualisation.processor.listeners.SchedulerListener;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles the methods by the ObservableAlgorithm interface.
 * It prevents code duplication and all algorithms should extend from this.
 */
public abstract class AlgorithmHandler implements ObservableAlgorithm {
    private List<SchedulerListener> listeners = new ArrayList<>();
    private State state;
    private AlgorithmEvents eventType;
    private long timeTaken;
    private Timeline timeline;
    @Override
    public void addListener(SchedulerListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(SchedulerListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void fireEvent(AlgorithmEvents event) {
        this.eventType = event;
        fire();
    }

    @Override
    public void fireEvent(AlgorithmEvents event, State state) {
        this.eventType = event;
        this.state = state;
        fire();

    }

    protected void startTimer() {

        timeline = new Timeline();
        long time = System.currentTimeMillis();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100), (ActionEvent e) -> {
            this.eventType = AlgorithmEvents.UPDATE_TIME_ELAPSED;
            timeTaken = System.currentTimeMillis() - time;
            fire();
        } ));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    protected void endTimer() {
        timeline.stop();
    }

    /**
     * Updates the listeners whenever an event occurs
     */
    private void fire() {
        switch(eventType) {
            case ALGORITHM_FINISHED:
                endTimer();
                for (SchedulerListener listener : listeners) {
                    listener.setState(state);
                }
                return;
            case UPDATE_TIME_ELAPSED:
                for (SchedulerListener listener: listeners) {
                    listener.updateTimeElapsed(timeTaken);
                }
                return;
            case UPDATE_BRANCH_COUNTER:
                //TODO: Not sure if this is accurate
                for (SchedulerListener listener: listeners) {
                    listener.updateBranchCounter();
                }
                return;
        }
    }
}
