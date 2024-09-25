public class Customer {
    private final int id;
    private final double arrivalTime;
    private final double serviceTime;
    private static final double THRESHOLD = 1E-15;

    public Customer(int id, double arrivalTime, double serviceTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    // Add necessary getter methods
    public double getArrivalTime() {
        return arrivalTime;
    }

    public double getServiceTime() {
        return serviceTime;
    }

    public boolean canBeServed(double time) {
        return this.arrivalTime - time >= -THRESHOLD;
    }
    public double serveTill(double serviceTime) {
        return serviceTime + this.arrivalTime;
    }

    @Override
    public String toString() {
        return "customer " + id;
    }
}
