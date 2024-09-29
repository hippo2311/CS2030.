import java.util.Optional;

public class ArriveEvent extends Event {

    public ArriveEvent(Customer customer, double time) {
        super(customer, time);
    }

    public Pair<Event, Shop> next(Shop shop) {
        Optional<Server> serverOpt = shop.findServer(this.getCustomer());

        return serverOpt
            .map(server -> {
                Event serveEvent =
                    new ServeEvent(this.getCustomer(), server, this.getTime());
                Server updatedServer = server.serve(this.getCustomer());
                Shop updatedShop = shop.update(updatedServer);

                return new Pair<>(serveEvent, updatedShop);
            })
            .orElseGet(() -> {
                // Create a LeaveEvent if no server is available
                Event leaveEvent =
                    new LeaveEvent(this.getCustomer(), this.getTime());
                // Return the LeaveEvent wrapped in an Optional and unchanged Shop
                return new Pair<>(leaveEvent, shop);
            });
    }

    public String toString() {
        return String.format("%.1f %s arrives", this.getTime(),
                             this.getCustomer());
    }
}
