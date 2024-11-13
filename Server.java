import java.util.Optional;

class Server {
    private final int id;
    private final int maxQueueLength;
    private final boolean idle;
    private final double nextAvailableTime;
    private final int queueLength;

    // Constructor to initialize a server
    Server(int id, int maxQueueLength) {
        this.id = id;
        this.maxQueueLength = maxQueueLength;
        this.idle = true;
        this.nextAvailableTime = 0.0;
        this.queueLength = 0;
    }

    // Private constructor for creating updated server instances
    private Server(int id, int maxQueueLength, boolean idle,
                   double nextAvailableTime, int queueLength) {
        this.id = id;
        this.maxQueueLength = maxQueueLength;
        this.idle = idle;
        this.nextAvailableTime = nextAvailableTime;
        this.queueLength = queueLength;
    }

    public int getId() {
        return this.id;
    }

    // Check if the server is idle, considering the arrival time of the customer
    public boolean isIdle(Customer customer) {
        return nextAvailableTime <= customer.getArrivalTime() &&
            queueLength == 0;
    }

    public boolean canServe(Customer customer) {
        return isIdle(customer) ||
            nextAvailableTime <= customer.getArrivalTime();
    }

    // Serve a customer and update the server's state
    public Optional<Server> serve(Customer customer, double serviceTime) {
        double endTime =
            Math.max(customer.getArrivalTime(), nextAvailableTime) +
            serviceTime;

        if (isIdle(customer)) {
            return Optional.of(
                new Server(id, maxQueueLength, false, endTime, queueLength));
        } else if (queueLength > 0) {
            return Optional.of(new Server(id, maxQueueLength, false, endTime,
                                          queueLength - 1));
        }
        return Optional.of(this);
    }

    public double getNextAvailableTime() {
        return nextAvailableTime;
    }

    public boolean canQueue() {
        return queueLength < maxQueueLength;
    }

    public Server removeFromQueue() {
        if (queueLength > 0) {
            return new Server(id, maxQueueLength, idle, nextAvailableTime,
                              queueLength - 1);
        }
        return this;    // If no one is in the queue, return the server as is
    }

    public Server addToQueue() {
        if (canQueue()) {
            return new Server(id, maxQueueLength, idle, nextAvailableTime,
                              queueLength + 1);
        }
        return this;
    }

    public Server finishServing() {
        if (queueLength > 0) {
            return new Server(id, maxQueueLength, false, nextAvailableTime,
                              queueLength - 1);
        }
        return new Server(id, maxQueueLength, true, nextAvailableTime, 0);
    }

    public boolean hasQueuedCustomer() {
        return queueLength > 0;
    }

    @Override
    public String toString() {
        return "server " + id;
    }
}
