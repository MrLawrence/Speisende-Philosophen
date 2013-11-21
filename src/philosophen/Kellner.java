package philosophen;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Kellner {
	private final static Logger LOG = Logger.getLogger(Kellner.class.getName());
	ArrayList<Stuhl> stuehle;

	public Kellner(ArrayList<Stuhl> stuehle) {
		this.stuehle = stuehle;
	}

	public Stuhl findeSitzplatz() {
		Stuhl besterStuhl = null;
		// TODO finde Stuhl mit einem freien Nebenstuhl
		Stuhl einsamerStuhl = findeDreiFreieStuehle();
		if (einsamerStuhl != null) {
			return einsamerStuhl;
		} else {
			besterStuhl = findeEinzelnenFreienStuhl();
		}
		
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
}
