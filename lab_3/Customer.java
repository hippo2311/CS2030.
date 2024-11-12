public class Customer {
    private final int id;
    private final double arrivalTime;
    private static final double THRESHOLD = 1E-15;

    public Customer(int id, double arrivalTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
    }

    public int getId() {
        return id;
    }

    public double getArrivalTime() {
        return this.arrivalTime;
    }

    public boolean canBeServed(double time) {
        return this.arrivalTime - time >= -THRESHOLD;
    }

    @Override
    public String toString() {
        return "customer " + id;
    }
}
