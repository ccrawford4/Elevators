package src;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("ERROR! No command line argument found.");
            return;
        }
        PropertyMap propertymap = new PropertyMap(args[0]);
        propertymap.printMap();

    }
}
