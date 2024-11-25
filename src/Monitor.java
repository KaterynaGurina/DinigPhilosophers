/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Monitor
{
	/*
	 * ------------
	 * Data members
	 * ------------
	 */
	private boolean[] chopsticks; // Tracks availability of chopsticks
	private final int numPhilosophers;
	private boolean isSomeoneTalking = false; // Tracks talking state

	/**
	 * Constructor
	 */
	// TODO: set appropriate number of chopsticks based on the # of philosophers

	public Monitor(int numPhilosophers) {
		this.numPhilosophers = numPhilosophers;
		chopsticks = new boolean[numPhilosophers];

		// Initialize all chopsticks to available
		for (int i = 0; i < numPhilosophers; i++) {
			chopsticks[i] = true; // true = available
		}
	}

	/*
	 * -------------------------------
	 * User-defined monitor procedures
	 * -------------------------------
	 */

	/**
	 * Grants request (returns) to eat when both chopsticks/forks are available.
	 * Else forces the philosopher to wait()
	 */
	//K: Allows a philosopher to pick up their left and right chopsticks to eat.
	//If either chopstick is unavailable, the philosopher waits.
	//Convert the philosopher's ID (piTID) into zero-based indices:
	//Left chopstick: philosopherIndex (same as the philosopher’s index).
	//Right chopstick: (philosopherIndex + 1) % numPhilosophers (circular wrap-around).
	//Check if both chopsticks are available:
	//If they are not, the philosopher calls wait() to release the lock and wait for a notification.
	//When both chopsticks are free: Set their states to false (in use).
	//The philosopher can proceed to eat.
	public synchronized void pickUp(final int piTID) {
		int philosopherIndex = piTID - 1; // Convert to zero-based index
		int leftChopstick = philosopherIndex;
		int rightChopstick = (philosopherIndex + 1) % numPhilosophers;

		// Wait until both chopsticks are available
		while (!chopsticks[leftChopstick] || !chopsticks[rightChopstick]) {
			try {
				wait(); // Wait for chopsticks to become available
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt(); // Restore interrupt status
			}
		}

		// Pick up both chopsticks
		chopsticks[leftChopstick] = false;
		chopsticks[rightChopstick] = false;
	}

	/**
	 * When a given philosopher's done eating, they put the chopstiks/forks down
	 * and let others know they are available.
	 */
	//K: Releases the left and right chopsticks after a philosopher finishes eating.
	//Notifies all waiting philosophers that resources may now be available.
	//Convert the philosopher's ID (piTID) into zero-based indices for their chopsticks.
	//Set both chopsticks to true (available).
	//Call notifyAll() to wake up all waiting philosophers so they can recheck their conditions.
	public synchronized void putDown(final int piTID) {
		int philosopherIndex = piTID - 1; // Convert to zero-based index
		int leftChopstick = philosopherIndex;
		int rightChopstick = (philosopherIndex + 1) % numPhilosophers;

		// Put down both chopsticks
		chopsticks[leftChopstick] = true;
		chopsticks[rightChopstick] = true;

		// Notify all waiting philosophers
		notifyAll();
	}


	/**
	 * Only one philopher at a time is allowed to philosophy
	 * (while she is not eating).
	 */
	//K: Ensures that only one philosopher can talk at a time.
	//If another philosopher is talking, the current philosopher must wait.
	//Use the isSomeoneTalking boolean to track if a philosopher is currently talking.
	//If isSomeoneTalking is true, the philosopher calls wait() to release the lock and wait for a notification.
	//Once the philosopher is allowed to talk, set isSomeoneTalking to true.
	public synchronized void requestTalk() {
		// Wait until no one is talking
		while (isSomeoneTalking) {
			try {
				wait(); // Wait for a notification that talking is free
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt(); // Restore interrupt status
			}
		}

		// Philosopher starts talking
		isSomeoneTalking = true;
	}


	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	//K: Releases the talking privilege after a philosopher finishes talking.
	//Notifies waiting philosophers that they can now talk.
	//Set isSomeoneTalking to false (no one is talking).
	//Call notifyAll() to wake up all waiting philosophers.
	public synchronized void endTalk() {
		// Philosopher finishes talking
		isSomeoneTalking = false;

		// Notify waiting philosophers
		notifyAll();
	}

}

// EOF
