import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Domain model representing a guest's intent to book a room.
 */
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "Guest: " + guestName + " | Requested: " + roomType;
    }
}

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

class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();
    public void initializeRoom(String roomType, int count) { inventory.put(roomType, count); }
    public int getAvailability(String roomType) { return inventory.getOrDefault(roomType, 0); }
}

/**
 * Main Application Class - Refactored to Version 5.0
 * Implements Use Case 5: Booking Request (FIFO Queue)
 */
public class BookMyStay {

    public static void main(String[] args) {
        System.out.println("Welcome to the Hotel Booking Management System!");
        System.out.println("Application: Book My Stay App");
        System.out.println("Version: 5.0\n");

        // 1. Setup System State
        RoomInventory inventoryManager = new RoomInventory();
        inventoryManager.initializeRoom("Single Room", 5);
        inventoryManager.initializeRoom("Double Room", 3);
        inventoryManager.initializeRoom("Suite Room", 2);

        // 2. Initialize Booking Request Queue (FIFO)
        Queue<Reservation> bookingQueue = new LinkedList<>();

        // 3. Guests submit booking requests (Order of arrival is preserved)
        System.out.println("--- Incoming Booking Requests ---");
        addRequest(bookingQueue, new Reservation("Alice", "Single Room"));
        addRequest(bookingQueue, new Reservation("Bob", "Suite Room"));
        addRequest(bookingQueue, new Reservation("Charlie", "Single Room"));

        // 4. Display Queued Requests
        displayQueue(bookingQueue);

        System.out.println("\nRequests are waiting in order. No inventory has been modified yet.");
    }

    private static void addRequest(Queue<Reservation> queue, Reservation res) {
        queue.add(res);
        System.out.println("Added to Queue: " + res);
    }

    private static void displayQueue(Queue<Reservation> queue) {
        System.out.println("\n--- Current Booking Queue ---");
        if (queue.isEmpty()) {
            System.out.println("Queue is empty.");
        } else {
            for (Reservation res : queue) {
                System.out.println("[WAITING] " + res);
            }
        }
    }
}
