package algorithm;

import graph.Graph;
import scheduler.AStarComparator;
import scheduler.State;
import visualisation.processor.listeners.SchedulerListener;

import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * Algorithm which deals with the problem using the A star implementation. Here, a priority queue
 * is used to ensure that nodes with least cost are placed with greatest priority followed
 * by their level. This is similar to the
 */
public class AStarParallel  implements  Algorithm{
    private int minFullPath = Integer.MAX_VALUE;
    private boolean traversed;
    private PriorityQueue<State> candidate;
    private HashSet<State> visited;
    private Graph graph;
    private State result;
    private final int MAX_THREADS = 4;
    private int currentThreads;

    private int num;

    public AStarParallel(int numProcessors, Graph graph) {
        candidate = new PriorityQueue<>(new AStarComparator());
        visited = new HashSet();
        this.graph = graph;
        traversed = false;
        candidate.add(new State(numProcessors, graph));
        currentThreads = 1;
        num = 0;
    }

    private synchronized void changeCurrentThreads(int i){
        currentThreads = currentThreads + i;
    }

    @Override
    public void addListener(SchedulerListener listener) {

    }

    /**
     * Runs the algorithm
     * @return
     */
    public State runAlgorithm() {
        while (!candidate.isEmpty() && candidate.peek().getCostToBottomLevel() <= minFullPath) {
            if (currentThreads < MAX_THREADS) {
                changeCurrentThreads(1);
                new AStarThread().start();
                iterate();
            } else {
                iterate();
            }

        }
        return result;
    }

    private synchronized State pollQueue(){
        scheduler.State s = candidate.poll();
        return s;
    }

    private synchronized void addToQueue(State s1){
        candidate.add(s1);
    }

    private void iterate(){
        scheduler.State s = pollQueue();
        if (s!=null) {
            // Generate possibilities for a given state
            for (scheduler.State s1 : s.generatePossibilities()) {

                // Only traverse and analyse if it hasnt been visited before
                if (!visited.contains(s1)) {

                    // Only traverse if it is a viable candidate
                    if (s1.getCostToBottomLevel() < minFullPath) {
                        addToQueue(s1);

                        // Update optimal if it is better than the optimal solution
                        if (s1.allVisited() && s1.getCostToBottomLevel() < minFullPath) {
                            minFullPath = s1.getCostToBottomLevel();
                            result = s1;
                        }
                    }
                    
                    visited.add(s1);
                }
            }
        }
    }

    private class AStarThread extends Thread{
        @Override
        public void run() {
            runAlgorithm();
            changeCurrentThreads(-1);
        }


    }

}