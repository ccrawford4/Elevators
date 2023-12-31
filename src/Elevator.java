package src;
import java.util.*;

public class Elevator {
    private boolean up;
    private int capacity;
    private int currentFloor;
    private PriorityQueue<Passenger> goingUp;
    private  PriorityQueue<Passenger> goingDown;
    private PriorityQueue<Passenger> requestedFloorsUp;
    private PriorityQueue<Passenger> requestedFloorsDown;
    ElevatorSimulation simulation;
    int numFloors;

    /**
     * Elevator class constructor
     *
     * @param simulation - Simulation class to be referenced
     * @param capacity - Elevator Capacity
     * @param numFloors - Number of floors
     */
    Elevator (ElevatorSimulation simulation, int capacity, int numFloors) {
        this.goingUp = new PriorityQueue<>(); // Min Heap
        this.goingDown = new PriorityQueue<>(Comparator.reverseOrder()); // Max heap
        this.requestedFloorsUp = new PriorityQueue<>(); // Min Heap
        this.requestedFloorsDown = new PriorityQueue<>(Comparator.reverseOrder()); // Max Heap
        this.capacity = capacity;
        this.up = true;
        this.simulation = simulation;
        this.numFloors = numFloors;
    }

    /**
     *
     * @return - # of floors
     */
    public int getCurrentFloor () {
        return currentFloor;
    }

    /**
     *
     * @return - boolean if the elevator is going up
     */
    public boolean goingUp () {
        return up;
    }

    /**
     * Loads the requested passengers onto the elevator
     */
   public void load() {
        if (up) {
            while (!requestedFloorsUp.isEmpty() && requestedFloorsUp.peek().getStartFloor() == currentFloor && goingUp.size() + goingDown.size() < capacity) {
                goingUp.add(requestedFloorsUp.remove());
            }
        }
        else {
            while (!requestedFloorsDown.isEmpty() && requestedFloorsDown.peek().getStartFloor() == currentFloor & goingUp.size() + goingDown.size() < capacity) {
                goingDown.add(requestedFloorsDown.remove());
            }
        }
   }

    /**
     * Removes the passengers from the elevator
     *
     * @param currentTime - Current time for calculation’s sake
     */
   public void unload(int currentTime) {
        while (!goingUp.isEmpty() && goingUp.peek().getDestinationFloor() == currentFloor) {
            Passenger passenger = goingUp.peek();
            if (passenger != null) {
                simulation.getReport(currentTime - passenger.getStartTime());
            }
            goingUp.remove();
        }
        while (!goingDown.isEmpty() && goingDown.peek().getDestinationFloor() == currentFloor) {
            Passenger passenger = goingUp.peek();
            if (passenger != null) {
                simulation.getReport(currentTime - passenger.getStartTime());
            }
            goingDown.remove();
        }
   }

    /**
     * Requests a stop
     *
     * @param passenger - Takes in the passenger that's waiting
     */
   public void requestStop(Passenger passenger) {
        if (currentFloor < passenger.getStartFloor()) {
            requestedFloorsUp.add(passenger);
        }
        else {
            requestedFloorsDown.add(passenger);
        }
   }

    /**
     * Prints elevator info - helpful for debugging
     */
   public void printInfo() {
       System.out.println("------------------------------------------");
       System.out.println("Current Floor: " + currentFloor);
       System.out.println("Going up passengers: " + goingUp.size());
       System.out.println("Going down passenger: " + goingDown.size());
       System.out.println("Requested up passengers: " + requestedFloorsUp.size());
       System.out.println("Requested down passengers: " + requestedFloorsDown.size());
       System.out.println("-------------------------------------------");
   }

    /**
     * Travel method to control where the elevator goes to next.
     *
     * @param tick - The current time
     */
    public void travel(int tick) {
        if (currentFloor == numFloors-1 || goingUp.isEmpty() && requestedFloorsUp.isEmpty()) {
            up = false;
        }
        if (currentFloor <= 1 || goingDown.isEmpty() && requestedFloorsDown.isEmpty()) {
            up = true;
        }
        if (!goingUp.isEmpty() && goingUp.peek().getDestinationFloor() == currentFloor) {
            unload(tick);
        }
        if (!goingDown.isEmpty() && goingDown.peek().getDestinationFloor() == currentFloor) {
            unload(tick);
        }

        int nextFloor;
        int requestedFloor;
        int currentPassenger;
        int priorityRequest;

        if (up) {
            nextFloor = Math.min(currentFloor + 5, numFloors - 1);
            requestedFloor = requestedFloorsUp.isEmpty() ? nextFloor : requestedFloorsUp.peek().getStartFloor();
            currentPassenger = goingUp.isEmpty() ? nextFloor : goingUp.peek().getDestinationFloor();
            priorityRequest = Math.min(requestedFloor, currentPassenger);
            currentFloor = Math.min(nextFloor, priorityRequest);
        }
        else {
            nextFloor = Math.max(currentFloor - 5, 0);
            requestedFloor = requestedFloorsDown.isEmpty() ? nextFloor : requestedFloorsDown.peek().getStartFloor();
            currentPassenger = goingDown.isEmpty() ? nextFloor : goingDown.peek().getDestinationFloor();
            priorityRequest = Math.max(requestedFloor, currentPassenger);
            currentFloor = Math.max(nextFloor, priorityRequest);
        }
        if (currentFloor == requestedFloor) {
            load();
        }
    }

}
