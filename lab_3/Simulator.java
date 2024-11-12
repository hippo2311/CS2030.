import java.util.List;
import java.util.stream.Stream;

class Simulator {
    private final int numOfServers;
    private final int numOfCustomers;
    private final  List<Pair<Integer,Pair<Double,Double>>> arrivals;

    Simulator(int numOfServers, int numOfCustomers, List<Pair<Integer,Pair<Double,Double>>> arrivals) {
        this.numOfServers = numOfServers;
        this.numOfCustomers = numOfCustomers;
        this.arrivals = arrivals;
    }

    State run() {
        Shop shop = new Shop(numOfServers);
        PQ<Event> pq = new PQ<Event>();
        for (Pair<Integer,Pair<Double,Double>> customerPair : this.arrivals) {
            pq = pq.add(new ArriveEvent(
                new Customer(customerPair.t(), customerPair.u().t(), customerPair.u().u()),
                customerPair.u().t()));
        }
        return Stream.iterate(new State(pq, shop),
            state -> !state.isEmpty(),
            state -> state.next())
            .reduce(new State(pq, shop), (x, y) -> y);
    }
}
