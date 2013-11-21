package philosophen;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class Stuhl {
	private final static Logger LOG = Logger.getLogger(Stuhl.class.getName());
	private static AtomicInteger nextId = new AtomicInteger();
	private Integer id;
	
	private Gabel linkeGabel;
	private Gabel rechteGabel;
	public Stuhl(Gabel linkeGabel, Gabel rechteGabel) {
		this.id = nextId.incrementAndGet();
		this.linkeGabel = linkeGabel;
		this.rechteGabel = rechteGabel;
		LOG.info(this.toString() + " erzeugt");
	}
	
	public String toString() {
		return "Stuhl #" + this.id + " mit " + linkeGabel.toString() + " und " + rechteGabel.toString();
	}
}
