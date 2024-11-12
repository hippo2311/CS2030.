import java.util.Optional;

class ServeEvent extends Event {
    private final Server server;
    private final double serviceTime;

    ServeEvent(Customer customer, double eventTime, Server server,
               double serviceTime) {
        super(eventTime, customer);
        this.server = server;
        this.serviceTime = serviceTime;
    }

    @Override
    public Pair<Optional<Event>, Shop> next(Shop shop) {
        double doneTime = eventTime + serviceTime;
        Server updatedServer = server.finishServing();
        Shop updatedShop = shop.updateServer(updatedServer);
        Event doneEvent = new DoneEvent(customer, doneTime, updatedServer);
        return new Pair<>(Optional.of(doneEvent), updatedShop);
    }

    @Override
    public String toString() {
        return String.format("%.3f %s serves by %s", eventTime, customer,
                             server);
    }
}
