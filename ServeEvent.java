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
        Event doneEvent = new DoneEvent(customer, doneTime, server);
        return new Pair<>(Optional.of(doneEvent), shop);
    }

    @Override
    public String toString() {
        return String.format("%.3f %s serves by %s", eventTime, customer,
                             server);
    }
}
