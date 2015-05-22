/*
 * Project : simple-reactive-streams
 * Author : bassem.zohdy
 * Email : bassem.zohdy@gmail.com
 */
package simple.reactivestreams.test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

// TODO: Auto-generated Javadoc
/**
 * The Class ModelSupplier.
 */
public class ModelSupplier implements Supplier<Model> {

	/** The counter. */
	private final AtomicInteger counter = new AtomicInteger(0);
	
	/** The limit. */
	private final long limit;

	/**
	 * Instantiates a new model supplier.
	 *
	 * @param limit
	 *            the limit
	 */
	public ModelSupplier(long limit) {
		this.limit = limit;
	}

	/* (non-Javadoc)
	 * @see java.util.function.Supplier#get()
	 */
	@Override
	public Model get() {
		if (counter.get() < limit) {
			int i = counter.incrementAndGet();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return new Model("name" + i, i, i);
		}
		return null;
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		ModelSupplier supplier = new ModelSupplier(3);
		Model m = null;
		while ((m = supplier.get()) != null) {
			System.out.println(m);
		}
	}
}