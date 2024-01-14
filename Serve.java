import java.util.function.Supplier;

class Serve implements Event {
    private final double time;
    private final Customer customer;
    private final Server server;

    Serve(double time, Customer customer, Server server) {
        this.time = time;
        this.customer = customer;
        this.server = server;
    }
    
    public Pair<Event, ImList<Server>> getNextEvent(ImList<Server> serverList, 
        Supplier<Double> restTime, int numOfServers) {
        double serviceTime = this.getServiceTime();
        Server currServer = this.getCurrServer(serverList);
        if (currServer.getServerId() > numOfServers + 1) {
            Server mainSelf = serverList.get(numOfServers);
            if (!mainSelf.getQueue().isEmpty()) {
                Server newServer = currServer.waitServe(serviceTime);
                mainSelf = mainSelf.removeQueue();
                serverList = serverList.set(this.server.getServerId() - 1, newServer);
                serverList = serverList.set(numOfServers, mainSelf);
                return new Pair<>(new Done(this.getArrivalTime() + serviceTime,
                    this.getCustomer(), newServer), serverList);
            } else {
                Server newServer = currServer.serve(serviceTime, this.getCustomer());
                serverList = serverList.set(this.server.getServerId() - 1, newServer);
                return new Pair<>(new Done(this.getArrivalTime() + serviceTime,
                    this.getCustomer(), newServer), serverList);
            }
        } else { 
            if (!currServer.getQueue().isEmpty()) {
                Server newServer = currServer.waitServe(serviceTime);
                newServer = newServer.removeQueue();
                serverList = serverList.set(this.server.getServerId() - 1, newServer);
                return new Pair<>(new Done(this.getArrivalTime() + serviceTime,
                    this.getCustomer(), newServer), serverList);
            } else {
                Server newServer = currServer.serve(serviceTime, this.getCustomer());
                serverList = serverList.set(this.server.getServerId() - 1, newServer);
                return new Pair<>(new Done(this.getArrivalTime() + serviceTime,
                    this.getCustomer(), newServer), serverList);
            }
        }
    }

    public double getServiceTime() {
        return this.getCustomer().getServiceTime();
    }

    public double getArrivalTime() {
        return this.time;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public Server getCurrServer(ImList<Server> serverList) {
        int serverId = this.server.getServerId();
        return serverList.get(serverId - 1);
    }

    public Statistics update(Statistics stat, ImList<Server> serverList, int numOfServers) {
        Statistics updatedStat = stat.serve();
        return updatedStat;
    }

    @Override
    public String toString() {
        return String.format("%.3f" + this.getCustomer().toString() + 
            " serves by" + this.server.toString() + "\n", this.time);
    }
}