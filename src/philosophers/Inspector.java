package philosophers;

import java.util.List;
import java.util.logging.Logger;

public class Inspector implements Runnable {
	private final static Logger LOG = Logger.getLogger(Inspector.class
			.getName());
	private List<Philosopher> philosophers;
	private Integer difference = 10;
	private final Integer banTime = 5;

	public Inspector(List<Philosopher> philosophers) {
		this.philosophers = philosophers;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				LOG.info(this.toString() + " was interrupted");
			}

			Integer smallestValue = philosophers.get(0).getMealsAmount();
			for (Philosopher p : philosophers) {
				if(p.getMealsAmount() < smallestValue) {
					smallestValue = p.getMealsAmount();
				}
			}

			for (Philosopher p : philosophers) {
				if (smallestValue + difference < p.getMealsAmount()) {
					p.banFor(banTime * (p.getMealsAmount() - (smallestValue + difference)));
				}
			}
		}
	}

	@Override
	public String toString() {
		return "Inspector";
	}
}
