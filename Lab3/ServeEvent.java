public class ServeEvent extends Event {
    private final Customer customer;
    private final Server server;

    public ServeEvent(Customer customer, Server server, double time) {
        super(time);
        this.customer = customer;
        this.server = server;
    }

    public Pair<Event, Shop> next(Shop shop) {
        // Create a DoneEvent after service is completed
        double doneTime = this.getTime() + customer.getServiceTime();
        Event doneEvent = new DoneEvent(customer, server, doneTime);
        Shop updatedShop = shop.update(server.serve(customer));

        return new Pair<>(doneEvent, updatedShop);
    }

    public String toString() {
        return String.format("%.1f %s served by %s", this.getTime(), customer,
                             server);
    }
}
