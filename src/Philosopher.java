import common.BaseThread;
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//Make sure you put comments for every task that involves coding to the changes
//that you’ve made. This will be considered in the grading process.
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

/**
 * Class Philosopher.
 * Outlines main subrutines of our virtual philosopher.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Philosopher extends BaseThread {
    /**
     * Max time any action can take (in milliseconds) (eating, thinking, or talking).
     * Actions will take a random amount of time up to this value.
     */
    public static final long TIME_TO_WASTE = 1000;

    /**
     * The act of eating.
     * - Print the fact that a given phil (their TID) has started eating.
     * - yield
     * - Then sleep() for a random interval.
     * - yield
     * - Then print that they are done eating.
     */
//K: I don't know what to put in the comment, the code is exactly what the comment above suggests line by line
    public void eat() {
        System.out.println("Philosopher " + getTID() + " starts eating.");
        Thread.yield();
        try {
            sleep((long) (Math.random() * TIME_TO_WASTE)); // Random eating time
        } catch (InterruptedException e) {
            System.err.println("Philosopher.eat():");
            DiningPhilosophers.reportException(e);
            System.exit(1);
        }
        Thread.yield();
        System.out.println("Philosopher " + getTID() + " finishes eating.");
    }


    /**
     * The act of thinking.
     * - Print the fact that a given phil (their TID) has started thinking.
     * - yield
     * - Then sleep() for a random interval.
     * - yield
     * - The print that they are done thinking.
     */
    //K: same thing, just whats in the comment above
    public void think() {
        System.out.println("Philosopher " + getTID() + " starts thinking.");
        Thread.yield();
        try {
            sleep((long) (Math.random() * TIME_TO_WASTE));
                    }
        catch (InterruptedException e) {
            System.err.println("Philosopher.think():");
            DiningPhilosophers.reportException(e);
            System.exit(1);
        }
        Thread.yield();
        System.out.println("Philosopher " + getTID() + " finishes thinking.");

    }

    /**
     * The act of talking.
     * - Print the fact that a given phil (their TID) has started talking.
     * - yield
     * - Say something brilliant at random
     * - yield
     * - The print that they are done talking.
     */
    public void talk() {
        System.out.println("Philosopher " + getTID() + " starts talking.");
        Thread.yield();
        saySomething();
        Thread.yield();
        System.out.println("Philosopher " + getTID() + " finishes talk.");
    }

    /**
     * No, this is not the act of running, just the overridden Thread.run()
     */
    //Comment
    public void run() {
        for (int i = 0; i < DiningPhilosophers.DINING_STEPS; i++) {
            DiningPhilosophers.soMonitor.pickUp(getTID());

            eat();

            DiningPhilosophers.soMonitor.putDown(getTID());

            think();

            /*
             * TODO:
             * A decision is made at random whether this particular
             * philosopher is about to say something terribly useful.
             */
            if (Math.random() < 0.5) { // Em... what's with "true == false"?
                DiningPhilosophers.soMonitor.requestTalk();
                talk();
                DiningPhilosophers.soMonitor.endTalk();
            }

            Thread.yield();
        }
    } // run()

    /**
     * Prints out a phrase from the array of phrases at random.
     * Feel free to add your own phrases.
     */
    //K: I added more funny phrases
    public void saySomething() {
        String[] astrPhrases =
                {
                        "Eh, it's not easy to be a philosopher: eat, think, talk, eat...",
                        "You know, true is false and false is true if you think of it",
                        "2 + 2 = 5 for extremely large values of 2...",
                        "If thee cannot speak, thee must be silent",
                        "Why do we even eat? To distract ourselves from the meaning of existence.",
                        "I think, therefore I am... hungry.",
                        "One chopstick is lonely, two chopsticks make art.",
                        "If a tree falls in the forest, can I eat it?",
                        "Infinity is a long time, especially when you're waiting to eat.",
                        "My number is " + getTID() + "",
                        "Life is just a recursive loop: think, eat, repeat.",
                        "Parallelism is great, except when someone else gets the chopsticks.",
                        "They say sharing is caring, but not in this particular situation."
                };

        System.out.println
                (
                        "Philosopher " + getTID() + " says: " +
                                astrPhrases[(int) (Math.random() * astrPhrases.length)]
                );
    }
}

// EOF
