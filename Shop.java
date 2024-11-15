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

    // Finds the first idle server that can serve a customer based on arrival time
    Optional<Server> findIdleServer(Customer customer) {
        return this.servers.stream()
            .filter(server -> server.isIdle(customer))
            .findFirst();
    }

    // Finds an available server, either idle or with queue space
    Optional<Server> findAvailableServer(Customer customer) {
        return this.servers.stream()
            .filter(server -> server.isIdle(customer) || server.canQueue())
            .findFirst();    // Returns the first available server, if any
    }

    // Finds any server with available queue space
    public Optional<Server> findServerWithQueueSpace() {
        return this.servers.stream().filter(Server::canQueue).findFirst();
    }

    // Updates the state of a specific server and returns a new Shop instance
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
