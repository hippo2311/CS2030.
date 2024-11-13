    import java.util.Optional;

    class State {
        private final PQ<Event> pq;
        private final Shop shop;
        private final String logMessage;
        private final PQ<Event> lastPQ;

        // Constructor with initial empty PQ and log message
        State(Shop shop) {
            this.pq = new PQ<Event>();
            this.shop = shop;
            this.logMessage = "";
            this.lastPQ = new PQ<Event>();
        }

        // Constructor with PQ and Shop, and empty log message
        State(PQ<Event> pq, Shop shop) {
            this.pq = pq;
            this.shop = shop;
            this.logMessage = "";
            this.lastPQ = pq;
        }

        // Constructor with PQ, Shop, and a predefined log message
        State(PQ<Event> pq, Shop shop, String preLog) {
            this.pq = pq;
            this.shop = shop;
            this.logMessage = preLog;
            this.lastPQ = pq;
        }

        // Constructor with PQ, Shop, log message, and lastPQ
        State(PQ<Event> pq, Shop shop, String preLog, PQ<Event> lastPQ) {
            this.pq = pq;
            this.shop = shop;
            this.logMessage = preLog;
            this.lastPQ = lastPQ;
        }

        State next() {
            // Poll the priority queue for the next event, if available
            Optional<Event> priorEventOpt = this.pq.poll().t();

            // Generate a new log message based on the prior event, if it exists
            String newLogMessage =
                    this.logMessage +
                            priorEventOpt
                                    .map(event
                                            -> (this.logMessage.isEmpty() ? "" : "\n") +
                                            event.toString())
                                    .orElse("");

            // Set default values for newPQ and newShop
            PQ<Event> initialPQ = this.pq.poll().u();
            Shop initialShop = this.shop;

            // Determine updatedPQ based on whether there's a prior event and whether it is terminal
            PQ<Event> updatedPQ =
                    priorEventOpt
                            .map(event -> {
                                if (event.isTerminal()) {
                                    return initialPQ;    // Return initialPQ if event is terminal
                                } else {
                                    // For non-terminal events, get the next event and shop state
                                    Pair<Optional<Event>, Shop> nextPair =
                                            event.next(this.shop);
                                    Shop updatedShop =
                                            nextPair.u();    // Updated shop state

                                    // Return the updated PQ with the next event if present
                                    return nextPair.t()
                                            .map(initialPQ::add)
                                            .orElse(initialPQ);
                                }
                            })
                            .orElse(initialPQ);

            // Determine updatedShop based on whether prior event had a next state
            Shop updatedShop =
                    priorEventOpt
                            .flatMap(event
                                    -> event.isTerminal()
                                    ? Optional.empty()
                                    : Optional.of(event.next(this.shop).u()))
                            .orElse(initialShop);

            return new State(updatedPQ, updatedShop, newLogMessage, this.pq);
        }

        public boolean isEmpty() {
            return this.lastPQ.isEmpty();
        }

        public PQ<Event> getLastPQ() {
            return this.lastPQ;
        }

        @Override
        public String toString() {
            return this.logMessage;
        }
    }