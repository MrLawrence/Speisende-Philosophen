package philosophers.table;

import java.util.List;
import java.util.logging.Logger;

public class Waitress {
	private final static Logger LOG = Logger.getLogger(Waitress.class.getName());
	List<Chair> chairs;

	public Waitress(List<Chair> chairs) {
		this.chairs = chairs;
	}

	public synchronized Chair findChair() {
		Chair bestChair = null;

		bestChair = findFreeChairWithFreeNeighbour();
		if (bestChair == null) {
			bestChair = findOneFreeChair();
		}
		return bestChair;
	}

	private Chair findOneFreeChair() {
		Chair chairFound = null;

		for (int i = 0; i < chairs.size(); i++) {
			Chair testedChair = chairs.get(i);
			if (testedChair.empty()) {
				chairFound = testedChair;
				break;
			}
		}
		return chairFound;
	}

	private Chair findFreeChairWithFreeNeighbour() {
		Chair chairFound = null;

		for (int i = 0; i < chairs.size(); i++) {
			Chair testedChair = chairs.get(i);
			Chair leftChair = findLeftChair(i);
			Chair rightChair = findeRightChair(i);

			if (testedChair.empty()
					&& (leftChair.empty() || rightChair.empty())) {
				chairFound = testedChair;
				break;
			}
		}
		return chairFound;
	}

	private Chair findLeftChair(Integer i) {
		Chair leftChair;

		if (i == 0) {
			leftChair = chairs.get(chairs.size() - 1);
		} else {
			leftChair = chairs.get(i - 1);
		}
		return leftChair;
	}

	private Chair findeRightChair(Integer i) {
		Chair rightChair;

		if (i == chairs.size() - 1) {
			rightChair = chairs.get(0);
		} else {
			rightChair = chairs.get(i + 1);
		}
		return rightChair;
	}
}
