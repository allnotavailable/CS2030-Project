import java.util.function.Supplier;

class Leave implements Event {
    private final double time;
    private final Customer customer;

    Leave(double time, Customer customer) {
        this.time = time;
        this.customer = customer;
    }

    public Pair<Event, ImList<Server>> getNextEvent(ImList<Server> serverList, 
        Supplier<Double> restTime, int numOfServers) {
        return new Pair<>(this, serverList);
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public double getArrivalTime() {
        return this.time;
    }

    public Statistics update(Statistics stat, ImList<Server> serverList, int numOfServers) {
        Statistics updatedStat = stat.left();
        return updatedStat;
    }
    
    @Override
    public String toString() {
        return String.format("%.3f" + this.getCustomer().toString() + " leaves" + "\n", this.time);
    }
}

