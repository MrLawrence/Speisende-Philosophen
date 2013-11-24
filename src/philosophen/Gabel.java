package philosophen;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class Gabel {
	private final static Logger LOG = Logger.getLogger(Gabel.class.getName());
	private static AtomicInteger nextId = new AtomicInteger();
	private Integer id;

	private Philosoph besitzenderPhilosoph = null;

	public Gabel() {
		this.id = nextId.incrementAndGet();
		LOG.fine(this.toString() + " erzeugt");
	}

	public void nimmInDieHand(Philosoph philosoph)
			throws GabelNichtVerfügbarException {
		if (!istFrei().get()) {
			throw new GabelNichtVerfügbarException("Gabel gehört bereits"
					+ besitzenderPhilosoph);
		} else {
			besitzenderPhilosoph = philosoph;
		}
	}

	public void legAb(Philosoph philosoph) {
		if (besitzenderPhilosoph == philosoph) {
			besitzenderPhilosoph = null;
		} else {
			LOG.severe(this.toString() + " gehört " + philosoph.toString() + " nicht!");
		}
	}

	public AtomicBoolean istBesitzer(Philosoph philosoph) {
		return new AtomicBoolean(besitzenderPhilosoph == philosoph);
	}

	public AtomicBoolean istFrei() {
		return new AtomicBoolean(besitzenderPhilosoph == null);
	}
	
	@Override
	public String toString() {
		return "Gabel #" + id;
	}
}
