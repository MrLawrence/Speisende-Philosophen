package philosophen;

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

	private Integer essvorgaenge = 0;
	private Integer essvorgaengeVorSchlaf = 0;
	private Integer denkzeit = 10;
	private Integer schlafzeit = 100;
	private Integer esszeit = 2;
	private AtomicInteger sperrzeit = new AtomicInteger(0);
	private Integer maxEssvorgaenge = 3;
	private Boolean istHungrig;

	public Philosoph(Tisch tisch, Boolean istHungrig) {
		this.id = nextId.incrementAndGet();
		LOG.info("Philosoph #" + id + " erzeugt");
		this.istHungrig = istHungrig;
		if (istHungrig) {
			schlafzeit /= 2;
		}
		this.tisch = tisch;
	}

	@Override
	public void run() {
		while (true) {
			Stuhl stuhl = tisch.findeStuhl(this);
			LOG.fine(this.toString() + " hat sich auf " + stuhl.toString()
					+ " gesetzt");
			essen(stuhl);

			denken();
		}
	}

	private void essen(Stuhl stuhl) {
		sperrzeitAbsitzen();
		LOG.fine(this.toString() + " versucht zu essen");
		nimmGabeln(stuhl);
		try {
			LOG.info(this.toString() + " isst auf " + stuhl.toString());
			Thread.sleep(esszeit);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		stuhl.aufstehen(this);
		legGabelnAb();
	}

	private void nimmGabeln(Stuhl stuhl) {
		if (stuhl.istLinkeGabelGroesser()) {
			linkeGabel = stuhl.nimmLinkeGabel();
			rechteGabel = stuhl.nimmRechteGabel();
		} else {
			rechteGabel = stuhl.nimmRechteGabel();
			linkeGabel = stuhl.nimmLinkeGabel();
		}
	}
	
	private void legGabelnAb() {
		linkeGabel.legAb(this);
		rechteGabel.legAb(this);
		linkeGabel = null;
		rechteGabel = null;
	}

	private void denken() {
		essvorgaenge += 1;
		essvorgaengeVorSchlaf += 1;
		LOG.fine(this.toString() + " beginnt denken nach "
				+ essvorgaengeVorSchlaf + " Essen");

		try {
			Thread.sleep(denkzeit);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (essvorgaengeVorSchlaf.equals(maxEssvorgaenge)) {
			try {
				Thread.sleep(schlafzeit);
				LOG.info(this.toString() + " schläft");
				essvorgaengeVorSchlaf = 0;
			} catch (InterruptedException e) {
				LOG.severe(this.toString() + " konnte nicht schlafen");
			}
		}
	}

	public Integer getAlleEssvorgaenge() {
		return essvorgaenge;
	}

	public void sperreFuer(AtomicInteger sperrzeit) {
		this.sperrzeit = sperrzeit;
	}

	private void sperrzeitAbsitzen() {
		try {
			Thread.sleep(sperrzeit.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		sperrzeit.set(0);
	}

	public void printStats() {
		System.out.println(this.toString() + ": " + essvorgaenge
				+ " Essen");
	}

	@Override
	public String toString() {
		String string = "Philosoph #" + id;
		if (istHungrig) {
			string = string + " (hungrig)";
		}
		return string;
	}
}
