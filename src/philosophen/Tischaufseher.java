package philosophen;

import java.util.List;
import java.util.logging.Logger;

public class Tischaufseher implements Runnable {
	private final static Logger LOG = Logger.getLogger(Tischaufseher.class
			.getName());
	private List<Philosoph> philosophen;
	private Integer abweichung = 10;
	private final Integer sperrzeit = 5;

	public Tischaufseher(List<Philosoph> philosophen) {
		this.philosophen = philosophen;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				LOG.info(this.toString() + " wurde beendet");
			}

			Integer kleinsterWert = philosophen.get(0).getAlleEssen();
			for (Philosoph p : philosophen) {
				if(p.getAlleEssen() < kleinsterWert) {
					kleinsterWert = p.getAlleEssen();
				}
			}

			for (Philosoph p : philosophen) {
				if (kleinsterWert + abweichung < p.getAlleEssen()) {
					p.sperreFuer(sperrzeit * (p.getAlleEssen() - (kleinsterWert + abweichung)));
				}
			}
		}
	}

	@Override
	public String toString() {
		return "Tischaufseher";
	}
}
