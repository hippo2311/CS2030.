import java.util.Optional;

public class ServeEvent extends Event {
    private final Server server;

    public ServeEvent(Customer customer, Server server, double time) {
        super(customer, time);

        this.server = server;
    }

    public Pair<Event, Shop> next(Shop shop) {
        // Calculate the done time after service
        double doneTime = this.getTime() + this.getCustomer().getServiceTime();

        // Create the DoneEvent indicating service completion
        Event doneEvent = new DoneEvent(this.getCustomer(), server, doneTime);

        // Update the shop with the server's done state
        Server updatedServer = server.done();
        Shop updatedShop = shop.update(updatedServer);

        // Return the DoneEvent wrapped in Optional and the updated shop state
        return new Pair<>(doneEvent, updatedShop);
    }

    public String toString() {
        return String.format("%.1f %s served by %s", this.getTime(),
                             this.getCustomer(), server);
    }
}
