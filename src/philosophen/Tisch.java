package philosophen;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Tisch {
	private final static Logger LOG = Logger.getLogger(Tisch.class.getName());
	ArrayList<Stuhl> stuehle = new ArrayList<Stuhl>();
	ArrayList<Gabel> gabeln = new ArrayList<Gabel>();

	Kellner kellner = new Kellner(stuehle);

	public Tisch(Integer stuhlAmount) {
		tischDecken(stuhlAmount);
		LOG.info(this.toString() + " erzeugt");
	}

	public synchronized Stuhl sucheStuhl(Philosoph philosoph) {
		Stuhl besterStuhl = kellner.findeSitzplatz();
		besterStuhl.hinsetzen(philosoph);
		return besterStuhl;
	}

	private void tischDecken(Integer stuhlAmount) {
		Integer gabelAmount = stuhlAmount;

		// Hol die Gabeln
		for (int i = 0; i < gabelAmount; i++) {
			gabeln.add(new Gabel());
		}
		// Stell die Stühle an den Tisch und leg die Gabeln dazu
		for (int i = 0; i < stuhlAmount; i++) {
			if (i == stuhlAmount - 1) {
				stuehle.add(new Stuhl(gabeln.get(i), gabeln.get(0)));
			} else {
				stuehle.add(new Stuhl(gabeln.get(i), gabeln.get(i + 1)));
			}
		}
	}

	public String toString() {
		return "Tisch mit " + stuehle.size() + " Stühlen und " + gabeln.size()
				+ " Gabeln";
	}
}
