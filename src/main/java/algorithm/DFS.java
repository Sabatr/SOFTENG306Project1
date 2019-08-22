package algorithm;

import graph.Graph;
import scheduler.State;

import java.util.Stack;

/**
 * Algorithm which deals with using the DFS implementation. Here, a stack is used to allow
 * us to view the last assigned state and then allows us to explore the states that follow
 * the last assigned state. This enables us to implement the DFS algorithm.
 */
public class DFS implements Algorithm {
    private final int numP;
    private Stack<State> stateStack;
    Graph graph;
    private int boundValue;

    public DFS(int numProcessors, Graph g, State initState) {
        graph = g;
        stateStack = new Stack<State>();
        numP = numProcessors;

        //Init state
        stateStack.push(initState);
        boundValue = Integer.MAX_VALUE;



    }

    public DFS(int numProcessors, Graph g) {
        graph = g;
        stateStack = new Stack<State>();
        numP = numProcessors;

        //Init state
        stateStack.push(new State(numProcessors,g));
        boundValue = Integer.MAX_VALUE;



    }

    /**
     * This is the implementation of the DFS algorithm
     * @return
     */
    public State runAlgorithm() {

//             state = state.getNextPossibleState(state);
//            System.out.println("next state " + state);
//            State afterState = state.getNextPossibleState(state);
//            System.out.println("AFTER state " + afterState);
//        System.out.println("GET count " + state.getCountNextStateCalled());
//
//
//
//        State state2 = state.getNextPossibleState(state);
//        System.out.println("next state " + state2);
//
//        State state3 = state.getNextPossibleState(state);
//        System.out.println("next state " + state3);
//
//        State state4 = state.getNextPossibleState(state);
//        System.out.println("next state " + state4);



//        System.out.println("Here is the count " + state.getCountNextStateCalled());
//
//

        State bestState = new State(numP, graph);
        bestState.setCurrentCost(Integer.MAX_VALUE);
        State state = stateStack.peek();
        System.out.println(state);
            while (!stateStack.empty()) {
                    if (state.getCurrentCost() == 2){
                        System.out.println(state);
                    }

                    state = state.getNextPossibleState(state);
//                } else {
//                    state = null;
//                }
                if (state != null ) {
                    stateStack.push(state);
                } else {
                    stateStack.pop();
                    if (stateStack.isEmpty()){
                        break;
                    }
                    state = stateStack.peek();
                    if (state.isScheduleEmpty()){
                        break;
                    }
                    if (state.getCurrentCost() < bestState.getCurrentCost()){
                        bestState = state;
                    }
//
//                    System.out.println(state);

                }
                System.out.println(state);

        }

        System.out.println(bestState);

//
//        System.out.println("best state " +  bestState);

//        while (!stateStack.empty()) {
//            //get latest state
//
//            int currentBoundValue = boundValue;
//            //If cost of state equals or greater than bound value don't visit its following states then
//            if (state.getCurrentCost() < currentBoundValue) {
//                if (state.allVisited()) {
//                    boundValue = state.getCurrentCost();
//                    bestState = state;
//                } else {
//                    for (State nextState : state.generatePossibilities()) {
//                        stateStack.push(nextState);
//                    }
//                }
//            }
//
//        }
        return bestState;
    }

}