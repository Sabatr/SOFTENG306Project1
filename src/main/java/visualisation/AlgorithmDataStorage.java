package visualisation;

import algorithm.AlgorithmBranchDetails;
import visualisation.processor.listeners.SchedulerListener;
import scheduler.State;

/**
 * A storage for useful data needed for the algorithm.
 * It acts as an in between class for the listener and the GUI
 * This class is a singleton as we would only want one storage at a time.
 */
public class AlgorithmDataStorage {
    private static AlgorithmDataStorage storage;
    private SchedulerListener listener;
    private AlgorithmBranchDetails branchDetails = new AlgorithmBranchDetails();

    private AlgorithmDataStorage() {
    }

    public int getNumberOfProcessors() {
        return listener.getNumberOfProcessors();
    }


    /**
     * @return The single instance of this class
     */
    public static AlgorithmDataStorage getInstance() {
        if (storage == null) {
            storage = new AlgorithmDataStorage();
        }
        return storage;
    }

    public void setListener(SchedulerListener listener) {
        this.listener = listener;
    }

    public State getState() {
        return listener.getState();
    }

    public String getInputFileName() {
        return listener.getFileName();
    }

    public long getTimeElapsed() {
        return listener.getTimeElapsed();
    }

    public void setTotalBranches(int counter) {
        branchDetails.setBranchesSeen(counter);
    }

    public void setPrunedBranches(int counter) {
        branchDetails.setBranchesPruned(counter);
    }

    public void setDuplicateBranches(int counter) {
        branchDetails.setBranchesPruned(counter);
    }

    public AlgorithmBranchDetails getDetails() {
        return branchDetails;
    }

    public void incrementDuplicates() {
        branchDetails.incrementDuplicates();
    }

    public void incrementPruned(int i) {
        branchDetails.incrementPruned(i);
    }

    public void incrementVisited() {
        branchDetails.incrementVisited();
    }
}
