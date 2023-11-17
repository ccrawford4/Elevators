package src;
import java.util.*;

public class ElevatorSimulation {
    private List<Elevator> elevators;
    private List<Floor> floors;
    private float passengerRatio;
    List<Integer> times;
    private int maxTime = 0;
    private int minTime = Integer.MAX_VALUE;

    ElevatorSimulation(int numElevators, String structure, float passengerRatio, int numFloors, int capacity) {
        if (structure.equals("linked")) {
            this.elevators = new LinkedList<>();
            this.floors = new LinkedList<>();
            this.times = new LinkedList<>();
        }
        else {
            this.elevators = new ArrayList<>();
            this.floors = new ArrayList<>();
            this.times = new ArrayList<>();
        }
        this.passengerRatio = passengerRatio;
        for (int i = 1; i <= numFloors; i++) {
            floors.add(new Floor(structure, i));
        }
        for (int i=0; i < numElevators; i++) {
            Elevator elevator = new Elevator(this, capacity);
            this.elevators.add(elevator);
        }
    }

    public int randomFloor() {
        Random random = new Random();
        return random.nextInt(1, floors.size());
    }
    public float randomFloat() {
        Random random = new Random();
        return random.nextFloat(0, 1);
    }
   public void runSimulation(int ticks) {
        for (int tick = 0; tick < ticks; tick++) {
            if (randomFloat() < passengerRatio) {
                int startFloor = randomFloor();
                int destinationFloor = randomFloor();
                floors.get(startFloor).generatePassenger(tick, destinationFloor);
                Elevator closestElevator = findBestElevator(startFloor);
                closestElevator.requestStop(startFloor);
            }
            for (Elevator elevator : elevators) {
                //elevator.printInfo();
                int currentFloor = elevator.getCurrentFloor();
                Queue<Passenger> floorQueue = floors.get(currentFloor).upQueue();
                elevator.travel(tick, floorQueue);
            }
        }
   }

   public Elevator findBestElevator(int startFloor) {
        Elevator bestElevator = elevators.get(0);
        int minDistance = Math.abs(bestElevator.getCurrentFloor() - startFloor);

        for (Elevator elevator : elevators) {
            int distance = Math.abs(elevator.getCurrentFloor() - startFloor);

            if (elevator.goingUp() && startFloor < elevator.getCurrentFloor()) {
                distance += startFloor - elevator.getCurrentFloor();
            }
            else if (!elevator.goingUp() && startFloor > elevator.getCurrentFloor()) {
                distance += startFloor - elevator.getCurrentFloor();
            }

            if (distance < minDistance) {
                minDistance = distance;
                bestElevator = elevator;
            }
        }
        return bestElevator;
   }

    public void getReport(int time) {
        minTime = Math.min(time, minTime);
        maxTime = Math.max(time, maxTime);
        times.add(time);
    }

    public int getShortestTime() {
        return minTime;
    }

    public int getLongestTime() {
        return maxTime;
    }

    public int getAverageTime() {
        int sum = 0;
        for (Integer time : times) {
            sum += time;
        }
        return sum / times.size();
    }

}
