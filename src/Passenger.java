package src;

import java.util.Random;

public class Passenger implements Comparable<Passenger>{
    private final int startTime;
    private final int destinationFloor;
    Passenger(int startTime, int destinationFloor) {
        this.startTime = startTime;
        this.destinationFloor = destinationFloor;
    }

    public int getStartTime() {
        return startTime;
    }

    @Override
    public int compareTo(Passenger other) {
        return Integer.compare(this.destinationFloor, other.destinationFloor);
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }
}
