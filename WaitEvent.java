import java.util.Optional;

class WaitEvent extends Event {
    private final Server server;

    WaitEvent(Customer customer, double eventTime, Server server) {
        super(eventTime, customer);
        this.server = server;
    }

    @Override
    public Pair<Optional<Event>, Shop> next(Shop shop) {
        // Remove the customer from the queue first
        Server dequeuedServer = server.removeFromQueue();

        // Check if the server is available to serve the dequeued customer
        if (dequeuedServer.isIdle(customer)) {
            double serviceTime = shop.getServiceTime().get();
            Optional<Server> updatedServerOpt = dequeuedServer.serve(customer, serviceTime);

            if (updatedServerOpt.isPresent()) {
                Server updatedServer = updatedServerOpt.get();
                Event serveEvent = new ServeEvent(customer, eventTime, updatedServer, serviceTime);
                Shop updatedShop = shop.updateServer(updatedServer);
                return new Pair<>(Optional.of(serveEvent), updatedShop);
            }
        }

        // If the server is still busy, reschedule the wait event to the next available time
        double nextCheckTime = dequeuedServer.getNextAvailableTime();
        Event rescheduledWaitEvent = new WaitEvent(customer, nextCheckTime, dequeuedServer);
        Shop updatedShop = shop.updateServer(dequeuedServer.addToQueue());
        return new Pair<>(Optional.of(rescheduledWaitEvent), updatedShop);
    }

    @Override
    public String toString() {
        return String.format("%.3f %s waits at %s", eventTime, customer, server);
    }
}
