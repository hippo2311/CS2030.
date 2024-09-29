import java.util.Optional;

public class LeaveEvent extends Event {

    public LeaveEvent(Customer customer, double time) {
        super(customer, time);
    }

    public Pair<Event, Shop> next(Shop shop) {
        return new Pair<>(this, shop);
    }

    public String toString() {
        return String.format("%.1f %s leaves", this.getTime(),
                             this.getCustomer());
    }
}
