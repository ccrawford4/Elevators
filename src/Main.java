package src;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("ERROR! No command line argument found.");
            return;
        }
        PropertyMap propertymap = new PropertyMap(args[0]);
        int numElevators = (Integer) propertymap.get("elevators");
        String structure =  (String) propertymap.get("structures");
        float passengerRatio = (Float) propertymap.get("passengers");
        int numFloors = (Integer) propertymap.get("floors");
        ElevatorSimulation simulation = new ElevatorSimulation(numElevators, structure, passengerRatio, numFloors);
        int duration = (Integer) propertymap.get("duration");
        simulation.runSimulation(duration);
        System.out.println("Shortest Time: " + simulation.getShortestTime());
        System.out.println("Longest Time: " + simulation.getLongestTime());
        System.out.println("Average Time: " + simulation.getAverageTime());
    }

}

