package concurrencyexample;
/**
 * CT8 Concurrency Java Example
 * This program utilizes concurrency by using two threads as counters.
 * One thread counts up to 20, and upon completion, a second thread counts down to 0.
 * This uses synchronization and thread communication
 *
 * @author Brandon Bacher
 */
public class CT8ConcurrencyApp {
	 private static final Object lock = new Object();
	 private static boolean threadOneFinished = false;

	public static void main(String[] args) {
	
		// Thread 1: Counting up to 20
		Thread threadOne = new Thread(() -> {
		    synchronized (lock) {
		        for (int i = 1; i <= 20; i++) {
		            System.out.println("Count up: " + i);
		        }
		        threadOneFinished = true;
		        lock.notify(); // Notify the waiting thread
		    }
		});

		// Thread 2: Counting down from 19 to 0 after Thread 1 completion
		Thread threadTwo = new Thread(() -> {
		    synchronized (lock) {
		        // Wait for notification from Thread 1
		        while (!threadOneFinished) {
		            try {
		                lock.wait();
		            } catch (InterruptedException e) {
		                // Handle interruption exceptions if needed
		                e.printStackTrace();
		            }
		        }

		        // Counting down from 19 to 0
		        for (int i = 20; i >= 0; i--) {
		            System.out.println("Count down: " + i);
		        }
		    }
		});

		// Start both threads
		threadOne.start();
		threadTwo.start();
	}
}


