import java.util.Optional;

class WaitEvent extends Event {
    private final Server server;

    WaitEvent(Customer customer, double eventTime, Server server) {
        super(eventTime, customer);
        this.server = server;
    }

    @Override
    public Pair<Optional<Event>, Shop> next(Shop shop) {
        if (server.canServe(customer)) {
            double serviceTime = shop.getServiceTime().get();
            Optional<Server> updatedServerOpt =
                server.serve(customer, serviceTime);

            if (updatedServerOpt.isPresent()) {
                Server updatedServer = updatedServerOpt.get();
                Event serveEvent = new ServeEvent(customer, eventTime,
                                                  updatedServer, serviceTime);
                Shop updatedShop = shop.updateServer(updatedServer);
                return new Pair<>(Optional.of(serveEvent), updatedShop);
            }
        }

        // Keep the customer in the queue if the server is still busy
        return new Pair<>(Optional.of(this), shop);
    }

    @Override
    public String toString() {
        return String.format("%.3f %s waits at %s", eventTime, customer,
                             server);
    }
}
