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
	private enum State { THINKING, HUNGRY, EATING } // Philosopher states
	private final State[] state; // Tracks the state of each philosopher
	private final int numPhilosophers; // Total number of philosophers
	private boolean isSomeoneTalking = false; // Tracks whether a philosopher is talking

	/**
	 * Constructor
	 */
	// TODO: set appropriate number of chopsticks based on the # of philosophers

	public Monitor(int numPhilosophers) {
		this.numPhilosophers = numPhilosophers;
		state = new State[numPhilosophers];

		// Initialize all philosophers as THINKING
		for (int i = 0; i < numPhilosophers; i++) {
			state[i] = State.THINKING;
		}
	}

	/*
	 * -------------------------------
	 * User-defined monitor procedures
	 * -------------------------------
	 */

	/**
	 * A philosopher attempts to pick up their left and right chopsticks.
	 * If both chopsticks are not available, the philosopher waits.
	 * The state is set to hungry, then we check if the neighboors are not eating, this means that this philosopher can eat
	 * if he cant, then he waits
	 * if he can, then the state is changes to eating
	 */
	public synchronized void pickUp(int i) {
		// Philosopher becomes hungry
		state[i] = State.HUNGRY;

		// Check if the philosopher can eat
		while (!canEat(i)) {
			try {
				wait(); // Wait until chopsticks become available
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		// Philosopher starts eating
		state[i] = State.EATING;
	}

	/**
	 * When a given philosopher's done eating, they put the chopstiks/forks down
	 * and let others know they are available.
	 * the state is set to thinkg
	 * and then we notify the waiting philosophers that the can recheck if they can eat
	 */
	public synchronized void putDown(int i) {
		// Philosopher finishes eating and starts thinking
		state[i] = State.THINKING;

		// Notify all waiting philosophers to recheck conditions
		notifyAll();
	}

	/**
	 * Tests if philosopher `i` can eat based on the states of his neighbors.
	 * A philosopher can eat if his state is hungry and both of his neighbors are not eating.
	 */
	private boolean canEat(int i) {
		return state[i] == State.HUNGRY &&
				state[(i + numPhilosophers - 1) % numPhilosophers] != State.EATING &&
				state[(i + 1) % numPhilosophers] != State.EATING;
	}

	/**
	 * Only one philosopher at a time is allowed to philosophy
	 * (while she is not eating).
	 * If another philosopher is already talking the current one waits
	 * when no one is talking the current phys sets isSomoneTalking to true and talks
	 */
	public synchronized void requestTalk() {
		// Wait until no one else is talking
		while (isSomeoneTalking) {
			try {
				wait();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		// Philosopher starts talking
		isSomeoneTalking = true;
	}


	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 * Releases talking privilege
	 * Notifies other waiting philosophers to recheck if they can start talking
	 */

	public synchronized void endTalk() {
		// Philosopher finishes talking
		isSomeoneTalking = false;

		// Notify all waiting philosophers
		notifyAll();
	}

}

// EOF
