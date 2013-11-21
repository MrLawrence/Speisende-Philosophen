package philosophen;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class Gabel {
	private final static Logger LOG = Logger.getLogger(Gabel.class.getName());
	private static AtomicInteger nextId = new AtomicInteger();
	private Integer id;

	public Gabel() {
		this.id = nextId.incrementAndGet();
		LOG.fine(this.toString() + " erzeugt");
	}

	public String toString() {
		return "Gabel #" + id;
	}
}
