/**
 * Abstract class representing a generic Room.
 * Demonstrates Encapsulation and Abstraction.
 */
abstract class Room {
    private String type;
    private double price;

    public Room(String type, double price) {
        this.type = type;
        this.price = price;
    }

    public String getType() { return type; }
    public double getPrice() { return price; }

    public abstract void displayFeatures();
}

/**
 * Concrete implementations showing Inheritance.
 */
class SingleRoom extends Room {
    public SingleRoom() { super("Single Room", 100.0); }
    @Override
    public void displayFeatures() { System.out.println("Features: 1 Single Bed, Wifi, AC"); }
}

class DoubleRoom extends Room {
    public DoubleRoom() { super("Double Room", 180.0); }
    @Override
    public void displayFeatures() { System.out.println("Features: 1 King Bed, Wifi, AC, Mini-Bar"); }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super("Suite Room", 350.0); }
    @Override
    public void displayFeatures() { System.out.println("Features: 2 Rooms, Living Area, Wifi, AC, Ocean View"); }
}

/**
 * The BookMyStay class serves as the entry point.
 * Refactored to Version 2.0 for Use Case 2.
 */
public class BookMyStay {

    public static void main(String[] args) {
        System.out.println("Welcome to the Hotel Booking Management System!");
        System.out.println("Application: Book My Stay App");
        System.out.println("Version: 2.0\n");

        // Static Availability using individual variables
        int singleRoomAvailability = 5;
        int doubleRoomAvailability = 3;
        int suiteRoomAvailability = 2;

        // Initializing room objects and using Polymorphism
        Room[] rooms = { new SingleRoom(), new DoubleRoom(), new SuiteRoom() };

        System.out.println("--- Current Room Availability ---");

        for (Room room : rooms) {
            System.out.println("Room Type: " + room.getType());
            System.out.println("Price: $" + room.getPrice());
            room.displayFeatures();

            // Displaying availability from static variables
            if (room instanceof SingleRoom)
                System.out.println("Available Units: " + singleRoomAvailability);
            else if (room instanceof DoubleRoom)
                System.out.println("Available Units: " + doubleRoomAvailability);
            else if (room instanceof SuiteRoom)
                System.out.println("Available Units: " + suiteRoomAvailability);

            System.out.println("---------------------------------");
        }
    }
}
