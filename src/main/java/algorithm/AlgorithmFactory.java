package algorithm;

import graph.Graph;
import visualisation.AlgorithmDataStorage;
import visualisation.AlgorithmListener;
import visualisation.processor.listeners.SchedulerListener;

/**
 * This follows the factory design pattern.
 * A new algorithm is made depending on the algorithm choice, which is determined by the user.
 */
public class AlgorithmFactory  {
    public Algorithm createAlgorithm(AlgorithmChoice choice, String[] args, Graph graph) throws Exception {
        SchedulerListener listener = createListener(args);
        Algorithm algorithm;
        int numberOfProcessors = Integer.parseInt(args[1]);
        switch (choice) {
            case DFS:
                algorithm = new DFS(numberOfProcessors,graph);
                break;
            case ASTAR:
                algorithm = new AStar(numberOfProcessors,graph);
                break;
            case DFS_PARALLEL:
                algorithm = new DFSParallel(numberOfProcessors,graph);
                break;
            default:
                //TODO: Make our own exception
                throw new Exception("Not a valid algorithm.");
        }

        //Each algorithm needs to have a listener attached.
        // Binds the listener to the storage and the algorithm itself.
        algorithm.addListener(listener);
        System.out.println("a??");
        AlgorithmDataStorage.getInstance().setListener(listener);
        return algorithm;
    }

    /**
     * Creates a listener for the algorithm
     * @param result
     * @return
     */
    private SchedulerListener createListener(String[] result) {
        SchedulerListener listener = new AlgorithmListener();
        listener.setFileName(result[0]);
        listener.setNumberOfProcessors(Integer.parseInt(result[1]));
        return listener;
    }
}
