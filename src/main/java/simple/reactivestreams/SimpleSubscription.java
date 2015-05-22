/*
 * Project : simple-reactive-streams
 * Author : bassem.zohdy
 * Email : bassem.zohdy@gmail.com
 */
package simple.reactivestreams;

import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

// TODO: Auto-generated Javadoc
/**
 * The Class SimpleSubscription.
 *
 * @param <T>
 *            the generic type
 */
public class SimpleSubscription<T> implements Subscription {
	
	/** The iterator. */
	private Iterator<T> iterator;
	
	/** The subscriber. */
	private final Subscriber<? super T> subscriber;
	
	/** The cancelled. */
	private volatile boolean cancelled = false;
	
	/** The demand. */
	private volatile long demand = 0;
	
	/** The executor. */
	private final ExecutorService executor = Executors
			.newSingleThreadExecutor();

	/**
	 * Instantiates a new simple subscription.
	 *
	 * @param subscriber
	 *            the subscriber
	 * @param supplier
	 *            the supplier
	 */
	public SimpleSubscription(Subscriber<? super T> subscriber,
			Supplier<T> supplier) {
		if (subscriber == null)
			throw null;
		this.subscriber = subscriber;

		try {
			iterator = IterableOfSupplier.of(supplier).iterator();
			if (iterator == null)
				iterator = Collections.<T> emptyList().iterator();
		} catch (Throwable t) {

			subscriber.onSubscribe(new Subscription() { // We need to make
														// sure we signal
														// onSubscribe
														// before onError,
														// obeying rule 1.9
						@Override
						public void cancel() {
						}

						@Override
						public void request(long n) {
						}
					});
			cancel();
			subscriber.onError(new IllegalStateException(t));

		}
		if (!cancelled) {
			try {
				subscriber.onSubscribe(this);
			} catch (Throwable t) {
				cancel();
				subscriber.onError(new IllegalStateException(t));

			}
		}
		if (!cancelled) {
			try {
				if (!iterator.hasNext()) {

					cancel();
					subscriber.onComplete();
				}

			} catch (Throwable t) {
				subscriber.onError(new IllegalStateException(t));
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.reactivestreams.Subscription#request(long)
	 */
	@Override
	public void request(long n) {
		if (n < 1) {
			subscriber.onError(new IllegalArgumentException(
					" violated the Reactive Streams rule 3.9 "
							+ "by requesting a non-positive number "
							+ "of elements."));
			return;
		} else if (demand + n < 1) {
			demand = Long.MAX_VALUE;
		} else {
			demand += n;
		}
		send();
	}

	/* (non-Javadoc)
	 * @see org.reactivestreams.Subscription#cancel()
	 */
	@Override
	public void cancel() {
		if (!cancelled) { // 3.7
			cancelled = true;
		}
	}

	/**
	 * Send.
	 */
	public void send() {
		executor.execute(() -> {
			try {
				if (!cancelled && demand > 0) { // check demand > 0 as it could
												// run a thread after --demand >
												// 0
					do {
						T next;
						boolean hasNext;
						try {
							next = iterator.next();
							hasNext = iterator.hasNext();
						} catch (final Throwable t) {
							subscriber.onError(t);
							return;
						}
						subscriber.onNext(next);
						if (!hasNext) {
							cancel();
							subscriber.onComplete();
						}
					} while (--demand > 0 && !cancelled);
					if (!cancelled && demand > 0)
						send();

				}
			} catch (Throwable t) {
				cancel();
				// subscriber.onError(t);
			}
		});
	}
}
