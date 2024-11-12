import java.util.Optional;

abstract class Event implements Comparable<Event> {
    protected final double eventTime;
    protected final Customer customer;

    Event(double eventTime, Customer customer) {
        this.eventTime = eventTime;
        this.customer = customer;
    }

    public abstract Pair<Optional<Event>, Shop> next(Shop shop);

    public boolean isTerminal() {
        return false;
    }

    @Override
    public int compareTo(Event event) {
        if (this.eventTime == event.eventTime) {
            if (this.isTerminal() && event.isTerminal()) {
                return this.customer.getId() - event.customer.getId();
            } else if (!this.isTerminal() && event.isTerminal()) {
                return 1;
            } else if (this.isTerminal() && !event.isTerminal()) {
                return -1;
            } else {
                return this.customer.getId() - event.customer.getId();
            }
        } else {
            return Double.compare(this.eventTime, event.eventTime);
        }
    }
}
