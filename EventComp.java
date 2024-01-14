import java.util.Comparator;

class EventComp implements Comparator<Event> {
    
    public int compare(Event e1, Event e2) {
        double timeDiff = e1.getArrivalTime() - e2.getArrivalTime();
        double custDiff = e1.getCustomer().getCustId() - e2.getCustomer().getCustId();
        if (timeDiff < 0) {
            return -1;
        } else if (timeDiff > 0) {
            return 1;
        } else if (timeDiff == 0 && custDiff < 0) {
            return -1;
        } else {
            return 1;
        }
    }
}