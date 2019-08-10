//package algorhithm;
//
//import javafx.event.EventType;
//import visualisation.processor.listeners.ObservableAlgorithm;
//import visualisation.processor.listeners.SchedulerListener;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import  algorhithm.AlgorithmEvents.Events;
//
//public abstract class AlgorithmHandler implements ObservableAlgorithm {
//    private List<SchedulerListener> listeners = new ArrayList<>();
//    @Override
//    public void addListener(SchedulerListener listener) {
//        listeners.add(listener);
//    }
//
//    @Override
//    public void removeListener(SchedulerListener listener) {
//        listeners.remove(listener);
//    }
//
//    @Override
//    public void fireEvent(AlgorithmEvents event) {
//        switch(event.getEvent()) {
//            case AlgorithmEvents.Events.GET_NUMBER_OF_PROCESSORS:
//                for (SchedulerListener listener : listeners) {
//                    listener.getNumberOfProcessors();
//                }
//                return;
//            case ALGORITHM_FINISHED:
//                return;
//        }
//    }
//}
