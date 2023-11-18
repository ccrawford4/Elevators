package src;
public class Passenger implements Comparable<Passenger>{
    private int startTime = 0;
    private final int startFloor;
    private final int destinationFloor;

    /**
     * Constructor for the Passenger class
     *
     * @param startTime - Start time for the passenger passed as an integer
     * @param startFloor - Start floor for the passenger passed as an integer
     * @param destinationFloor - Destination floor for the passenger passed as an integer
     */
    Passenger(int startTime, int startFloor, int destinationFloor) {
        this.startTime = startTime;
        this.destinationFloor = destinationFloor;
        this.startFloor = startFloor;
    }

    /**
     * Returns the start time for the passenger
     *
     * @return - Integer representing the start time
     */
    public int getStartTime() {
        return startTime;
    }

    /**
     * Returns the start floor for the passenger
     *
     * @return - Integer representing for the passenger
     */
    public int getStartFloor() {return startFloor;}

    /**
     * Comparable method for Passenger objects
     *
     * @param other the object to be compared.
     * @return The integer comparable using the destination floor
     */
    @Override
    public int compareTo(Passenger other) {
        return Integer.compare(this.destinationFloor, other.destinationFloor);
    }

    /**
     * Returns the destination floor as an integer
     *
     * @return - Destination floor
     */
    public int getDestinationFloor() {
        return destinationFloor;
    }
}
