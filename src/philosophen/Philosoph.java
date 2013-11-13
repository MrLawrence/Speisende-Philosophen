package philosophen;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Philosoph implements Runnable {
	private ReentrantLock linkeHand;
	private ReentrantLock rechteHand;
	
	private boolean istHungrig = false;

	static AtomicInteger nextId = new AtomicInteger();
	private Integer id;

	public Philosoph() {
		this.id = nextId.incrementAndGet();
	}

	@Override
	public void run() {

	}
}
