class Server {

    private final int serverId;
    private final double endTime;
    private final ImList<Customer> queue;
    private final int qmax;

    Server(int serverId, double endTime, ImList<Customer> queue, int qmax) {
        this.serverId = serverId;
        this.endTime = endTime;
        this.queue = queue;
        this.qmax = qmax;
    }

    public int getMainId() {
        return this.serverId;
    }

    public int getQmax() {
        return this.qmax;
    }

    public int getServerId() {
        return this.serverId;
    }

    public double getEndTime() {
        return this.endTime;
    }

    public ImList<Customer> getQueue() {
        return this.queue;
    }

    public boolean isQueueAvailable() {
        return this.queue.size() < qmax;
    }

    public Server addQueue(Customer customer) {
        return new Server(serverId, endTime, queue.add(customer), qmax);
    }

    public Server removeQueue() {
        return new Server(serverId, endTime, queue.remove(0), qmax);
    }

    public Server serve(double serviceTime, Customer customer) {
        return new Server(serverId, customer.getArrivalTime() + serviceTime, queue, qmax);
    }

    public Server waitServe(double serviceTime) {
        return new Server(serverId, endTime + serviceTime, queue, qmax);
    }

    public Server rest(double restTime) {
        return new Server(serverId, endTime + restTime, queue, qmax);
    }

    public boolean isAvailable(Customer customer) {
        return customer.getArrivalTime() >= this.endTime;
    }

    @Override
    public String toString() {
        return String.format(" %d", this.serverId);
    }
}