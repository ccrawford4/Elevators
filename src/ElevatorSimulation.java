package src;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ElevatorSimulation {
    private List<Elevator> elevators;
    private List<Floor> floors;
    private float passengerRatio;

    private int numFloors;
    private int capacity;
    // SIMULATION:
    // --> generate new passenger based on ratio
    // check direction (using getDirection)
    // check min or max floor --> getNextPossibleFloor();
    // findNextFloor(direction)
        // --> check floors for max or min
    // goToNextFloor(nextFloor);

    // getCapacity() --> get number of remaining spots
    // communicate to the floor of said index (iterate through the queue)

    // ELEVATOR:
    // getDirection --> will return the direction
    // check minimum floor it has to visit return to simulation
    // travel()
    // store times

    ElevatorSimulation(int numElevators, String structure, float passengerRatio, int numFloors, int capacity) {
        if (structure.equals("linked")) {
            this.elevators = new LinkedList<>();
            this.floors = new LinkedList<>();
        }
        else {
            this.elevators = new ArrayList<>();
            this.floors = new ArrayList<>();
        }
        this.passengerRatio = passengerRatio;
        this.numFloors = numFloors;
        this.capacity = capacity;
        for (int i = 0; i <= numFloors; i++) {
            floors.add(new Floor(structure));
        }
        for (int i=0; i < numElevators; i++) {
            Elevator elevator = new Elevator(structure);
            this.elevators.add(elevator);
        }
    }

    public void newPassenger(int startTime, boolean up) {
        Random random = new Random();
        float randomValue = random.nextFloat();
        if (randomValue <= passengerRatio) {
            int randomFloor = random.nextInt(1, this.numFloors+1);
            Passenger passenger = new Passenger(startTime, randomFloor);
            floors.get(randomFloor).newPassenger(passenger, up);
        }
    }

    public int getNextFloor(int currentFloor, boolean up) {
        int ratio = up ? 1 : -1;
        for (int i=currentFloor; i >=0 && i < floors.size(); i += ratio) {
            if (floors.get(i).containsPassenger(up)) {
                return i;
            }
        }
        return currentFloor;
    }

    public void runSimulation(int duration) {
        for (int tick = 0; tick < duration; tick++) {
            for (Elevator elevator : elevators) {
                boolean up = elevator.getDirection();
                newPassenger(tick, up);
                int passengersOnElevator = elevator.getNextFloor(numFloors); // 0
                int currentFloor = elevator.getCurrentFloor(); // 1
                int passengersWaiting = getNextFloor(currentFloor, up); // waiting on floor 44
                int nextFloor;
                if (up) {
                    nextFloor = Math.min(passengersOnElevator, passengersWaiting);
                }
                else {
                    nextFloor = Math.max(passengersOnElevator, passengersWaiting);
                }
                int remainingSpace = capacity - elevator.getNumberOfPassengers();
                while (floors.get(nextFloor).containsPassenger(up) && remainingSpace > 0) {
                    Passenger passenger = floors.get(nextFloor).loadPassenger(up);
                    elevator.addPassenger(passenger);
                }
                elevator.travel(nextFloor, tick, numFloors);
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
