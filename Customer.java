import java.util.function.Supplier;

class Customer {
    private final int custId;
    private final double arrivalTime;
    private final Supplier<Double> serviceTimes;

    Customer(int custId, double arrivalTime, Supplier<Double> serviceTimes) {
        this.custId = custId;
        this.arrivalTime = arrivalTime;
        this.serviceTimes = serviceTimes;
    }

    public int getCustId() {
        return this.custId;
    }

    public double getArrivalTime() {
        return this.arrivalTime;
    }

    public double getServiceTime() {
        return this.serviceTimes.get();
    }

    @Override
    public String toString() {
        return String.format(" %d", this.custId);
    }
}
