package philosophers;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import philosophers.table.Chair;
import philosophers.table.Table;

public class Philosopher implements Runnable {
	private final static Logger LOG = Logger.getLogger(Philosopher.class
			.getName());
	private static AtomicInteger nextId = new AtomicInteger();
	private Integer id;

	private Table table;
	private Chair chair;

	private Integer meals = 0;
	private Integer mealsBeforeSleep = 0;
	private Integer thinkTime = 10;
	private Integer sleepTime = 100;
	private Integer mealTime = 2;
	private Integer penaltyTime = 0;
	private Integer maxMeals = 3;
	private Boolean isHungry;

	public Philosopher(Table table, Boolean isHungry) {
		this.id = nextId.incrementAndGet();
		LOG.info("Philosoph #" + id + " created");
		this.isHungry = isHungry;
		if (isHungry) {
			sleepTime /= 2;
		}
		this.table = table;
	}

	@Override
	public void run() {
		while (true) {
			chair = table.getFreeChair();
			chair.sitDown();
			LOG.fine(this.toString() + " sits on " + chair.toString());
			penalty();
			eat();
			think();
		}
	}

	private void eat() {
		chair.acquireForks();
		try {
			LOG.info(this.toString() + " eats on " + chair.toString());
			Thread.sleep(mealTime);
		} catch (InterruptedException e) {
			LOG.info(this.toString() + " was interrupted");
		}
		chair.leave();
		table.notifyFreeChair();
	}

	private void think() {
		meals += 1;
		mealsBeforeSleep += 1;
		LOG.fine(this.toString() + " starts eating after " + mealsBeforeSleep
				+ " meals");

		try {
			Thread.sleep(thinkTime);
		} catch (InterruptedException e) {
			LOG.info(this.toString() + " was interrupted");
		}

		if (mealsBeforeSleep.equals(maxMeals)) {
			try {
				Thread.sleep(sleepTime);
				LOG.info(this.toString() + " sleeps");
				mealsBeforeSleep = 0;
			} catch (InterruptedException e) {
				LOG.info(this.toString() + " was interrupted");
			}
		}
	}

	public Integer getMealsAmount() {
		return meals;
	}

	public void banFor(Integer penaltyTime) {
		this.penaltyTime = penaltyTime;
	}

	private void penalty() {
		if (penaltyTime != 0) {
			try {
				LOG.info(this.toString() + " banned for " + this.penaltyTime
						+ "ms");
				Thread.sleep(penaltyTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			penaltyTime = 0;
		}
	}

	public void printStats() {
		System.out.println(this.toString() + ": " + meals + " meals");
	}

	@Override
	public String toString() {
		String string = "Philosopher #" + id;
		if (isHungry) {
			string = string + " (hungry)";
		}
		return string;
	}
}
