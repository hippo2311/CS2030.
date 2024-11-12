import java.util.Optional;

class DoneEvent extends Event {
    private final Server server;

    DoneEvent(Customer customer, double eventTime, Server server) {
        super(eventTime, customer);
        this.server = server;
    }

    public Pair<Optional<Event>, Shop> next(Shop shop) {
        // Finish serving the current customer
        Server updatedServer = server.finishServing();

        // Update the shop with the server's new state
        Shop updatedShop = shop.updateServer(updatedServer);

        // No next event is created here if the server becomes idle
        return new Pair<>(Optional.empty(), updatedShop);
    }

    public boolean isTerminal() {
        return true;    // This is a terminal event
    }

    @Override
    public String toString() {
        return String.format("%.3f %s done", eventTime, customer);
    }
}
