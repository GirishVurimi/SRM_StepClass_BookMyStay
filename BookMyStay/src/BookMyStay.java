import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class representing a generic Room.
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
 * Use Case 3: Centralized Room Inventory Management using HashMap.
 */
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        this.inventory = new HashMap<>();
    }

    public void initializeRoom(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType, int newCount) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, newCount);
        }
    }

    public void displayInventory() {
        System.out.println("--- Current Room Inventory Status ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " units available");
        }
        System.out.println("-------------------------------------");
    }
}

/**
 * The BookMyStay class serves as the entry point for the system.
 * Refactored to Version 3.0.
 */
public class BookMyStay {

    public static void main(String[] args) {
        System.out.println("Welcome to the Hotel Booking Management System!");
        System.out.println("Application: Book My Stay App");
        System.out.println("Version: 3.0\n");

        // Step 1: Initialize Centralized Inventory
        RoomInventory inventoryManager = new RoomInventory();
        inventoryManager.initializeRoom("Single Room", 5);
        inventoryManager.initializeRoom("Double Room", 3);
        inventoryManager.initializeRoom("Suite Room", 2);

        // Step 2: Initialize Room Objects
        Room[] rooms = { new SingleRoom(), new DoubleRoom(), new SuiteRoom() };

        // Step 3: Display room details and availability from HashMap
        for (Room room : rooms) {
            System.out.println("Room Type: " + room.getType());
            System.out.println("Price: $" + room.getPrice());
            room.displayFeatures();

            int availableCount = inventoryManager.getAvailability(room.getType());
            System.out.println("Available Units: " + availableCount);
            System.out.println("---------------------------------");
        }

        // Example update
        System.out.println("\nUpdating Suite Room availability...");
        inventoryManager.updateAvailability("Suite Room", 1);
        inventoryManager.displayInventory();
    }
}
