/*
 * Project : simple-reactive-streams
 * Author : bassem.zohdy
 * Email : bassem.zohdy@gmail.com
 */
package simple.reactivestreams.test;

import java.util.function.Consumer;

import org.reactivestreams.Subscriber;
import org.reactivestreams.tck.SubscriberBlackboxVerification;
import org.reactivestreams.tck.TestEnvironment;
import org.testng.annotations.Test;

import simple.reactivestreams.SimpleSubscriber;

// TODO: Auto-generated Javadoc
/**
 * The Class SimpleSubscriberBlackboxTest.
 */
@Test
public class SimpleSubscriberBlackboxTest extends
		SubscriberBlackboxVerification<Model> {

	/**
	 * Instantiates a new simple subscriber blackbox test.
	 */
	public SimpleSubscriberBlackboxTest() {
		super(new TestEnvironment());
	}

	/** The model supplier. */
	ModelSupplier modelSupplier = new ModelSupplier(100);

	/* (non-Javadoc)
	 * @see org.reactivestreams.tck.SubscriberBlackboxVerification#createSubscriber()
	 */
	@Override
	public Subscriber<Model> createSubscriber() {
		return SimpleSubscriber.of((Consumer<Model>) System.out::println);
	}

	/* (non-Javadoc)
	 * @see org.reactivestreams.tck.WithHelperPublisher#createElement(int)
	 */
	@Override
	public Model createElement(int arg0) {
		return modelSupplier.get();
	}

}
