import java.util.Optional;
abstract class Event implements Comparable<Event> {
    public final double time;

    Event(double time) {
        this.time = time;
    }

    public double getTime() {
        return this.time;
    }

    public int compareTo(Event other) {
        if (this.getTime() < other.getTime()) {
            return -1;
        } else if (this.getTime() > other.getTime()) {
            return 1;
        } else {
            return 0;
        }
    }

    public abstract Pair<Event, Shop> next(Shop shop);

    @Override public abstract String toString();
}
