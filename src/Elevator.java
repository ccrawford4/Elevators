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
        }
        else {
            passengers = new ArrayList<>();
        }
        currentFloor = 0;
        this.passengerRatio = passengerRatio;
        this.numFloors = numFloors;

    }

    public List<Integer> travel(int tick) {
        int nextFloor = currentFloor + 5;
        List<Integer> times = new ArrayList<>();
        for (Passenger passenger : passengers) { // Go through the floors the first time
            if (passenger.destinationFloor == currentFloor) {
                int time = tick - passenger.startTime;
                times.add(time);
                passengers.remove(passenger);
                newPassenger(tick);
            }
            else {
                nextFloor = Math.min(nextFloor, passenger.destinationFloor);
            }
        }
        currentFloor = nextFloor;
        return times;
    }

    public void newPassenger(int startTime) {
        Random random = new Random();
        float randomValue = random.nextFloat();
        if (randomValue <= passengerRatio) {
            int randomFloor;
            if (passengers.isEmpty() || currentFloor == numFloors) { // if we are empty or at the end
                randomFloor = random.nextInt(1, numFloors);
                if (randomFloor < currentFloor) {
                    up = false;
                }
            }
            else if (up) { // if we are at the bottom
                randomFloor = random.nextInt(currentFloor, numFloors);
            }
            else {
                randomFloor = random.nextInt(1, currentFloor);
            }

            Passenger passenger = new Passenger(startTime, randomFloor);
            passengers.add(passenger);
        }
    }

    public int getSmallestTime () {
        int smallestTime = Integer.MAX_VALUE;
        for (Integer time : times) {
            smallestTime = Math.min(time, smallestTime);
        }
        return smallestTime;
    }

    public int getBiggestTime() {
        int biggestTime = 0;
        for (Integer time : times) {
            biggestTime = Math.max(biggestTime, time);
        }
        return biggestTime;
    }

    public int averageTime() {
        int size = times.size();
        int sum = 0;
        for (Integer time : times) {
            sum += time;
        }
        return sum / size;
    }





}
