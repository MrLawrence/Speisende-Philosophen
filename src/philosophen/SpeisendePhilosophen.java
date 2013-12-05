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
		Integer gesamtlaufzeit = 60; //in s
		LOG.info("Zu generierende Philosophen: " + anzahlPhilosophen);
		LOG.info("Zu generierende Stuehle: " + anzahlStuehle);
		Tisch tisch = new Tisch(anzahlStuehle);

		ArrayList<Philosoph> philosophen = new ArrayList<Philosoph>();

		ArrayList<Thread> philosophenThreads = new ArrayList<Thread>();
		for (int i = 0; i < anzahlPhilosophen; i++) {
			Philosoph philosoph = new Philosoph(tisch, false);
			philosophenThreads.add(new Thread(philosoph));
			philosophen.add(philosoph);
		}

		for (int i = 0; i < anzahlHungrigePhilosophen; i++) {
			Philosoph philosoph = new Philosoph(tisch, true);
			philosophenThreads.add(new Thread(philosoph));
			philosophen.add(philosoph);
		}
		Thread aufseherThread = new Thread(new Tischaufseher(philosophen));
		aufseherThread.start();
		for (Thread t : philosophenThreads) {
			t.start();
		}
		
        try {
            Thread.sleep(1000 * gesamtlaufzeit);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
		for (Philosoph p : philosophen) {
			p.printStats();
		}
		
		for (Thread t : philosophenThreads) {
			t.stop();
		}
		aufseherThread.stop();
	}
}
