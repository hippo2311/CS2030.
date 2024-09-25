import java.util.Optional;

public class State {
    private final PQ<Event> eventQueue;
    private final Shop shop;

    public State(PQ<Event> eventQueue, Shop shop) {
        this.eventQueue = eventQueue;
        this.shop = shop;
    }

    public State next() {
        // Debug print
        System.out.println("Current Event Queue: " + eventQueue);

        Pair<Optional<Event>, PQ<Event>> currentEventPair = eventQueue.poll();
        Optional<Event> currentEventOpt = currentEventPair.t();
        if (!currentEventOpt.isPresent()) {
            return this;    // No more events, return the current state
        }

        Event currentEvent = currentEventOpt.get();
        System.out.println("Processing Event: " + currentEvent);

        Pair<Event, Shop> result = currentEvent.next(shop);
        System.out.println("Next Event: " + result.t() +
                           ", Updated Shop: " + result.u());

        PQ<Event> updatedEventQueue = currentEventPair.u().add(result.t());
        return new State(updatedEventQueue, result.u());
    }

    @Override
    public String toString() {
        // Return the current event only, without listing future events
        return eventQueue.toString();
    }
}
