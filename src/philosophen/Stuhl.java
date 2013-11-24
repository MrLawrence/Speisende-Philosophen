package philosophen;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class Stuhl {
	private final static Logger LOG = Logger.getLogger(Stuhl.class.getName());
	private static AtomicInteger nextId = new AtomicInteger();
	private Integer id;

	private Gabel linkeGabel;
	private Gabel rechteGabel;
	private Philosoph sitzenderPhilosoph;

	public Stuhl(Gabel linkeGabel, Gabel rechteGabel) {
		this.id = nextId.incrementAndGet();
		this.linkeGabel = linkeGabel;
		this.rechteGabel = rechteGabel;
		LOG.info(this.toString() + " erzeugt");
	}

	public Stuhl hinsetzen(Philosoph philosoph) {
		if (!this.istBesetzt()) {
			this.sitzenderPhilosoph = philosoph;
		} else {
			LOG.severe(philosoph.toString() + "versuchte sich auf "
					+ this.toString() + "zu setzen, aber "
					+ sitzenderPhilosoph.toString() + "sitzt dort schon!");
		}
		return this;
	}

	public void aufstehen(Philosoph philosoph) {
		if (sitzenderPhilosoph == philosoph) {
			sitzenderPhilosoph = null;
			LOG.finer(philosoph.toString() + " von " + this.toString()
					+ " aufgestanden");
		} else {
			LOG.severe(philosoph.toString() + " sitzt hier nicht!");
		}

	}

	public Boolean istLinkeGabelFrei() {
		return linkeGabel.istFrei().get();
	}

	public Boolean istRechteGabelFrei() {
		return rechteGabel.istFrei().get();
	}

	public Gabel nimmLinkeGabel() {
		return nimmGabel(linkeGabel);
	}

	public Gabel nimmRechteGabel() {
		return nimmGabel(rechteGabel);
	}

	private Gabel nimmGabel(Gabel gabel) {
		try {
			gabel.nimmInDieHand(sitzenderPhilosoph);
		} catch (GabelNichtVerfügbarException e) {
			LOG.severe("Gabel nicht verfügbar!");
		}
		return gabel;
	}

	public boolean istBesetzt() {
		if (sitzenderPhilosoph != null) {
			return true;
		} else {
			return false;
		}
	}

	public String toString() {
		return "Stuhl #" + this.id;
	}
}
