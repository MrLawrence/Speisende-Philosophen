package philosophen;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import philosophen.tisch.Gabel;
import philosophen.tisch.Stuhl;
import philosophen.tisch.Tisch;

public class Philosoph implements Runnable {
	private final static Logger LOG = Logger.getLogger(Philosoph.class
			.getName());
	private static AtomicInteger nextId = new AtomicInteger();
	private Integer id;

	private Tisch tisch;
	private Gabel linkeGabel;
	private Gabel rechteGabel;
	private Random randomGen = new Random();
	private Integer essvorgaenge = 0;
	private Boolean istHungrig;
	private Integer denkzeit = 100;
	private Integer maxEssvorgaenge = 3;

	public Philosoph(Tisch tisch, Boolean istHungrig) {
		this.id = nextId.incrementAndGet();
		LOG.info(this.toString() + " erzeugt");
		this.istHungrig = istHungrig;
		if (istHungrig) {
			denkzeit /= 2;
		}
		this.tisch = tisch;
	}

	@Override
	public void run() {
		while (true) {

			Stuhl stuhl = tisch.sucheStuhl(this);
			LOG.info(this.toString() + " hat sich auf " + stuhl.toString()
					+ " gesetzt");
			versucheZuEssen(stuhl);

			stuhl.aufstehen(this);
			linkeGabel.legAb(this);
			rechteGabel.legAb(this);
			linkeGabel = null;
			rechteGabel = null;
			denken();
		}
	}

	private void versucheZuEssen(Stuhl stuhl) {
		LOG.fine(this.toString() + " versucht zu essen");
		nimmLinkeGabel(stuhl);
		
		Boolean hatRechteGabel = false;
		Boolean warteFuerImmer = true;
		Integer tries = 0;
		Integer MAX_TRIES = 10;
		while (!hatRechteGabel && warteFuerImmer) {
			if (stuhl.istRechteGabelFrei()) {
				rechteGabel = stuhl.nimmRechteGabel();
				hatRechteGabel = true;
				LOG.info(this.toString() + " isst mit " + linkeGabel.toString()
						+ " und " + rechteGabel.toString());
			} else if (tries.equals(MAX_TRIES)) {
				LOG.info(this.toString() + " opfert seine Gabel");
				linkeGabel.legAb(this);
				linkeGabel = null;
				warteFuerImmer = false;
				try {
					Thread.currentThread().sleep(randomGen.nextInt(10));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				versucheZuEssen(stuhl);
			}
			tries += 1;
		}

	}

	private void nimmLinkeGabel(Stuhl stuhl) {
		Boolean hatLinkeGabel = false;
		while (!hatLinkeGabel) {
			if (stuhl.istLinkeGabelFrei()) {
				linkeGabel = stuhl.nimmLinkeGabel();
				hatLinkeGabel = true;
				LOG.fine(this.toString() + " hat linke "
						+ linkeGabel.toString());
			}
		}
	}

	private void denken() {
		essvorgaenge += 1;
		LOG.fine(this.toString() + " beginnt denken nach " + essvorgaenge
				+ " Essen");
		if (essvorgaenge.equals(maxEssvorgaenge)) {
			try {
				Thread.currentThread().sleep(randomGen.nextInt(denkzeit));
				LOG.info(this.toString() + " schläft");
				essvorgaenge = 0;
			} catch (InterruptedException e) {
				LOG.severe(this.toString() + " konnte nicht schlafen");
			}
		}
	}

	@Override
	public String toString() {
		return "Philosoph #" + id;
	}
}
