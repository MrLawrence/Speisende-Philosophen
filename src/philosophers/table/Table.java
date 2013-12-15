package philosophers.table;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class Table {
	private final static Logger LOG = Logger.getLogger(Table.class.getName());
	private List<Chair> chairs = new ArrayList<Chair>();
	private List<ReentrantLock> forks = new ArrayList<ReentrantLock>();
	private Semaphore freeChairs;
	private Waitress waitress = new Waitress(chairs);

	public Table(Integer chairAmount) {
		freeChairs = new Semaphore(chairAmount, true);
		addChairsandForks(chairAmount);
	}

	public synchronized Chair getFreeChair() {
		try {
			freeChairs.acquire();
		} catch (InterruptedException e) {
			LOG.info(this.toString() + " was interrupted");
		}
		return waitress.findChair();

	}

	private void addChairsandForks(Integer chairAmount) {
		Integer gabelAmount = chairAmount;

		for (int i = 0; i < gabelAmount; i++) {
			forks.add(new ReentrantLock(true));
		}

		for (int i = 0; i < chairAmount; i++) {
			if (i == chairAmount - 1) {
				chairs.add(new Chair(forks.get(i), forks.get(0)));
			} else {
				chairs.add(new Chair(forks.get(i), forks.get(i + 1)));
			}
		}
	}

	public void notifyFreeChair() {
		freeChairs.release();
	}

	@Override
	public String toString() {
		return "Table with " + chairs.size() + " Chairs";
	}
}
