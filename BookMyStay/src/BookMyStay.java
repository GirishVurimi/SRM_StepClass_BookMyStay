import java.util.*;

/**
 * Model class representing a Reservation.
 */
class Reservation {
    private String bookingId;
    private String guestName;
    private String roomType;

    public Reservation(String bookingId, String guestName, String roomType) {
        this.bookingId = bookingId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getBookingId() { return bookingId; }
    public String getRoomType() { return roomType; }

    @Override
    public String toString() {
        return String.format("ID: %-6s | Guest: %-10s | Room: %-10s", bookingId, guestName, roomType);
    }
}

/**
 * Manages inventory and performs rollback operations using a Stack.
 */
class InventoryManager {
    private Map<String, Integer> roomInventory = new HashMap<>();
    private Stack<String> releasedRooms = new Stack<>();

    public InventoryManager() {
        roomInventory.put("Deluxe", 5);
        roomInventory.put("Standard", 10);
    }

    public void rollbackInventory(String roomType, String bookingId) {
        // Increment inventory count
        roomInventory.put(roomType, roomInventory.get(roomType) + 1);
        // Push the booking ID (or room ID) onto the stack for LIFO rollback tracking
        releasedRooms.push(bookingId);
        System.out.println("Inventory restored for: " + roomType + ". Total: " + roomInventory.get(roomType));
        System.out.println("Rollback Stack (LIFO) updated: " + releasedRooms);
    }

    public void displayInventory() {
        System.out.println("Current Inventory: " + roomInventory);
    }
}

/**
 * Handles the cancellation logic and validation.
 */
class CancellationService {
    private Map<String, Reservation> activeBookings;
    private InventoryManager inventoryManager;

    public CancellationService(Map<String, Reservation> activeBookings, InventoryManager inventoryManager) {
        this.activeBookings = activeBookings;
        this.inventoryManager = inventoryManager;
    }

    public void cancelBooking(String bookingId) {
        System.out.println("\nInitiating cancellation for ID: " + bookingId);

        // 1. Validation: Ensure the reservation exists
        if (!activeBookings.containsKey(bookingId)) {
            System.out.println("Error: Booking ID " + bookingId + " not found or already cancelled.");
            return;
        }

        // 2. Retrieve reservation details
        Reservation res = activeBookings.remove(bookingId);

        // 3. Perform Rollback
        inventoryManager.rollbackInventory(res.getRoomType(), res.getBookingId());

        System.out.println("Cancellation Successful for " + bookingId);
    }
}

/**
 * Entry point: BookMyStay
 */
public class BookMyStay {
    public static void main(String[] args) {
        System.out.println("--- Book My Stay: Use Case 10 (Cancellation & Rollback) ---");

        InventoryManager inventory = new InventoryManager();
        Map<String, Reservation> bookings = new HashMap<>();

        // Setup initial state (Pre-existing bookings)
        bookings.put("BK001", new Reservation("BK001", "Alice", "Deluxe"));
        bookings.put("BK002", new Reservation("BK002", "Bob", "Standard"));

        inventory.displayInventory();

        CancellationService cancellationService = new CancellationService(bookings, inventory);

        // Scenario 1: Valid Cancellation
        cancellationService.cancelBooking("BK002");

        // Scenario 2: Valid Cancellation (demonstrating Stack/LIFO)
        cancellationService.cancelBooking("BK001");

        // Scenario 3: Invalid/Duplicate Cancellation
        cancellationService.cancelBooking("BK001");

        System.out.println("\nFinal System State:");
        inventory.displayInventory();
    }
}
