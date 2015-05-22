/*
 * Project : simple-reactive-streams
 * Author : bassem.zohdy
 * Email : bassem.zohdy@gmail.com
 */
package simple.reactivestreams.test;

import java.util.function.Supplier;

import org.reactivestreams.Publisher;
import org.reactivestreams.tck.PublisherVerification;
import org.reactivestreams.tck.TestEnvironment;
import org.testng.annotations.Test;

import simple.reactivestreams.SimplePublisher;

// TODO: Auto-generated Javadoc
/**
 * The Class SimplePublisherTest.
 */
@Test
public class SimplePublisherTest extends PublisherVerification<Model> {

	/**
	 * Instantiates a new simple publisher test.
	 */
	public SimplePublisherTest() {
		super(new TestEnvironment());
	}

	/* (non-Javadoc)
	 * @see org.reactivestreams.tck.PublisherVerification#createFailedPublisher()
	 */
	@Override
	public Publisher<Model> createFailedPublisher() {
		return SimplePublisher.of((Supplier<Model>) () -> {
			throw new RuntimeException("Error state signal!");
		});
	}

	/* (non-Javadoc)
	 * @see org.reactivestreams.tck.PublisherVerification#createPublisher(long)
	 */
	@Override
	public Publisher<Model> createPublisher(long requestNumber) {
		return SimplePublisher.of(new ModelSupplier(requestNumber));
	}

}
