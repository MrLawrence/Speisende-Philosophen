package philosophen;

import java.util.ArrayList;
import java.util.logging.Logger;
//TODO Klasse teilen
public class Tisch {
	private final static Logger LOG = Logger.getLogger(Tisch.class.getName());
	ArrayList<Stuhl> stuehle = new ArrayList<Stuhl>();
	ArrayList<Gabel> gabeln = new ArrayList<Gabel>();

	public Tisch(Integer stuhlAmount) {
		tischDecken(stuhlAmount);
		LOG.info(this.toString() + " erzeugt");
	}

	public Stuhl sucheStuhl(Philosoph philosoph) {
		Stuhl besterStuhl = null;
		// TODO finde Stuhl mit einem freien Nebenstuhl
		Stuhl einsamerStuhl = findeDreiFreieStuehle();
		if (einsamerStuhl != null) {
			return einsamerStuhl;
		} else {
			Stuhl einzelnerFreierStuhl = findeEinzelnenFreienStuhl();
		}
		besterStuhl.hinsetzen(philosoph);
		return besterStuhl;
	}

	private Stuhl findeEinzelnenFreienStuhl() {
		Stuhl gefundenerStuhl = null;

		for (int i = 0; i < stuehle.size(); i++) {
			Stuhl dieserStuhl = stuehle.get(i);
			if (!dieserStuhl.istBesetzt()) {
				gefundenerStuhl = dieserStuhl;
				break;
			}
		}
		return gefundenerStuhl;
	}

	// finde Stuhl mit zwei leeren Nachbarstühlen
	private Stuhl findeDreiFreieStuehle() {
		Stuhl gefundenerStuhl = null;
		for (int i = 0; i < stuehle.size(); i++) {
			Stuhl dieserStuhl = stuehle.get(i);
			Stuhl linkerStuhl;
			Stuhl rechterStuhl;
			if (i == 0) {
				linkerStuhl = stuehle.get(stuehle.size() - 1);
				rechterStuhl = stuehle.get(1);
			} else if (i == stuehle.size() - 1) {
				linkerStuhl = stuehle.get(i - 1);
				rechterStuhl = stuehle.get(0);
			} else {
				linkerStuhl = stuehle.get(i - 1);
				rechterStuhl = stuehle.get(i + 1);
			}
			if (!dieserStuhl.istBesetzt() && !linkerStuhl.istBesetzt()
					&& !rechterStuhl.istBesetzt()) {
				gefundenerStuhl = dieserStuhl;
				break;
			}
		}
		return gefundenerStuhl;
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
