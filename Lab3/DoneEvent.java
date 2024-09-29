import java.util.Optional;

public class DoneEvent extends Event {
    private final Server server;

    public DoneEvent(Customer customer, Server server, double time) {
        super(customer, time);
        this.server = server;
    }

    public Pair<Event, Shop> next(Shop shop) {
        return new Pair<>(this, shop);
    }

    public String toString() {
        return String.format("%.1f %s done", this.getTime(),
                             this.getCustomer());
    }
}
