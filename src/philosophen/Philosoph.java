package philosophen;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class Philosoph implements Runnable {
	private final static Logger LOG = Logger.getLogger(Philosoph.class
			.getName());
	private static AtomicInteger nextId = new AtomicInteger();
	private Integer id;

	private AtomicBoolean isHungry = new AtomicBoolean(false);
	private Tisch tisch;
	private Random randomGenerator = new Random();

	public Philosoph(Tisch tisch) {
		this.id = nextId.incrementAndGet();
		LOG.info(this.toString() + " erzeugt");
		this.tisch = tisch;
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
