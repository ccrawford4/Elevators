package src;
import java.util.Random;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

public class Elevator {
    private float passengerRatio;
    boolean up = true;
    private int currentFloor;

    private List<Passenger> passengers;
    private int numFloors;

    private List<Integer> times;


    private class Passenger {
        private int id;
        private int startTime;
        private int destinationFloor;
        Passenger(int startTime, int destinationFloor) {
            Random random = new Random(0);
            id = random.nextInt(0, 100000);
            this.startTime = startTime;
            this.destinationFloor = destinationFloor;
        }

        public int getDestinationFloor() {
            return destinationFloor;
        }
    }

    Elevator (float passengerRatio, String structure, int numFloors) {
        if (structure.equals("linked")) {
            passengers = new LinkedList<>();
            this.times = new LinkedList<>();
        }
        else {
            passengers = new ArrayList<>();
            this.times = new ArrayList<>();
        }
        currentFloor = 0;
        this.passengerRatio = passengerRatio;
        this.numFloors = numFloors;

    }

    public void travel(int tick) {
        newPassenger(tick);
        if (passengers.isEmpty()) {
            return;
        }
        int nextFloor;
        if (up) {
            nextFloor = currentFloor + 5;
        }
        else {
            nextFloor = currentFloor - 5;
        }
        for (Passenger passenger : passengers) { // Go through the floors the first time
            if (passenger.getDestinationFloor() == currentFloor) {
                int time = tick - passenger.startTime;
                times.add(time);
                passengers.remove(passenger);
                break;
            }
            else {
                if (up) {
                    nextFloor = Math.min(nextFloor, passenger.getDestinationFloor());
                }
                else {
                    nextFloor = Math.max(nextFloor, passenger.getDestinationFloor());
                }

            }
        }
        if (up) {
            currentFloor = Math.min(nextFloor, numFloors);
        }
        else {
            currentFloor = Math.max(1, nextFloor+1);
        }
    }

    public void newPassenger(int startTime) {
        Random random = new Random();
        float randomValue = random.nextFloat();
        if (randomValue <= passengerRatio) {
            int randomFloor;
            if (currentFloor == numFloors) { // if we are empty or at the end
                randomFloor = random.nextInt(1, numFloors);
                up = false;
            }
            else if (up) {
                randomFloor = random.nextInt(currentFloor, numFloors);
            }
            else {
                randomFloor = random.nextInt(1, currentFloor);
            }

            Passenger passenger = new Passenger(startTime, randomFloor);
            passengers.add(passenger);
        }
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
