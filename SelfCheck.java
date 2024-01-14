class SelfCheck extends Server {
    private final int mainId;

    SelfCheck(int serverId, double endTime, ImList<Customer> queue, int qmax, int mainId) {
        super(serverId, endTime, queue, qmax);
        this.mainId = mainId;   
    }

    @Override
    public Server removeQueue() {
        return new SelfCheck(getServerId(), getEndTime(), getQueue().remove(0), getQmax(),
            this.mainId);
    }

    @Override
    public Server addQueue(Customer customer) {
        return new SelfCheck(getServerId(), getEndTime(), getQueue().add(customer), getQmax(), 
            this.mainId);
    }

    @Override
    public Server serve(double serviceTime, Customer customer) {
        return new SelfCheck(getServerId(), customer.getArrivalTime() + serviceTime, getQueue(), 
            getQmax(), this.mainId);
    }

    @Override
    public Server waitServe(double serviceTime) {
        return new SelfCheck(getServerId(), getEndTime() + serviceTime, getQueue(), 
            getQmax(), this.mainId);
    }

    @Override
    public int getMainId() {
        return this.mainId;
    }

    @Override
    public Server rest(double restTime) {
        return this;
    }

    @Override
    public String toString() {
        return " self-check" + super.toString();
    }
}