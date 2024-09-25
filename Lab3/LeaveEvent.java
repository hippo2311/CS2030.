public class LeaveEvent extends Event {
    private final Customer customer;

    public LeaveEvent(Customer customer, double time) {
        super(time);
        this.customer = customer;
    }

    public Pair<Event, Shop> next(Shop shop) {
        // No further events after a customer leaves
        return new Pair<>(this, shop);
    }

    public String toString() {
        return String.format("%.1f %s leaves", this.getTime(), customer);
    }
}
