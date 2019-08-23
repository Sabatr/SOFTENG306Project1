package algorithm;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import scheduler.State;
import utils.CliParser;
import visualisation.processor.listeners.ObservableAlgorithm;
import visualisation.processor.listeners.SchedulerListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    private boolean timerStarted = false;
    private String formattedTime;

    public AlgorithmHandler() {
        branchUpdate();
    }

    private void branchUpdate() {
        Timeline timeline = new Timeline(
            new KeyFrame(
                    Duration.millis( 1000 ),
                    event -> {
                        fireEvent(AlgorithmEvents.UPDATE_BRANCH_COUNTER);
                    }
            )
        );
        timeline.setCycleCount( Animation.INDEFINITE );
        timeline.play();
    }

    private void timeUpdate() {
//        Timeline timeline = new Timeline(
//                new KeyFrame(
//                        Duration.millis( 1000 ),
//                        event -> {
//
//                        }
//                )
//        );
//        Task<Void> task = new Task<Void>() {
//            @Override
//            protected Void call() throws Exception {
//                AlgorithmHandler.this.fireEvent(AlgorithmEvents.UPDATE_TIME_ELAPSED);
//                return null;
//            }
//        };
//        timeline.setCycleCount( Animation.INDEFINITE );
//        timeline.play();
//        Thread thread = new Thread(task);
//        try {
//            thread.start();
//            thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void addListener(SchedulerListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(SchedulerListener listener) {
        listeners.remove(listener);
    }

    /**
     * Fires an event
     * @param event
     */
    @Override
    public void fireEvent(AlgorithmEvents event) {
        this.eventType = event;
        fire();
    }

    /**
     * Fires of an event, but also takes in a state
     * @param event
     * @param state
     */
    @Override
    public void fireEvent(AlgorithmEvents event, State state) {
        this.eventType = event;
        this.state = state;
        fire();

    }

    /**
     * Begins the timer which is used to record the time taken until the algorithm is complete
     */
    protected void startTimer() {
        if (!CliParser.isVisualisation()) return;
        long time = System.currentTimeMillis();
        timeline = new Timeline();
        Task task = new Task<Void>() {
            @Override
            protected Void call() {
                timeline.getKeyFrames().add(new KeyFrame(Duration.millis(10), (ActionEvent e) -> {
                    eventType = AlgorithmEvents.UPDATE_TIME_ELAPSED;
                    timeTaken = System.currentTimeMillis() - time;
//                    formattedTime = TimeUnit.MILLISECONDS.toMinutes(timeTaken)
//                            + ":" + TimeUnit.MILLISECONDS.toSeconds(timeTaken)
//                            + ":" + timeTaken%1000;
                    formattedTime = String.format("%02d:%02d:%03d",
                            TimeUnit.MILLISECONDS.toMinutes(timeTaken),
                            TimeUnit.MILLISECONDS.toSeconds(timeTaken),
                            timeTaken%1000);

                    if (timerStarted) {
                        timeline.stop();
                    }
                    fire();
                } ));
                timeline.setCycleCount(Timeline.INDEFINITE);
                timeline.play();
                return null;
            }
        };
        new Thread(task).start();

    }

    protected void endTimer() {
        timerStarted = true;
    }

    /**
     * Updates the listeners whenever an event occurs
     */
    private void fire() {
        if (!CliParser.isVisualisation()) return;
        switch(eventType) {
            case ALGORITHM_FINISHED:
                endTimer();
                for (SchedulerListener listener : listeners) {
                    listener.setState(state);
                }
                return;
            case UPDATE_TIME_ELAPSED:
                for (SchedulerListener listener: listeners) {
                    listener.updateTimeElapsed(formattedTime);
                }
                return;
            case UPDATE_BRANCH_COUNTER:
                for (SchedulerListener listener: listeners) {
                    listener.updateBranchCounter();
                }
                return;
        }
    }
}
