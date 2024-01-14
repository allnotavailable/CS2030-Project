import java.util.function.Supplier;

class Buffer implements Event {
    private final double time;
    private final Customer customer;
    private final Server server;
    
    Buffer(double time, Customer customer, Server server) {
        this.time = time;
        this.customer = customer;
        this.server = server;
    }

    public double getArrivalTime() {
        return this.time;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public Pair<Event, ImList<Server>> getNextEvent(ImList<Server> serverList, 
        Supplier<Double> restTime, int numOfServers) {
        Server currServer = this.getCurrServer(serverList);
        if (currServer.getServerId() > numOfServers) {
            Server mainSelf = serverList.get(numOfServers);
            Server earliestServer = this.getEarliestSelfCheck(serverList, numOfServers);
            if (mainSelf.getQueue().get(0).getCustId() == this.getCustomer().getCustId() 
                && this.getArrivalTime() == currServer.getEndTime()) {
                return new Pair<>(new Serve(earliestServer.getEndTime(), this.getCustomer(),
                    earliestServer), serverList);
            } else {
                return new Pair<>(new Buffer(earliestServer.getEndTime(), getCustomer(), 
                    earliestServer), serverList);
            }
        } else {
            if (currServer.getQueue().get(0).getCustId() == this.getCustomer().getCustId() 
                && this.getArrivalTime() == currServer.getEndTime()) {
                return new Pair<>(new Serve(currServer.getEndTime(), this.getCustomer(),
                    currServer), serverList);
            } else {
                return new Pair<>(new Buffer(currServer.getEndTime(), getCustomer(), currServer), 
                    serverList);
            }
        }
    }

    public Server getEarliestSelfCheck(ImList<Server> serverList, int numOfServers) {
        Server currServer = this.getCurrServer(serverList);
        int selfCheckID = currServer.getServerId();
        for (Server server : serverList) {
            if (server.getServerId() > numOfServers) {
                if (server.getEndTime() < currServer.getEndTime()) {
                    selfCheckID = server.getServerId();
                }
            }
        }
        return serverList.get(selfCheckID - 1);
    }

    public Server getCurrServer(ImList<Server> serverList) {
        int serverId = this.server.getServerId();
        return serverList.get(serverId - 1);
    }

    public double getRestTime(Supplier<Double> restTime) {
        return restTime.get();
    }

    public Statistics update(Statistics stat, ImList<Server> serverList, int numOfServers) {
        Server currServer = this.getCurrServer(serverList);
        if (currServer.getServerId() > numOfServers) {
            Server mainSelf = serverList.get(numOfServers);
            Server earliestServer = this.getEarliestSelfCheck(serverList, numOfServers);
            if (mainSelf.getQueue().get(0).getCustId() == this.getCustomer().getCustId() 
                && this.getArrivalTime() == currServer.getEndTime()) {
                Statistics updatedStat = stat.waitingTime(earliestServer, this.getCustomer());
                return updatedStat;
            } else {
                return stat;
            }
        } else {
            if (currServer.getQueue().get(0).getCustId() == this.getCustomer().getCustId()
                && this.getArrivalTime() == currServer.getEndTime()) {
                Statistics updatedStat = stat.waitingTime(currServer, this.getCustomer());
                return updatedStat;
            } else {
                return stat;
            }
        }
    }

    @Override
    public String toString() {
        return "";
    }
}