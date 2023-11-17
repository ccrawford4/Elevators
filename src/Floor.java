package src;
import java.util.Queue;
import java.util.ArrayDeque;
import java.util.LinkedList;

public class Floor {
    private Queue<Passenger> up;
    private Queue<Passenger> down;

    private final int floorNumber;

    Floor(String structure, int floorNumber) {
        this.floorNumber = floorNumber;
        if (structure.equals("linked")) {
            up = new LinkedList<>();
            down = new LinkedList<>();
        }
        else {
            up = new ArrayDeque<>();
            down = new ArrayDeque<>();
        }
    }

    public Queue<Passenger> upQueue() {
        return up;
    }

    public Queue<Passenger> downQueue() {
        return down;
    }

    public boolean goingUp() {
        return !up.isEmpty();
    }

    public boolean goingDown() {
        return !down.isEmpty();
    }

    public void generatePassenger(int startTime, int destinationFloor) {
        if (destinationFloor > floorNumber) {
            up.add(new Passenger(startTime, destinationFloor));
        }
        else {
            down.add(new Passenger(startTime, destinationFloor));
        }
    }

   /* public void removePassengers(boolean up, int numToRemove) {
        if (up) {
            for (int i = 0; i < numToRemove; i++) {
                this.up.remove();
            }
        }
    }*/




}
