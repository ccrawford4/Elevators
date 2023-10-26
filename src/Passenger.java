package src;

import java.util.Random;

public class Passenger {
    private int id;
    private int startTime;
    private int destinationFloor;
    Passenger(int startTime, int destinationFloor) {
        Random random = new Random(0);
        id = random.nextInt(0, 100000);
        this.startTime = startTime;
        this.destinationFloor = destinationFloor;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }
}
