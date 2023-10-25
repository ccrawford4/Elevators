package src;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

public class ElevatorSimulation {
    private int shortestTime;
    private int averageTime;
    private int longestTime;
    private int numFloors;

    private List<Elevator> structure;
    ElevatorSimulation(int numElevators, String structure, float passengerRatio) {
        if (structure.equals("linked")) {
            this.structure = new LinkedList<>();
        }
        else {
            this.structure = new ArrayList<>();
        }

        for (int i=0; i < numElevators; i++) {
            Elevator elevator = new Elevator(passengerRatio, structure, numFloors);
            this.structure.add(elevator);
        }
    }
    // 1 --> 2 --> 3 --> 4 --> 5 (passenger -> only gets on in this direction unless empty)
    // 1 --> 2 --> 3 --> 4 --> 5

    public void runSimulation(int duration) {
        for (int tick = 0; tick < duration; tick++) {
            for (Elevator elevator : structure) {
                elevator.travel(tick);
            }
            // elevator unload and load
            // elevator travel
            // new passengers
        }
    }



}
