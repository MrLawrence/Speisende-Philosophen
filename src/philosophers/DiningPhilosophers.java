package philosophers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import philosophers.table.Table;

public class DiningPhilosophers {
	private final static Logger LOG = Logger
			.getLogger(DiningPhilosophers.class.getName());

	public static void main(String[] args) {
		Integer philosophersAmount = 5;
		Integer hungryAmount = 2;
		Integer chairAmount = 5;
		Integer runtime = 30; //in s

		Table tisch = new Table(chairAmount);

		List<Philosopher> philosophers = new ArrayList<Philosopher>();
		List<Thread> philosopherThreads = new ArrayList<Thread>();
		
		for (int i = 0; i < philosophersAmount; i++) {
			Philosopher philosopher = new Philosopher(tisch, false);
			philosopherThreads.add(new Thread(philosopher));
			philosophers.add(philosopher);
		}

		for (int i = 0; i < hungryAmount; i++) {
			Philosopher philosoph = new Philosopher(tisch, true);
			philosopherThreads.add(new Thread(philosoph));
			philosophers.add(philosoph);
		}
		Thread inspectorThread = new Thread(new Inspector(philosophers));
		inspectorThread.start();
		for (Thread t : philosopherThreads) {
			t.start();
		}
		
        try {
            Thread.sleep(1000 * runtime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
		for (Philosopher p : philosophers) {
			p.printStats();
		}
		
		for (Thread t : philosopherThreads) {
			t.stop();
		}
		inspectorThread.stop();
	}
}
