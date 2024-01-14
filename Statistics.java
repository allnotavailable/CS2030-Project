class Statistics {
    private final double averageWaitingTimes;
    private final int numServed;
    private final int numLeft;
    private static final int ONE = 1;
    
    Statistics(double averageWaitingTimes, int numServed, int numLeft) {
        this.averageWaitingTimes = averageWaitingTimes;
        this.numServed = numServed;
        this.numLeft = numLeft;
    }

    public double getwaitingTime() {
        return this.averageWaitingTimes;
    }

    public Statistics serve() {
        return new Statistics(this.averageWaitingTimes, numServed + ONE, this.numLeft);
    }

    public Statistics left() {
        return new Statistics(this.averageWaitingTimes, this.numServed, numLeft + ONE);
    }

    public Statistics waitingTime(Server server, Customer customer) {
        return new Statistics(averageWaitingTimes + (server.getEndTime() - 
            customer.getArrivalTime()), numServed, numLeft);
    }

    public Statistics averageWaitingTime() {
        if (numServed == 0) {
            return new Statistics(0.0, numServed, numLeft);
        }
        return new Statistics(averageWaitingTimes / numServed, numServed, numLeft);
    }

    @Override
    public String toString() {
        return String.format("[%.3f %d %d]", this.averageWaitingTimes, this.numServed, 
            this.numLeft);
    }
}
