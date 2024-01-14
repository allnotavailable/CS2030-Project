import java.util.function.Supplier;

class Done implements Event {
    private final double time;
    private final Customer customer;
    private final Server server;

    Done(double time, Customer customer, Server server) {
        this.time = time;
        this.customer = customer;
        this.server = server;
    }

    public Pair<Event, ImList<Server>> getNextEvent(ImList<Server> serverList,
        Supplier<Double> restTime, int numOfServers) {
        Server currServer = this.getCurrServer(serverList);
        if (currServer.getServerId() <= numOfServers) {
            double rest = this.getRestTime(restTime);
            Server newServer = currServer.rest(rest);
            serverList = serverList.set(currServer.getServerId() - 1, newServer);
        }
        return new Pair<>(this, serverList);
    }

    public double getArrivalTime() {
        return this.time;
    }

    public double getRestTime(Supplier<Double> restTime) {
        return restTime.get();
    }

    public Server getCurrServer(ImList<Server> serverList) {
        int serverId = this.server.getServerId();
        return serverList.get(serverId - 1);
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public Statistics update(Statistics stat, ImList<Server> serverList, int numOfServers) {
        return stat;
    }
    
    @Override
    public String toString() {
        return String.format("%.3f" + this.getCustomer().toString() + 
            " done serving by" + this.server.toString() + "\n", this.time);
    }
}

