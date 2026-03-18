import java.util.ArrayList;
import java.util.List;

/**
 * Manages hotel inventory with thread-safe operations.
 */
class HotelInventory {
    private int availableRooms = 2; // Limited rooms to demonstrate thread safety

    /**
     * Synchronized method to ensure only one thread can book a room at a time.
     * This prevents race conditions and double-booking.
     */
    public synchronized boolean bookRoom(String guestName) {
        if (availableRooms > 0) {
            System.out.println(guestName + " is attempting to book... [Rooms available: " + availableRooms + "]");
            // Simulate processing time
            try { Thread.sleep(100); } catch (InterruptedException e) { }

            availableRooms--;
            System.out.println("SUCCESS: Room allocated to " + guestName + ". [Remaining: " + availableRooms + "]");
            return true;
        } else {
            System.out.println("FAILED: No rooms left for " + guestName);
            return false;
        }
    }
}

/**
 * Represents a Guest task that runs in its own thread.
 */
class BookingRequest implements Runnable {
    private HotelInventory inventory;
    private String guestName;

    public BookingRequest(HotelInventory inventory, String guestName) {
        this.inventory = inventory;
        this.guestName = guestName;
    }

    @Override
    public void run() {
        inventory.bookRoom(guestName);
    }
}

/**
 * Entry point: BookMyStay
 * Simulates concurrent booking requests from multiple guests.
 */
public class BookMyStay {
    public static void main(String[] args) {
        System.out.println("--- Book My Stay: Use Case 11 (Concurrent Booking Simulation) ---");
        System.out.println("Starting simulation with 2 rooms and 5 concurrent guests...\n");

        HotelInventory inventory = new HotelInventory();
        List<Thread> guestThreads = new ArrayList<>();

        // Create 5 concurrent booking requests (Threads)
        String[] guests = {"Alice", "Bob", "Charlie", "David", "Eve"};

        for (String name : guests) {
            Thread t = new Thread(new BookingRequest(inventory, name));
            guestThreads.add(t);
            t.start(); // Start the thread (simulates simultaneous request)
        }

        // Wait for all threads to finish
        for (Thread t : guestThreads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\nSimulation Complete. System state is consistent.");
    }
}
