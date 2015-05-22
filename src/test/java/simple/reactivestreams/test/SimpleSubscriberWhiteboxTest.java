/*
 * Project : simple-reactive-streams
 * Author : bassem.zohdy
 * Email : bassem.zohdy@gmail.com
 */
package simple.reactivestreams.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.reactivestreams.tck.SubscriberWhiteboxVerification;
import org.reactivestreams.tck.TestEnvironment;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import simple.reactivestreams.SimpleSubscriber;

// TODO: Auto-generated Javadoc
/**
 * The Class SimpleSubscriberWhiteboxTest.
 */

@Test
public class SimpleSubscriberWhiteboxTest extends
		SubscriberWhiteboxVerification<Model> {

	/** The e. */
	private ExecutorService e;

	/**
	 * Before.
	 */
	@BeforeClass
	void before() {
		e = Executors.newFixedThreadPool(4);
	}

	/**
	 * After.
	 */
	@AfterClass
	void after() {
		if (e != null)
			e.shutdown();
	}

	/**
	 * Instantiates a new simple subscriber whitebox test.
	 */
	public SimpleSubscriberWhiteboxTest() {
		super(new TestEnvironment());
	}

	/** The model supplier. */
	ModelSupplier modelSupplier = new ModelSupplier(100);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.reactivestreams.tck.SubscriberWhiteboxVerification#createSubscriber
	 * (org
	 * .reactivestreams.tck.SubscriberWhiteboxVerification.WhiteboxSubscriberProbe
	 * )
	 */
	@Override
	public Subscriber<Model> createSubscriber(
			org.reactivestreams.tck.SubscriberWhiteboxVerification.WhiteboxSubscriberProbe<Model> probe) {
		return new SimpleSubscriber<Model>() {
			@Override
			public void onSubscribe(final Subscription s) {
				super.onSubscribe(s);

				probe.registerOnSubscribe(new SubscriberPuppet() {
					@Override
					public void triggerRequest(long elements) {
						s.request(elements);
					}

					@Override
					public void signalCancel() {
						s.cancel();
					}
				});
			}

			@Override
			public void onNext(Model element) {
				super.onNext(element);
				probe.registerOnNext(element);
			}

			@Override
			public void onError(Throwable cause) {
				super.onError(cause);
				probe.registerOnError(cause);
			}

			@Override
			public void onComplete() {
				super.onComplete();
				probe.registerOnComplete();
			}

		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.reactivestreams.tck.WithHelperPublisher#createElement(int)
	 */
	@Override
	public Model createElement(int element) {
		return modelSupplier.get();
	}

}
