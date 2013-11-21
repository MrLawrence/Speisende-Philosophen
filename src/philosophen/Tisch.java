package philosophen;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Tisch {
	private final static Logger LOG = Logger.getLogger(Tisch.class.getName());
	ArrayList<Stuhl> stuehle = new ArrayList<Stuhl>();
	ArrayList<Gabel> gabeln = new ArrayList<Gabel>();

	public Tisch(Integer stuhlAmount) {
		Integer gabelAmount = stuhlAmount;
		
		for (int i = 0; i < stuhlAmount; i++) {
			gabeln.add(new Gabel());
		}
		
		for (int i = 0; i < stuhlAmount; i++) {
			if(i == stuhlAmount - 1){
				stuehle.add(new Stuhl(gabeln.get(i), gabeln.get(0)));
			} else {
			stuehle.add(new Stuhl(gabeln.get(i), gabeln.get(i+1)));
			}
		}
		LOG.info(this.toString() + " erzeugt");
	}

	public String toString() {
		return "Tisch mit " + stuehle.size() + " Stühlen und " + gabeln.size() + " Gabeln";
	}
}
