import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * Class DiningPhilosophers
 * The main starter.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class DiningPhilosophers
{
	/*
	 * ------------
	 * Data members
	 * ------------
	 */

	/**
	 * This default may be overridden from the command line
	 */
	public static final int DEFAULT_NUMBER_OF_PHILOSOPHERS = 4;

	/**
	 * Dining "iterations" per philosopher thread
	 * while they are socializing there
	 */
	public static final int DINING_STEPS = 10;

	/**
	 * Our shared monitor for the philosphers to consult
	 */
	public static Monitor soMonitor = null;

	/*
	 * -------
	 * Methods
	 * -------
	 */

	/**
	 * Main system starts up right here
	 */
	public static void main(String[] argv)
	{
		try{
			PrintStream test = new PrintStream("NonIntegerTest.txt");
			System.setOut(test);
		}catch(FileNotFoundException e){
			System.out.println("File not found");
			System.exit(0);

		}
		try
		{
			/*
			 * TODO:
			 * Should be settable from the command line
			 * or the default if no arguments supplied.
			 */
			//Default number of the philosophers
			int iPhilosophers = DEFAULT_NUMBER_OF_PHILOSOPHERS;

			//checking if the command line has any new commands
			if(argv.length > 0) {
				try {

					//converting the command line to an integer
					int nbPhilosophers = Integer.parseInt(argv[0]);


					//checking if the command line had a negative value
					if(nbPhilosophers < 0) {
						System.out.println("%java DiningPhilosophers- " + argv[0]);
						System.out.println("\"" + argv[0] + "\" is not Possitive decimal integer");
						Usage(argv[0]);
						return;
					}
					//checking if the command line had zero value
					if(nbPhilosophers == 0){
						System.out.println("%java DiningPhilosophers- " + argv[0]);
						System.out.println("\"" + argv[0] + "\" is a zero integer");
						Usage(argv[0]);
						return;
					}
					else {
						//assigning the number
						iPhilosophers = nbPhilosophers;
					}



				}catch(NumberFormatException e) {
					System.out.println("%java DiningPhilosophers- " + argv[0]);
					System.out.println("\"" + argv[0] + "\" is not a positive integer!");
					Usage(argv[0]);
					return;
				}

			}

			// Make the monitor aware of how many philosophers there are
			soMonitor = new Monitor(iPhilosophers);

			// Space for all the philosophers
			Philosopher aoPhilosophers[] = new Philosopher[iPhilosophers];

			// Let 'em sit down
			for(int j = 0; j < iPhilosophers; j++)
			{
				aoPhilosophers[j] = new Philosopher();
				aoPhilosophers[j].start();
			}

			System.out.println
			(
				iPhilosophers +
				" philosopher(s) came in for a dinner."
			);

			// Main waits for all its children to die...
			// I mean, philosophers to finish their dinner.
			for(int j = 0; j < iPhilosophers; j++)
				aoPhilosophers[j].join();

			System.out.println("All philosophers have left. System terminates normally.");
		}
		catch(InterruptedException e)
		{
			System.err.println("main():");
			reportException(e);
			System.exit(1);
		}


	} // main()

	/**
	 * Outputs exception information to STDERR
	 * @param poException Exception object to dump to STDERR
	 */
	public static void reportException(Exception poException)
	{
		System.err.println("Caught exception : " + poException.getClass().getName());
		System.err.println("Message          : " + poException.getMessage());
		System.err.println("Stack Trace      : ");
		poException.printStackTrace(System.err);
	}
	public static void Usage(String cmnd){
		System.out.println("Usage: java DiningPhilosophers [" + cmnd + "]");
	}
}

// EOF
