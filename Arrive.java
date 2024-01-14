import java.util.function.Supplier;

class Arrive implements Event {
    private final double time;
    private final Customer customer;
    private final int qmax;

    Arrive(double time, Customer customer, int qmax) {
        this.time = time;
        this.customer = customer;
        this.qmax = qmax;
    }

    public double getArrivalTime() {
        return this.time;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public Pair<Event, ImList<Server>> getNextEvent(ImList<Server> serverList, 
        Supplier<Double> restTime, int numOfServers) {
        double time = this.getArrivalTime();
        Customer customer = this.getCustomer();
        Server availableServer = this.getAvailableServer(serverList, customer);
        Server availableQueue = this.getAvailableQueue(serverList);
        if (availableServer.getServerId() != -1) {
            return new Pair<>(new Serve(customer.getArrivalTime(),
                customer, availableServer), serverList);
        } else if (availableServer.getServerId() == -1 && availableQueue.getServerId() != -1) {
            Server newServer = availableQueue.addQueue(customer);
            serverList = serverList.set(availableQueue.getServerId() - 1, newServer);
            return new Pair<>(new Wait(time, customer, newServer, numOfServers), serverList);
        } else {
            return new Pair<>(new Leave(time, customer), serverList);
        }
    }
    
    public Server getAvailableServer(ImList<Server> serverList, Customer customer) {
        for (int i = 0; i < serverList.size(); i++) {
            if (serverList.get(i).isAvailable(customer)) {
                return serverList.get(i);
            }
        }
        return new Server(-1, -1, new ImList<Customer>(), qmax);
    }

    public Server getAvailableQueue(ImList<Server> serverList) {
        for (int i = 0; i < serverList.size(); i++) {
            if (serverList.get(i).isQueueAvailable()) {
                return serverList.get(i);
            }
        }
        return new Server(-1,-1, new ImList<Customer>(), qmax);
    }

    public Statistics update(Statistics stat, ImList<Server> serverList, int numOfServers) {
        return stat;
    }

    @Override
    public String toString() {
        return String.format("%.3f" + this.getCustomer().toString() + " arrives" + "\n", this.time);
    }
}
