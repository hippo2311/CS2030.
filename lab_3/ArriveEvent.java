import java.util.Optional;

class ArriveEvent extends Event {
    ArriveEvent(Customer customer, double eventTime) {
        super(eventTime, customer);
    }

    @Override
    public Pair<Optional<Event>, Shop> next(Shop shop) {
        Optional<Server> idleServerOpt = shop.findIdleServer();
        Optional<Server> serverWithQueueSpaceOpt =
            shop.findServerWithQueueSpace();

        if (idleServerOpt.isPresent()) {
            // If there is an idle server, serve the customer immediately
            Server idleServer = idleServerOpt.get();
            double serviceTime = shop.getServiceTime().get();
            Server updatedServer =
                idleServer.serve(customer, serviceTime).orElse(idleServer);
            Event serveEvent =
                new ServeEvent(customer, eventTime, updatedServer, serviceTime);
            Shop updatedShop = shop.updateServer(updatedServer);
            return new Pair<>(Optional.of(serveEvent), updatedShop);

        } else if (serverWithQueueSpaceOpt.isPresent()) {
            // If no idle server is found but there is space in a queue, wait
            Server serverWithQueueSpace = serverWithQueueSpaceOpt.get();
            Server updatedServer = serverWithQueueSpace.addToQueue();
            Event waitEvent = new WaitEvent(customer, eventTime, updatedServer);
            Shop updatedShop = shop.updateServer(updatedServer);
            return new Pair<>(Optional.of(waitEvent), updatedShop);

        } else {
            // If no idle server or queue space is available, customer leaves
            Event leaveEvent = new LeaveEvent(customer, eventTime);
            return new Pair<>(Optional.of(leaveEvent), shop);
        }
    }

    @Override
    public String toString() {
        return String.format("%.3f %s arrives", eventTime, customer);
    }
}
