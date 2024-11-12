import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.IntStream;

class Shop {
    // Properties
    private final int numOfServers;
    private final Supplier<Double> serviceTimeSupplier;
    private final List<Server> servers;

    // Constructor to initialize Shop with the number of servers and max queue length
    Shop(int numOfServers, Supplier<Double> serviceTimeSupplier,
         int maxQueueLength) {
        this.numOfServers = numOfServers;
        this.serviceTimeSupplier = serviceTimeSupplier;
        this.servers = IntStream.rangeClosed(1, numOfServers)
                           .mapToObj(id -> new Server(id, maxQueueLength))
                           .toList();
    }

    // Private constructor for immutability when updating servers
    private Shop(int numOfServers, Supplier<Double> serviceTimeSupplier,
                 List<Server> servers) {
        this.numOfServers = numOfServers;
        this.serviceTimeSupplier = serviceTimeSupplier;
        this.servers = servers;
    }

    // Finds an available server that is either idle or has queue space
    Optional<Server> findAvailableServer(double customerArrivalTime) {
        return this.servers.stream()
            .filter(server -> server.isIdle() || server.canQueue())
            .findFirst();    // Returns the first available server, if any
    }

    // Finds the first idle server
    Optional<Server> findIdleServer() {
        return this.servers.stream()
            .filter(server -> server.isIdle())
            .findFirst();
    }

    // Finds any server with available queue space
    public Optional<Server> findServerWithQueueSpace() {
        return this.servers.stream()
            .filter(server -> server.canQueue())
            .findFirst();
    }

    // Updates the state of a specific server, returning a new Shop instance
    Shop updateServer(Server updatedServer) {
        List<Server> newServerList =
            this.servers.stream()
                .map(server
                     -> server.getId() == updatedServer.getId() ? updatedServer
                                                                : server)
                .toList();
        return new Shop(this.numOfServers, this.serviceTimeSupplier,
                        newServerList);
    }

    // Returns the service time supplier
    public Supplier<Double> getServiceTime() {
        return this.serviceTimeSupplier;
    }

    @Override
    public String toString() {
        return this.servers.toString();
    }
}
