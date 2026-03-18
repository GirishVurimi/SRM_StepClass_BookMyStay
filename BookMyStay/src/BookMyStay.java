import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class representing a generic Room (Domain Model).
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

class SingleRoom extends Room {
    public SingleRoom() { super("Single Room", 100.0); }
    @Override
    public void displayFeatures() { System.out.print("Features: 1 Single Bed, Wifi, AC"); }
}

class DoubleRoom extends Room {
    public DoubleRoom() { super("Double Room", 180.0); }
    @Override
    public void displayFeatures() { System.out.print("Features: 1 King Bed, Wifi, AC, Mini-Bar"); }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super("Suite Room", 350.0); }
    @Override
    public void displayFeatures() { System.out.print("Features: 2 Rooms, Living Area, Wifi, AC, Ocean View"); }
}

/**
 * Centralized Room Inventory (State Holder).
 */
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public void initializeRoom(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

/**
 * The BookMyStay class implements Use Case 4: Room Search & Availability Check.
 * This class ensures read-only access to inventory and filters unavailable rooms.
 *
 * @version 4.0
 */
public class BookMyStay {

    public static void main(String[] args) {
        System.out.println("Welcome to the Hotel Booking Management System!");
        System.out.println("Application: Book My Stay App");
        System.out.println("Version: 4.0\n");

        // 1. Setup System State (Inventory)
        RoomInventory inventoryManager = new RoomInventory();
        inventoryManager.initializeRoom("Single Room", 5);
        inventoryManager.initializeRoom("Double Room", 0); // Simulated: No availability
        inventoryManager.initializeRoom("Suite Room", 2);

        // 2. Setup Domain Objects
        Room[] rooms = { new SingleRoom(), new DoubleRoom(), new SuiteRoom() };

        // 3. Perform Room Search (Read-Only Operation)
        performSearch(rooms, inventoryManager);
    }

    /**
     * Search Service logic: Filters and displays available rooms.
     * Demonstrates Validation Logic and Defensive Programming.
     */
    public static void performSearch(Room[] rooms, RoomInventory inventory) {
        System.out.println("--- Searching for Available Rooms ---");
        boolean found = false;

        for (Room room : rooms) {
            int availableCount = inventory.getAvailability(room.getType());

            // Validation: Only display rooms with availability > 0
            if (availableCount > 0) {
                System.out.println("Room Type: " + room.getType());
                System.out.println("Price: $" + room.getPrice() + " per night");
                room.displayFeatures();
                System.out.println("\nStatus: " + availableCount + " units left.");
                System.out.println("-------------------------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No rooms are currently available.");
        }

        System.out.println("Search completed. System state remains unchanged.");
    }
}
