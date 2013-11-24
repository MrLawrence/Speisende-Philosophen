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

	private Tisch tisch;
	private Gabel linkeGabel;
	private Gabel rechteGabel;
	private Random randomGen = new Random();

	public Philosoph(Tisch tisch) {
		this.id = nextId.incrementAndGet();
		LOG.info(this.toString() + " erzeugt");
		this.tisch = tisch;
	}

	@Override
	public void run() {
		while (true) {
			denken();
			Stuhl stuhl = tisch.sucheStuhl(this);
			LOG.info(this.toString() + " hat sich auf " + stuhl.toString()
					+ " gesetzt");
			essen(stuhl);
			try {
				Thread.currentThread().sleep(randomGen.nextInt(10));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			stuhl.aufstehen(this);
			linkeGabel.legAb(this);
			rechteGabel.legAb(this);
			linkeGabel = null;
			rechteGabel = null;
		}
	}

	private void essen(Stuhl stuhl) {
		LOG.fine(this.toString() + " versucht zu essen");
		AtomicBoolean hatLinkeGabel = new AtomicBoolean(false);
		while (!hatLinkeGabel.get()) {
			if (stuhl.istLinkeGabelFrei()) {
				linkeGabel = stuhl.nimmLinkeGabel();
				hatLinkeGabel.set(true);
				LOG.info(this.toString() + " hat linke "
						+ linkeGabel.toString());
			}
		}

		AtomicBoolean hatRechteGabel = new AtomicBoolean(false);
		AtomicBoolean warteFuerImmer = new AtomicBoolean(true);
		Integer tries = 0;
		final Integer MAX_TRIES = 6;
		while (!hatRechteGabel.get() && warteFuerImmer.get()) {
			if (stuhl.istRechteGabelFrei()) {
				rechteGabel = stuhl.nimmRechteGabel();
				hatRechteGabel.set(true);
				LOG.info(this.toString() + " isst mit " + linkeGabel.toString()
						+ " und " + rechteGabel.toString());
			} else if (tries.equals(MAX_TRIES)) {
				LOG.info(this.toString() + " opfert seine Gabel");
				linkeGabel.legAb(this);
				linkeGabel = null;
				warteFuerImmer.set(false);
				essen(stuhl);		
			}
			tries += tries;
		}

	}

	private void denken() {
		LOG.fine(this.toString() + " beginnt denken");
		try {
			Thread.currentThread().sleep(randomGen.nextInt(100));
		} catch (InterruptedException e) {
			LOG.severe(this.toString() + " konnte nicht denken");
		}
	}

	public String toString() {
		return "Philosoph #" + id;
	}
}
