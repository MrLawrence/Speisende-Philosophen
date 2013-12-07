package philosophen.tisch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import philosophen.Philosoph;

public class Tisch {
	private List<Stuhl> stuehle = new ArrayList<Stuhl>();
	private List<Gabel> gabeln = new ArrayList<Gabel>();
	private Semaphore semaphore;
	private Kellner kellner = new Kellner(stuehle);

	public Tisch(Integer stuhlAmount) {
		semaphore = new Semaphore(stuhlAmount, true);
		tischDecken(stuhlAmount);
	}

	public synchronized Stuhl findeStuhl(Philosoph philosoph) {
		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Stuhl besterStuhl = kellner.findeSitzplatz();
		besterStuhl.hinsetzen(philosoph);
		return besterStuhl;
	}

	private void tischDecken(Integer stuhlAmount) {
		Integer gabelAmount = stuhlAmount;

		for (int i = 0; i < gabelAmount; i++) {
			gabeln.add(new Gabel());
		}

		for (int i = 0; i < stuhlAmount; i++) {
			if (i == stuhlAmount - 1) {
				stuehle.add(new Stuhl(this, gabeln.get(i), gabeln.get(0)));
			} else {
				stuehle.add(new Stuhl(this, gabeln.get(i), gabeln.get(i + 1)));
			}
		}
	}
	
	public void aufstehen(Stuhl stuhl) {
		semaphore.release();
		stuhl.aufstehen();
	}

	@Override
	public String toString() {
		return "Tisch mit " + stuehle.size() + " Stühlen und " + gabeln.size()
				+ " Gabeln";
	}
}
