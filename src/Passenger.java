package src;
public class Passenger implements Comparable<Passenger>{
    private int startTime = 0;
    private final int startFloor;
    private final int destinationFloor;
    Passenger(int startTime, int startFloor, int destinationFloor) {
        this.startTime = startTime;
        this.destinationFloor = destinationFloor;
        this.startFloor = startFloor;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getStartFloor() {return startFloor;}

    @Override
    public int compareTo(Passenger other) {
        return Integer.compare(this.destinationFloor, other.destinationFloor);
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }
}
