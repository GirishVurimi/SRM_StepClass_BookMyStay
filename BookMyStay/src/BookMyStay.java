import java.util.*;

/**
 * Custom Exception for invalid room types.
 */
class InvalidRoomTypeException extends Exception {
    public InvalidRoomTypeException(String message) {
        super(message);
    }
}

/**
 * Custom Exception for inventory-related failures.
 */
class InsufficientInventoryException extends Exception {
    public InsufficientInventoryException(String message) {
        super(message);
    }
}

/**
 * Domain models for Room and Reservation.
 */
abstract class Room {
    private String type;
    private double price;
    public Room(String type, double price) { this.type = type; this.price = price; }
    public String getType() { return type; }
    public abstract void displayFeatures();
}

class SingleRoom extends Room {
    public SingleRoom() { super("Single Room", 100.0); }
    @Override public void displayFeatures() { System.out.print("1 Single Bed, Wifi, AC"); }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super("Suite Room", 350.0); }
    @Override public void displayFeatures() { System.out.print("2 Rooms, Living Area, Ocean View"); }
}

/**
 * Enhanced Inventory with Validation and Exception Handling.
 */
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String type, int count) { inventory.put(type, count); }

    public void validateAndDecrement(String type) throws InvalidRoomTypeException, InsufficientInventoryException {
        // Case-sensitive validation
        if (!inventory.containsKey(type)) {
            throw new InvalidRoomTypeException("Error: The room type '" + type + "' does not exist in our system.");
        }
        if (inventory.get(type) <= 0) {
            throw new InsufficientInventoryException("Error: No available units left for '" + type + "'.");
        }
        inventory.put(type, inventory.get(type) - 1);
    }

    public int getCount(String type) { return inventory.getOrDefault(type, 0); }
}

/**
 * Main Class: BookMyStay (Use Case 9)
 */
public class BookMyStay {

    public static void main(String[] args) {
        System.out.println("Welcome to the Hotel Booking Management System!");
        System.out.println("Application: Book My Stay App | Version: 9.0\n");

        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 1); // Only 1 available for testing
        inventory.addRoomType("Suite Room", 2);

        // Scenario 1: Valid Booking
        processBooking(inventory, "Alice", "Single Room");

        // Scenario 2: Insufficient Inventory (Booking the last Single Room again)
        processBooking(inventory, "Bob", "Single Room");

        // Scenario 3: Invalid Room Type (Typo in "Suite")
        processBooking(inventory, "Charlie", "Suit Room");
    }

    public static void processBooking(RoomInventory inv, String guest, String type) {
        System.out.println("Processing request for " + guest + " (" + type + ")...");
        try {
            inv.validateAndDecrement(type);
            System.out.println("SUCCESS: Reservation confirmed for " + guest + "!");
        } catch (InvalidRoomTypeException | InsufficientInventoryException e) {
            // Graceful failure handling with meaningful messages
            System.err.println("VALIDATION FAILED: " + e.getMessage());
        } finally {
            System.out.println("Current " + type + " inventory: " + inv.getCount(type));
            System.out.println("--------------------------------------------------");
        }
    }
}
