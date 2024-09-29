import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Shop {
    private final List<Server> servers;

    Shop(int numberOfServers) {
        this.servers = IntStream.range(0, numberOfServers)
                           .mapToObj(i -> new Server(i + 1))
                           .toList();
    }

    private Shop(List<Server> updatedServers) {
        this.servers = updatedServers;
    }

    private Stream<Server> getServers() {
        return servers.stream();
    }

    public Optional<Server> findServer(Customer customer) {
        return getServers()
            .filter(server -> server.canServe(customer))
            .findFirst();
    }

    public Shop update(Server newServer) {
        List<Server> updatedServers =
            getServers()
                .map(server -> server.isSame(newServer) ? newServer : server)
                .toList();
        return new Shop(updatedServers);
    }

    @Override
    public String toString() {
        List<String> serverStrings =
            getServers().map(server -> server.toString()).toList();
        return "[" + String.join(", ", serverStrings) + "]";
    }
}
