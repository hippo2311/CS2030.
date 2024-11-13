import java.util.Optional;

class ArriveEvent extends Event {
    ArriveEvent(Customer customer, double eventTime) {
        super(eventTime, customer);
    }

    @Override
    public Pair<Optional<Event>, Shop> next(Shop shop) {
        // Check for an idle server first
        Optional<Server> idleServerOpt = shop.findIdleServer(customer);

        if (idleServerOpt.isPresent()) {
            // If an idle server is found, the customer is served immediately
            Server idleServer = idleServerOpt.get();
            double serviceTime = shop.getServiceTime().get();
            Server updatedServer =
                idleServer.serve(customer, serviceTime).orElse(idleServer);
            Event serveEvent =
                new ServeEvent(customer, eventTime, updatedServer, serviceTime);
            Shop updatedShop = shop.updateServer(updatedServer);
            return new Pair<>(Optional.of(serveEvent), updatedShop);

        } else {
            // No idle server, check if any server has queue space
            Optional<Server> serverWithQueueSpaceOpt =
                shop.findServerWithQueueSpace();

            if (serverWithQueueSpaceOpt.isPresent()) {
                // Server with queue space found; customer waits
                Server serverWithQueueSpace = serverWithQueueSpaceOpt.get();
                Server updatedServer = serverWithQueueSpace.addToQueue();
                Event waitEvent =
                    new WaitEvent(customer, eventTime, updatedServer);
                Shop updatedShop = shop.updateServer(updatedServer);
                return new Pair<>(Optional.of(waitEvent), updatedShop);

            } else {
                // No server with queue space available, customer leaves
                Event leaveEvent = new LeaveEvent(customer, eventTime);
                return new Pair<>(Optional.of(leaveEvent), shop);
            }
        }
    }

    @Override
    public String toString() {
        return String.format("%.3f %s arrives", eventTime, customer);
    }
}
