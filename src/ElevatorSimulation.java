package src;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

public class ElevatorSimulation {
    private int shortestTime;
    private int averageTime;
    private int longestTime;
    private int numFloors;

    private List<Elevator> elevators;
    ElevatorSimulation(int numElevators, String structure, float passengerRatio) {
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
    // 1 --> 2 --> 3 --> 4 --> 5 (passenger -> only gets on in this direction unless empty)
    // 1 --> 2 --> 3 --> 4 --> 5

    public void runSimulation(int duration) {
        for (int tick = 0; tick < duration; tick++) {
            for (Elevator elevator : elevators) {
                elevator.travel(tick);
            }
        }
    }

    public void getShortestTime() {
        int smallestTime = Integer.MAX_VALUE;
        for (Elevator elevator : elevators) {
            smallestTime = Math.min(smallestTime, elevator.getSmallestTime());
        }
    }




}
