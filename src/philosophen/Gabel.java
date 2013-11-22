package philosophen;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class Gabel {
	private final static Logger LOG = Logger.getLogger(Gabel.class.getName());
	private static AtomicInteger nextId = new AtomicInteger();
	private Integer id;
	
	private Philosoph haltenderPhilosoph = null;

	public Gabel() {
		this.id = nextId.incrementAndGet();
		LOG.fine(this.toString() + " erzeugt");
	}
	
	public void nimmInDieHand(Philosoph philosoph) throws GabelNichtVerfügbarException {
		if(isTaken()) {
			throw new GabelNichtVerfügbarException("Gabel gehört bereits" + haltenderPhilosoph);
		} else {
			haltenderPhilosoph = philosoph;
		}
	}
	
	public void legWiederHin() {
		haltenderPhilosoph = null;
	}
	
	public Boolean isTaken() {
		if (haltenderPhilosoph != null) {
			return true;
		} else {
			return false;
		}
	}

	public String toString() {
		return "Gabel #" + id;
	}
}
