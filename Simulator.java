import java.util.function.Supplier;

class Simulator {
    private final int numOfServers;
    private final int numOfSelfChecks;
    private final int qmax;
    private final ImList<Double> arrivalTimes;
    private final Supplier<Double> serviceTimes;
    private final Supplier<Double> restTimes;


    Simulator(int numOfServers, int numOfSelfChecks, int qmax, ImList<Double> arrivalTimes, 
        Supplier<Double> serviceTimes, Supplier<Double> restTimes) {
        this.numOfServers = numOfServers;
        this.numOfSelfChecks = numOfSelfChecks;
        this.qmax = qmax;
        this.arrivalTimes = arrivalTimes;
        this.serviceTimes = serviceTimes;
        this.restTimes = restTimes;
    }

    public String simulate() {
        ImList<Customer> customerList = new ImList<Customer>();
        for (int i = 0; i < arrivalTimes.size(); i++) {
            double custArrivalTime = arrivalTimes.get(i);
            customerList = customerList.add(new Customer(i + 1, custArrivalTime, serviceTimes));
        }
        PQ<Event> pq = new PQ<Event>(new EventComp());
        for (int i = 0; i < customerList.size(); i++) {
            Customer currCustomer = customerList.get(i);
            double currArrivalTime = currCustomer.getArrivalTime();
            Arrive arrive = new Arrive(currArrivalTime, currCustomer, qmax);
            pq = pq.add(arrive);
        }
        ImList<Server> serverList = this.createServers();
        String output = "";
        Pair<Event, PQ<Event>> pr = pq.poll();
        Statistics stat = new Statistics(0, 0, 0);
    
        while (pq.isEmpty() == false) {
            pq = pr.second();
            Event firstEvent = pr.first();
            Pair<Event, ImList<Server>> nextPair = firstEvent.getNextEvent(serverList, restTimes, 
                numOfServers);
            Event nextEvent = nextPair.first();
            serverList = nextPair.second();
            if (firstEvent != nextEvent) {
                pq = pq.add(nextEvent);
            }
            stat = firstEvent.update(stat, serverList, numOfServers);
            output += firstEvent;
            pr = pq.poll();
        }
        stat = stat.averageWaitingTime();
        return String.format(output + stat);
    }

    public ImList<Server> createServers() {
        ImList<Server> serverList = new ImList<Server>();
        for (int i = 0; i < this.numOfServers; i++) {
            serverList = serverList.add(new Server(i + 1, 0, new ImList<Customer>(), qmax));
        }
        if (numOfSelfChecks > 0) {
            serverList = serverList.add(new SelfCheck(numOfServers + 1, 0, 
                new ImList<Customer>(), qmax, numOfServers + 1));
            for (int i = 1; i < this.numOfSelfChecks; i++) {
                serverList = serverList.add(new SelfCheck(numOfServers + i + 1, 0, 
                    new ImList<Customer>(), 0, numOfServers + 1));
            }
        }
        return serverList;
    }
}

