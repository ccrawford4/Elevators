package src;
import java.util.Queue;
import java.util.ArrayDeque;
import java.util.LinkedList;

public class Floor {
    private Queue<Passenger> up;
    private Queue<Passenger> down;

    Floor(String structure) {
        if (structure.equals("linked")) {
            up = new LinkedList<>();
            down = new LinkedList<>();
        }
        else {
            up = new ArrayDeque<>();
            down = new ArrayDeque<>();
        }
    }

    boolean containsPassenger(boolean up) {
        if (up) {
            return !this.up.isEmpty();
        }
        return !down.isEmpty();
    }

    public void newPassenger(Passenger passenger, boolean up) {
        if (up) {
            this.up.add(passenger);
        }
        else {
            down.add(passenger);
        }
    }

}
