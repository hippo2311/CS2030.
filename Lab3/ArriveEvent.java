import java.util.Optional;

public class ArriveEvent extends Event {
    private final Customer customer;

    public ArriveEvent(Customer customer, double time) {
        super(time);
        this.customer = customer;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public Pair<Event, Shop> next(Shop shop) {
        // Find a server for the customer
        Optional<Server> serverOpt = shop.findServer(customer);

        if (serverOpt.isPresent()) {
            // Create a ServeEvent if a server is available
            Server server = serverOpt.get();
            Event serveEvent = new ServeEvent(customer, server, this.getTime());
            Server updatedServer = server.serve(customer);
            Shop updatedShop = shop.update(updatedServer);

            // Return the ServeEvent and updated Shop
            return new Pair<>(serveEvent, updatedShop);
        } else {
            // Create a LeaveEvent if no server is available
            Event leaveEvent = new LeaveEvent(customer, this.getTime());
            // Return the LeaveEvent and unchanged Shop
            return new Pair<>(leaveEvent, shop);
        }
    }
    public String toString() {
        return String.format("%.1f %s arrives", this.getTime(), customer);
    }
}
