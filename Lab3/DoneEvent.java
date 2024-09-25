public class DoneEvent extends Event {
    private final Customer customer;
    private final Server server;

    public DoneEvent(Customer customer, Server server, double time) {
        super(time);
        this.customer = customer;
        this.server = server;
    }

    public Pair<Event, Shop> next(Shop shop) {
        // No further events after a customer is done
        return new Pair<>(this, shop);
    }

    public String toString() {
        return String.format("%.1f %s done", this.getTime(), customer);
    }
}
