/*
 * Project : simple-reactive-streams
 * Author : bassem.zohdy
 * Email : bassem.zohdy@gmail.com
 */
package simple.reactivestreams;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.reactivestreams.Processor;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

// TODO: Auto-generated Javadoc
/**
 * The Class SimpleProcessor.
 *
 * @param <T>
 *            the generic type
 */
public class SimpleProcessor<T> implements Processor<T, T> {

	/** The supplier. */
	private final Supplier<T> supplier;
	
	/** The on next. */
	private final Consumer<T> onNext;
	
	/** The on error. */
	private final Consumer<Throwable> onError;
	
	/** The on complete. */
	private final Runnable onComplete;
	
	/** The subscription. */
	private Subscription subscription;
	
	/** The completed. */
	private volatile boolean completed = false;

	/**
	 * Instantiates a new simple processor.
	 *
	 * @param supplier
	 *            the supplier
	 * @param onNext
	 *            the on next
	 */
	private SimpleProcessor(Supplier<T> supplier, Consumer<T> onNext) {
		this.supplier = supplier;
		this.onNext = onNext;
		this.onComplete = null;
		this.onError = null;
		this.subscribe(this);
	}

	/**
	 * Instantiates a new simple processor.
	 *
	 * @param supplier
	 *            the supplier
	 * @param onNext
	 *            the on next
	 * @param onError
	 *            the on error
	 * @param onComplete
	 *            the on complete
	 */
	private SimpleProcessor(Supplier<T> supplier, Consumer<T> onNext,
			Consumer<Throwable> onError, Runnable onComplete) {
		this.supplier = supplier;
		this.onNext = onNext;
		this.onComplete = onComplete;
		this.onError = onError;
		this.subscribe(this);
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

	/**
	 * Of.
	 *
	 * @param <T>
	 *            the generic type
	 * @param supplier
	 *            the supplier
	 * @param onNext
	 *            the on next
	 * @return the simple processor
	 */
	public static <T> SimpleProcessor<T> of(Supplier<T> supplier,
			Consumer<T> onNext) {
		return new SimpleProcessor<T>(supplier, onNext);
	}

	/**
	 * Of.
	 *
	 * @param <T>
	 *            the generic type
	 * @param supplier
	 *            the supplier
	 * @param onNext
	 *            the on next
	 * @param onError
	 *            the on error
	 * @param onComplete
	 *            the on complete
	 * @return the simple processor
	 */
	public static <T> SimpleProcessor<T> of(Supplier<T> supplier,
			Consumer<T> onNext, Consumer<Throwable> onError, Runnable onComplete) {
		return new SimpleProcessor<T>(supplier, onNext, onError, onComplete);
	}

	/* (non-Javadoc)
	 * @see org.reactivestreams.Subscriber#onSubscribe(org.reactivestreams.Subscription)
	 */
	@Override
	public void onSubscribe(Subscription subscription) {
		if (this.subscription != null) {
			subscription.cancel();
			return;
		}
		this.subscription = subscription;
		subscription.request(1);
	}

	/* (non-Javadoc)
	 * @see org.reactivestreams.Subscriber#onNext(java.lang.Object)
	 */
	@Override
	public void onNext(T t) {
		if (t == null)
			throw null;
		if (onNext != null)
			onNext.accept(t);
		if (!completed)
			subscription.request(1);
	}

	/* (non-Javadoc)
	 * @see org.reactivestreams.Subscriber#onError(java.lang.Throwable)
	 */
	@Override
	public void onError(Throwable t) {
		if (t == null)
			throw null;
		if (onError != null)
			onError.accept(t);
	}

	/* (non-Javadoc)
	 * @see org.reactivestreams.Subscriber#onComplete()
	 */
	@Override
	public void onComplete() {
		completed = true;
		if (onComplete != null)
			onComplete.run();
	}
}
