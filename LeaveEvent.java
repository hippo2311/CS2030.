
import java.util.Optional;

class LeaveEvent extends Event {
    LeaveEvent(Customer customer, double eventTime) {
        super(eventTime, customer);
    }

    @Override
    public Pair<Optional<Event>, Shop> next(Shop shop) {
        // This is a terminal event; no follow-up event
        return new Pair<>(Optional.empty(), shop);
    }

    @Override
    public boolean isTerminal() {
        return true;
    }

    int getNumberServed() {
        return 0;
    }

    int getNumberLeft() {
        return 1;
    }

    int getNumWait() {
        return 0;
    }

    double waitingTime(Shop shop) {
        return 0.0;
    }

    @Override
    public String toString() {
        return String.format("%.3f %s leaves", eventTime, customer);
    }
}
