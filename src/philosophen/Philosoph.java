package philosophen;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class Philosoph implements Runnable {
	private final static Logger LOG = Logger.getLogger(Philosoph.class
			.getName());
	private static AtomicInteger nextId = new AtomicInteger();
	private Integer id;

	private ReentrantLock linkeGabel;
	private ReentrantLock rechteGabel;
	private AtomicBoolean isHungry = new AtomicBoolean(false);

	private Random randomGenerator = new Random();

	public Philosoph(ReentrantLock linkeGabel, ReentrantLock rechteGabel) {
		this.id = nextId.incrementAndGet();
		LOG.info(this.toString() + " erzeugt");
		this.linkeGabel = linkeGabel;
		this.rechteGabel = rechteGabel;
	}

	@Override
	public void run() {

	}
	
	private void think() {
		LOG.info(this.toString() + " beginnt denken");
		try {
			Thread.currentThread().sleep(randomGenerator.nextInt(1000));
		} catch (InterruptedException e) {
			LOG.severe(this.toString() + " konnte nicht denken");
		}
	}
	
	
	public String toString() {
		return "Philosoph #" + id;
	}
}
