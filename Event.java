import java.util.function.Supplier;

interface Event {

    public Customer getCustomer();

    public double getArrivalTime();

    public Statistics update(Statistics stat, ImList<Server> serverList, int numOfServers);

    public Pair<Event, ImList<Server>> getNextEvent(ImList<Server> serverList, 
        Supplier<Double> restTime, int numOfServers);
}