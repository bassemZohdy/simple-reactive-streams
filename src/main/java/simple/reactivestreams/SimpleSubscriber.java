/*
 * Project : simple-reactive-streams
 * Author : bassem.zohdy
 * Email : bassem.zohdy@gmail.com
 */
package simple.reactivestreams;

import java.util.function.Consumer;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

// TODO: Auto-generated Javadoc
/**
 * The Class SimpleSubscriber.
 *
 * @param <T>
 *            the generic type
 */
public class SimpleSubscriber<T> implements Subscriber<T> {
	
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
	 * Instantiates a new simple subscriber.
	 */
	public SimpleSubscriber(){ // for white test
		this.onNext = null;
		this.onComplete = null;
		this.onError = null;
	}

	/**
	 * Instantiates a new simple subscriber.
	 *
	 * @param onNext
	 *            the on next
	 */
	private SimpleSubscriber(Consumer<T> onNext) {
		this.onNext = onNext;
		this.onComplete = null;
		this.onError = null;
	}

	/**
	 * Instantiates a new simple subscriber.
	 *
	 * @param onNext
	 *            the on next
	 * @param onError
	 *            the on error
	 * @param onComplete
	 *            the on complete
	 */
	private SimpleSubscriber(Consumer<T> onNext, Consumer<Throwable> onError,
			Runnable onComplete) {
		this.onNext = onNext;
		this.onComplete = onComplete;
		this.onError = onError;
	}

	/**
	 * Of.
	 *
	 * @param <T>
	 *            the generic type
	 * @param onNext
	 *            the on next
	 * @return the simple subscriber
	 */
	public static <T> SimpleSubscriber<T> of(Consumer<T> onNext) {
		return new SimpleSubscriber<T>(onNext);
	}

	/**
	 * Of.
	 *
	 * @param <T>
	 *            the generic type
	 * @param onNext
	 *            the on next
	 * @param onError
	 *            the on error
	 * @param onComplete
	 *            the on complete
	 * @return the simple subscriber
	 */
	public static <T> SimpleSubscriber<T> of(Consumer<T> onNext,
			Consumer<Throwable> onError, Runnable onComplete) {
		return new SimpleSubscriber<T>(onNext, onError, onComplete);
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
