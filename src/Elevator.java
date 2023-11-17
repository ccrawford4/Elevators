package src;
import java.util.*;

public class Elevator {
    private boolean up;
    private int capacity;
    private int currentFloor;
    PriorityQueue<Passenger> goingUp;
    PriorityQueue<Passenger> goingDown;
    PriorityQueue<Integer> requestedFloorsUp;
    PriorityQueue<Integer> requestedFloorsDown;
    ElevatorSimulation simulation;

    int numFloors;


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
    public int getCurrentFloor () {
        return currentFloor;
    }
    public boolean goingUp () {
        return up;
    }

    public boolean full() {
        return (goingUp.size() + goingDown.size()) < capacity;
    }
   public void load(Queue<Passenger> peopleWaiting) {
        while (!peopleWaiting.isEmpty() && goingUp.size() + goingDown.size() < capacity) {
            if (peopleWaiting.peek().getDestinationFloor() < currentFloor) {
                goingDown.add(peopleWaiting.remove());
                requestedFloorsDown.remove();
            }
            else {
                goingUp.add(peopleWaiting.remove());
                requestedFloorsUp.remove();
            }
        }
   }

   public boolean requestToGetOff() {
        if (!goingUp.isEmpty() && goingUp.peek().getDestinationFloor() == currentFloor) {
            return true;
        }
       return !goingDown.isEmpty() && goingDown.peek().getDestinationFloor() == currentFloor;
   }

   public void unload(int currentTime) {
        while (!goingUp.isEmpty() && goingUp.peek().getDestinationFloor() == currentFloor) {
            Passenger passenger = goingUp.peek();
            simulation.getReport(currentTime - passenger.getStartTime());
            goingUp.remove();
        }
        while (!goingDown.isEmpty() && goingDown.peek().getDestinationFloor() == currentFloor) {
            goingDown.remove();
        }
   }

   public void requestStop(int requestedFloor) {
        if (goingUp.isEmpty() && goingDown.isEmpty()) {
            up = requestedFloor > currentFloor;
        }
        if (currentFloor < requestedFloor) {
            requestedFloorsUp.add(requestedFloor);
        }
        else {
            requestedFloorsDown.add(requestedFloor);
        }

   }

   public void printInfo() {
       System.out.println("------------------------------------------");
       System.out.println("Current Floor: " + currentFloor);
       System.out.println("Going up passengers: " + goingUp.size());
       System.out.println("Going down passenger: " + goingDown.size());
       System.out.println("Requested up passengers: " + requestedFloorsUp.size());
       System.out.println("Requested down passengers: " + requestedFloorsDown.size());
       System.out.println("-------------------------------------------");
   }

    public void travel(int tick, Queue<Passenger> people) {
        unload(tick);
        if (requestedFloorsUp.isEmpty() && goingUp.isEmpty()) {
            return;
        }
        int nextFloor = Math.min(currentFloor + 5, numFloors-1);
        int requestedFloor = requestedFloorsUp.isEmpty() ? nextFloor : requestedFloorsUp.peek();
        if (currentFloor == requestedFloor) {
            load(people);
        }
        int currentPassenger = goingUp.isEmpty() ? nextFloor : goingUp.peek().getDestinationFloor();
        int priorityRequest = Math.min(requestedFloor, currentPassenger);
        currentFloor = Math.min(nextFloor, priorityRequest);

    }

}
