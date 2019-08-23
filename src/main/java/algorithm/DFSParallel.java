package algorithm;

import graph.Graph;
import scheduler.AStarComparator;
import scheduler.State;

import java.util.*;

/**
 * Algorithm which deals with using the A star implementation. Here, a priority queue
 * is used to ensure that nodes with least cost are placed with greatest priority followed
 * by their level.
 */
public class DFSParallel implements Algorithm {
    private int minFullPath = Integer.MAX_VALUE;
    private boolean traversed;
    private Stack<State> candidate;
    private HashSet<State> visited;
    private Graph graph;

    private int currentThreads;
    private int MAX_THREADS;


    private AStarComparator aStarComparator;
    private State result;


    public DFSParallel(int numProcessors, Graph graph, int maxThreads) {
        candidate = new Stack<>();
        visited = new HashSet();
        this.graph = graph;
        traversed = false;
        candidate.add(new State(numProcessors, graph));
        aStarComparator = new AStarComparator();
        MAX_THREADS = maxThreads;
    }


    public DFSParallel(int numProcessors, Graph graph) {
        candidate = new Stack<>();
        visited = new HashSet();
        this.graph = graph;
        traversed = false;
        candidate.add(new State(numProcessors, graph));
        aStarComparator = new AStarComparator();
        MAX_THREADS = 4;
    }

    /**
     * Runs the algorithm
     *
     * @return
     */
    public State runAlgorithm() {

        List<DFSThread> threadList = new ArrayList<>();

        while (!candidate.isEmpty()) {

            if (currentThreads < MAX_THREADS) {
                currentThreads++;
                DFSThread newThread = new DFSThread();
                threadList.add(newThread);
                newThread.start();
            } else {
                iterate();
            }
        }

        for (DFSThread thread : threadList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    private void stackPush(State s) {
        candidate.push(s);
    }

    private void pruneStack(State s) {
        candidate.removeIf((state) -> aStarComparator.compare(s, state) < 0);
    }

    private State stackPop() {
        if (!candidate.empty()) {
            State s = candidate.pop();
            return s;
        } else {
            return null;
        }
    }

    private synchronized void setResult(State s) {
        if (s.allVisited() && s.getCostToBottomLevel() < minFullPath) {
            System.out.println(s.getCostToBottomLevel());
            result = s;
            minFullPath = s.getCostToBottomLevel();
        }
    }

    private boolean stackCompare(State s) {
        return s.getCostToBottomLevel() < minFullPath;
    }

    public void iterate() {
        //each thread gets a unique 's'
        State s = stackPop();
        if (s != null) {
            for (State s1 : s.generatePossibilities()) {
                if (!visited.contains(s1)) {
                    if (stackCompare(s1)) {
                        stackPush(s1);
                        if (s1.allVisited()) {
                            //Prune branches
                            pruneStack(s1);
                            setResult(s1);
                        }
                    }
                    visited.add(s1);
                }
            }
        }
    }

    private class DFSThread extends Thread {
        @Override
        public void run() {
            runAlgorithm();
        }
    }

}

