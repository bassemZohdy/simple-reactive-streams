/*
 * Project : simple-reactive-streams
 * Author : bassem.zohdy
 * Email : bassem.zohdy@gmail.com
 */
package simple.reactivestreams;

import java.util.function.Supplier;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

// TODO: Auto-generated Javadoc
/**
 * The Class SimplePublisher.
 *
 * @param <T>
 *            the generic type
 */
public class SimplePublisher<T> implements Publisher<T> {
	
	/** The supplier. */
	private final Supplier<T> supplier;

	/**
	 * Instantiates a new simple publisher.
	 *
	 * @param supplier
	 *            the supplier
	 */
	private SimplePublisher(Supplier<T> supplier) {
		this.supplier = supplier;
	}

	/**
	 * Of.
	 *
	 * @param <T>
	 *            the generic type
	 * @param supplier
	 *            the supplier
	 * @return the simple publisher
	 */
	public static <T> SimplePublisher<T> of(Supplier<T> supplier) {
		return new SimplePublisher<T>(supplier);
	}

	/* (non-Javadoc)
	 * @see org.reactivestreams.Publisher#subscribe(org.reactivestreams.Subscriber)
	 */
	@Override
	public void subscribe(Subscriber<? super T> subscriber) {
		if (subscriber == null)
			throw null;
		new SimpleSubscription<T>(subscriber, supplier);
	}

}
