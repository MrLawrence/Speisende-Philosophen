package philosophen;

import java.util.ArrayList;
import java.util.logging.Logger;

import philosophen.tisch.Tisch;

public class SpeisendePhilosophen {
	private final static Logger LOG = Logger
			.getLogger(SpeisendePhilosophen.class.getName());

	public static void main(String[] args) {
		Integer anzahlPhilosophen = 5;
		Integer anzahlHungrigePhilosophen = 2;
		Integer anzahlStuehle = 5;
		LOG.info("Zu generierende Philosophen: " + anzahlPhilosophen);
		LOG.info("Zu generierende Stuehle: " + anzahlStuehle);
		Tisch tisch = new Tisch(anzahlStuehle);

		ArrayList<Thread> philosophen = new ArrayList<Thread>();
		for (int i = 0; i < anzahlPhilosophen; i++) {
			philosophen.add(new Thread(new Philosoph(tisch, false)));
		}

		for (int i = 0; i < anzahlHungrigePhilosophen; i++) {
			philosophen.add(new Thread(new Philosoph(tisch, true)));
		}

		
		for (Thread t : philosophen) {
			t.start();
		}
	}
}
