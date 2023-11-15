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


    Elevator (ElevatorSimulation simulation, int capacity) {
        this.goingUp = new PriorityQueue<>(); // Min Heap
        this.goingDown = new PriorityQueue<>(Comparator.reverseOrder()); // Max heap
        this.requestedFloorsUp = new PriorityQueue<>(); // Min Heap
        this.requestedFloorsDown = new PriorityQueue<>(Comparator.reverseOrder()); // Max Heap
        this.capacity = capacity;
        this.up = true;
        this.simulation = simulation;
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
            }
            else {
                goingUp.add(peopleWaiting.remove());
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

   private int findOnBoardRequest(PriorityQueue<Passenger> onBoardQueue, boolean up) {
        int nextFloor = (up) ? currentFloor + 5 : currentFloor - 5;
        return (!onBoardQueue.isEmpty()) ? onBoardQueue.peek().getDestinationFloor() : Integer.MAX_VALUE;
   }
   private int findOffBoardRequest(PriorityQueue<Integer> offBoardQueue) {
        return (!offBoardQueue.isEmpty()) ? offBoardQueue.peek() : Integer.MAX_VALUE;
   }

    public void travel() {
        System.out.println("Current Floor before: " + currentFloor);
        int maxFloor = currentFloor + 5;
        int minFloor = currentFloor - 5;

        int onBoardRequest = (up) ? findOnBoardRequest(goingUp, up) : findOnBoardRequest(goingDown, up);
        int offBoardRequest = (up) ? findOffBoardRequest(requestedFloorsUp) : findOffBoardRequest(requestedFloorsDown);
        int priorityRequest = (up) ? Math.min(onBoardRequest, offBoardRequest) : Math.max(onBoardRequest, offBoardRequest);

        currentFloor = (up) ? Math.min(maxFloor, priorityRequest) : Math.max(minFloor, priorityRequest);

        System.out.println("Current Floor After: " + currentFloor);
    }

}
