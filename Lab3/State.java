import java.util.List;
import java.util.stream.Stream;

public class State {
    private final PQ<Event> eventQueue;
    private final Shop shop;
    private final List<String> log;

    public State(PQ<Event> eventQueue, Shop shop) {
        this(eventQueue, shop, List.of());
    }

    private State(PQ<Event> eventQueue, Shop shop, List<String> log) {
        this.eventQueue = eventQueue;
        this.shop = shop;
        this.log = log;
    }

    public State next() {
        // Poll the next event from the queue and get Pair<Event, PQ<Event>>
        Pair<Event, PQ<Event>> currentEventPair = eventQueue.poll();
        Event currentEvent = currentEventPair.t();    // Get the current event
        PQ<Event> updatedEventQueue =
            currentEventPair.u();    // Get the remaining events

        // Update the log with the current event's description
        List<String> updatedLog =
            Stream.concat(log.stream(), Stream.of(currentEvent.toString()))
                .toList();

        // Get the next event and updated shop state from currentEvent's next method
        Pair<Event, Shop> nextEventPair = currentEvent.next(shop);

        // Add the new event to the event queue
        PQ<Event> finalEventQueue = updatedEventQueue.add(nextEventPair.t());

        // Return the new state with the updated event queue, shop, and log
        return new State(finalEventQueue, nextEventPair.u(), updatedLog);
    }

    public boolean isEmpty() {
        return eventQueue.isEmpty();
    }

    @Override
    public String toString() {
        return String.join("\n", log);
    }

    public void printLog() {
        log.forEach(System.out::println);
    }
}
