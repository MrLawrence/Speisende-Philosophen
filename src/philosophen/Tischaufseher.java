package philosophen;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class Tischaufseher implements Runnable {
	private final static Logger LOG = Logger.getLogger(Tischaufseher.class
			.getName());

	ArrayList<Philosoph> philosophen = new ArrayList<Philosoph>();
	private Integer abweichung = 10;
	private AtomicInteger sperrzeit = new AtomicInteger(1000);
	public Tischaufseher(ArrayList<Philosoph> philosophen) {
		this.philosophen = philosophen;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Integer summe = 0;
			for (Philosoph p : philosophen) {
				summe += p.getAlleEssvorgaenge();
			}
			Integer durchschnitt = summe / philosophen.size();

			for (Philosoph p : philosophen) {
				if (durchschnitt + abweichung < p.getAlleEssvorgaenge()) {
					p.sperreFuer(sperrzeit);
					LOG.info("Sperre kurzzeitig " + p.toString());
				}
			}
		}
	}
}
