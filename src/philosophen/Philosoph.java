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
			essen(stuhl);
			stuhl.aufstehen(this);
			linkeGabel.legAb();
			rechteGabel.legAb();
			linkeGabel = null;
			rechteGabel = null;
		}
	}

	private void essen(Stuhl stuhl) {
		LOG.fine(this.toString() + " versucht zu essen");
		Boolean hatLinkeGabel = false;
		while (!hatLinkeGabel) {
			if (stuhl.istLinkeGabelFrei()) {
				linkeGabel = stuhl.nimmLinkeGabel();
				hatLinkeGabel = true;
				LOG.info(this.toString() + " hat linke "
						+ linkeGabel.toString());
			}
		}

		Boolean hatRechteGabel = false;
		Boolean warteFuerImmer = true;
		Integer tries = 0;
		final Integer MAX_TRIES = 20;
		while (!hatRechteGabel && warteFuerImmer) {
			if (stuhl.istRechteGabelFrei()) {
				rechteGabel = stuhl.nimmRechteGabel();
				hatRechteGabel = true;
				LOG.info(this.toString() + " isst!");
			} else if (tries.equals(MAX_TRIES)) {
				LOG.info(this.toString() + " opfert seine Gabel");
				linkeGabel.legAb();
				linkeGabel = null;
				warteFuerImmer = false;
				essen(stuhl);
			}
			tries += tries;
		}

	}

	private void denken() {
		LOG.info(this.toString() + " beginnt denken");
		try {
			Thread.currentThread().sleep(randomGen.nextInt(1000));
		} catch (InterruptedException e) {
			LOG.severe(this.toString() + " konnte nicht denken");
		}
	}

	public String toString() {
		return "Philosoph #" + id;
	}
}
