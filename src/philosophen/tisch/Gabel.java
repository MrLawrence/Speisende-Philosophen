package philosophen.tisch;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import philosophen.Philosoph;

public class Gabel {
	private final static Logger LOG = Logger.getLogger(Gabel.class.getName());
	private static AtomicInteger nextId = new AtomicInteger();
	private Semaphore mutex = new Semaphore(1, true);

	private Integer id;

	private Philosoph besitzenderPhilosoph = null;

	public Gabel() {
		this.id = nextId.incrementAndGet();
		LOG.fine("Gabel #" + id + " erzeugt");
	}

	public void nimm(Philosoph philosoph) {
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			LOG.info(this.toString() + " wurde abgebrochen");
		}
		besitzenderPhilosoph = philosoph;
	}

	public void legAb(Philosoph philosoph) {
		if (besitzenderPhilosoph == philosoph) {
			mutex.release();
		} else {
			LOG.severe(this.toString() + " gehört " + philosoph.toString()
					+ " nicht!");
		}
	}

	public AtomicBoolean istBesitzer(Philosoph philosoph) {
		return new AtomicBoolean(besitzenderPhilosoph == philosoph);
	}

	
	public Boolean hasBiggerIdThan(Gabel andereGabel) {
		return andereGabel.isIdBiggerThan(this.id);
	}
	
	public Boolean isIdBiggerThan(Integer otherId) {
		return this.id > otherId;
	}

	@Override
	public String toString() {
		return "Gabel #" + id;
	}
}
