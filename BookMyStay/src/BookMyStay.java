import java.util.*;

/**
 * Domain model representing a guest's intent to book a room.
 */
class Reservation {
    private String guestName;
    private String roomType;
    private String roomId; // Assigned during allocation

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
    public void setRoomId(String roomId) { this.roomId = roomId; }

    @Override
    public String toString() {
        return "Guest: " + guestName + " | Room: " + roomType + (roomId != null ? " | Room ID: " + roomId : "");
    }
}

/**
 * Centralized Room Inventory with update capabilities.
 */
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public void initializeRoom(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrementAvailability(String roomType) {
        int current = getAvailability(roomType);
        if (current > 0) {
            inventory.put(roomType, current - 1);
        }
    }
}

/**
 * Main Application Class - Refactored to Version 6.0
 * Implements Use Case 6: Reservation Confirmation & Room Allocation.
 */
public class BookMyStay {

    // Set to ensure unique Room IDs across the entire system
    private static Set<String> allocatedRoomIds = new HashSet<>();

    public static void main(String[] args) {
        System.out.println("Welcome to the Hotel Booking Management System!");
        System.out.println("Application: Book My Stay App");
        System.out.println("Version: 6.0\n");

        // 1. Setup Inventory
        RoomInventory inventoryManager = new RoomInventory();
        inventoryManager.initializeRoom("Single Room", 2); // Limited for testing
        inventoryManager.initializeRoom("Suite Room", 1);

        // 2. Setup Request Queue (FIFO)
        Queue<Reservation> bookingQueue = new LinkedList<>();
        bookingQueue.add(new Reservation("Alice", "Single Room"));
        bookingQueue.add(new Reservation("Bob", "Suite Room"));
        bookingQueue.add(new Reservation("Charlie", "Single Room"));
        bookingQueue.add(new Reservation("David", "Suite Room")); // Should fail (out of stock)

        // 3. Process Allocation Service
        processBookings(bookingQueue, inventoryManager);
    }

    /**
     * Booking Service: Dequeues requests and allocates rooms safely.
     */
    public static void processBookings(Queue<Reservation> queue, RoomInventory inventory) {
        System.out.println("--- Processing Room Allocations ---");

        while (!queue.isEmpty()) {
            Reservation request = queue.poll(); // Dequeue in FIFO order
            String type = request.getRoomType();

            if (inventory.getAvailability(type) > 0) {
                // Generate a Unique Room ID (e.g., SR-101, SU-201)
                String prefix = type.substring(0, 2).toUpperCase();
                String newId = prefix + "-" + (100 + allocatedRoomIds.size() + 1);

                // Enforce Uniqueness using Set
                if (!allocatedRoomIds.contains(newId)) {
                    request.setRoomId(newId);
                    allocatedRoomIds.add(newId);

                    // Atomic State Update
                    inventory.decrementAvailability(type);

                    System.out.println("[CONFIRMED] " + request);
                }
            } else {
                System.out.println("[FAILED] Guest: " + request.getGuestName() + " | Reason: " + type + " unavailable.");
            }
        }
        System.out.println("-----------------------------------");
    }
}
