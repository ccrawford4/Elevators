package src;
import java.util.Random;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

public class Elevator {
    private float passengerRatio;
    private boolean up = true;
    private int currentFloor;

    private List<Passenger> passengers;
    private int numFloors;

    private List<Integer> times;


    Elevator (String structure) {
        if (structure.equals("linked")) {
            passengers = new LinkedList<>();
            this.times = new LinkedList<>();
        }
        else {
            passengers = new ArrayList<>();
            this.times = new ArrayList<>();
        }
        currentFloor = 0;

    }
    public int getCurrentFloor () {
        return currentFloor;
    }
    public boolean getDirection () {
        return up;
    }

    public void travel(int floor, int time) {
        currentFloor = floor;
        for (Passenger passenger : passengers) {
            if (passenger.getDestinationFloor() == currentFloor) {
                int timeDiff = time - passenger.getStartTime();
                times.add(timeDiff);
                passengers.remove(passenger);
            }
        }
    }

    public int getNextFloor() {
        int limit;
        if (up) {
            limit = Math.min(currentFloor + 5, numFloors);
        }
        else {
            limit = Math.max(currentFloor - 5, 1);
        }
        int nextFloor = limit;
        for (Passenger passenger : passengers) {
            if (up) {
                nextFloor = Math.min(nextFloor, passenger.getDestinationFloor());
            }
            else {
                nextFloor = Math.max(nextFloor, passenger.getDestinationFloor());
            }
        }
        return nextFloor;
    }


    public int getShortestTime () {
        int smallestTime = Integer.MAX_VALUE;
        for (Integer time : times) {
            smallestTime = Math.min(time, smallestTime);
        }
        return smallestTime;
    }

    public int getLongestTime() {
        int biggestTime = 0;
        for (Integer time : times) {
            biggestTime = Math.max(biggestTime, time);
        }
        return biggestTime;
    }

    public int getAverageTime() {
        int size = Math.max(times.size(), 1);
        int sum = 0;
        for (Integer time : times) {
            sum += time;
        }
        return sum / size;
    }


}
