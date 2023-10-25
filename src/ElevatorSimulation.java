package src;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

public class ElevatorSimulation {
    private List<Elevator> elevators;
    ElevatorSimulation(int numElevators, String structure, float passengerRatio, int numFloors) {
        if (structure.equals("linked")) {
            this.elevators = new LinkedList<>();
        }
        else {
            this.elevators = new ArrayList<>();
        }
        for (int i=0; i < numElevators; i++) {
            Elevator elevator = new Elevator(passengerRatio, structure, numFloors);
            this.elevators.add(elevator);
        }
    }

    public void runSimulation(int duration) {
        for (int tick = 0; tick < duration; tick++) {
            for (Elevator elevator : elevators) {
                elevator.travel(tick);
            }
        }
    }

    public int getShortestTime() {
        int smallestTime = Integer.MAX_VALUE;
        for (Elevator elevator : elevators) {
            smallestTime = Math.min(smallestTime, elevator.getShortestTime());
        }
        return smallestTime;
    }

    public int getLongestTime() {
        int largestTime = 0;
        for (Elevator elevator : elevators) {
            largestTime = Math.max(largestTime, elevator.getLongestTime());
        }
        return largestTime;
    }

    public int getAverageTime() {
        int sum = 0;
        for (Elevator elevator : elevators) {
            sum += elevator.getAverageTime();
        }
        return sum / elevators.size();
    }

}
