class Server {
    private final int idServer;
    private final double availableFromTime;

    Server(int idServer) {
        this.idServer = idServer;
        this.availableFromTime = 0.0;
    }

    private Server(int idServer, double availableFromTime) {
        this.idServer = idServer;
        this.availableFromTime = availableFromTime;
    }

    public boolean isSame(Server other) {
        return this.idServer == other.idServer;
    }

    public boolean canServe(Customer customer) {
        return customer.canBeServed(this.availableFromTime);
    }

    public Server serve(Customer customer) {

        if (canServe(customer)) {
            double serviceTime = customer.getServiceTime();
            double newAvailableTime =
                Math.max(this.availableFromTime, customer.getArrivalTime());
            return new Server(this.idServer, newAvailableTime + serviceTime);
        }
        return this;
    }

    public Server done() {
        return new Server(this.idServer, this.availableFromTime);
    }

    @Override
    public String toString() {
        return "server " + this.idServer;
    }
}
