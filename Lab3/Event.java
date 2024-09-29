import java.util.Optional;

abstract class Event implements Comparable<Event> {
    private final double time;
    private final Customer customer;

    Event(Customer customer, double time) {
        this.customer = customer;
        this.time = time;
    }

    public double getTime() {
        return this.time;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public int compareTo(Event other) {
        if (this.getTime() < other.getTime()) {
            return -1;
        } else if (this.getTime() > other.getTime()) {
            return 1;
        } else {
            return Double.compare(customer.getId(), other.customer.getId());
        }
    }
    public abstract Pair<Event, Shop> next(Shop shop);

    public abstract String toString();
}
