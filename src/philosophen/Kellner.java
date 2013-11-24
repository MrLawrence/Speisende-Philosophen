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

		Stuhl dreifacherStuhl = findeDreiFreieStuehle();
		if (dreifacherStuhl != null) {
			besterStuhl =  dreifacherStuhl;
		} else {
			Stuhl zweifacherStuhl = findeStuhlMitEinemFreienNachbarn();
			if(zweifacherStuhl != null) {
				besterStuhl = zweifacherStuhl;
			} else {
				besterStuhl = findeEinzelnenFreienStuhl();

			}
		}		
		
		return besterStuhl;
	}

	private Stuhl findeEinzelnenFreienStuhl() {
		Stuhl gefundenerStuhl = null;

		for (int i = 0; i < stuehle.size(); i++) {
			Stuhl dieserStuhl = stuehle.get(i);
			if (dieserStuhl.istFrei()) {
				gefundenerStuhl = dieserStuhl;
				break;
			}
		}
		return gefundenerStuhl;
	}

	private Stuhl findeStuhlMitEinemFreienNachbarn() {
		Stuhl gefundenerStuhl = null;

		for (int i = 0; i < stuehle.size(); i++) {
			Stuhl dieserStuhl = stuehle.get(i);
			Stuhl linkerStuhl = findeLinkenStuhl(i);
			Stuhl rechterStuhl = findeRechtenStuhl(i);

			if (dieserStuhl.istFrei()
					&& (linkerStuhl.istFrei() || rechterStuhl.istFrei())) {
				gefundenerStuhl = dieserStuhl;
				break;
			}
		}
		return gefundenerStuhl;
	}

	private Stuhl findeDreiFreieStuehle() {
		Stuhl gefundenerStuhl = null;
		for (int i = 0; i < stuehle.size(); i++) {
			Stuhl dieserStuhl = stuehle.get(i);
			Stuhl linkerStuhl = findeLinkenStuhl(i);
			Stuhl rechterStuhl = findeRechtenStuhl(i);

			if (dieserStuhl.istFrei() && linkerStuhl.istFrei()
					&& rechterStuhl.istFrei()) {
				gefundenerStuhl = dieserStuhl;
				break;
			}
		}
		return gefundenerStuhl;
	}

	private Stuhl findeLinkenStuhl(Integer i) {
		Stuhl linkerStuhl;

		if (i == 0) {
			linkerStuhl = stuehle.get(stuehle.size() - 1);
		} else {
			linkerStuhl = stuehle.get(i - 1);
		}
		return linkerStuhl;
	}

	private Stuhl findeRechtenStuhl(Integer i) {
		Stuhl rechterStuhl;

		if (i == stuehle.size() - 1) {
			rechterStuhl = stuehle.get(0);
		} else {
			rechterStuhl = stuehle.get(i + 1);
		}
		return rechterStuhl;
	}
}
