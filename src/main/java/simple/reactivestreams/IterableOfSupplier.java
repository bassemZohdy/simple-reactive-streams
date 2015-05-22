/*
 * Project : simple-reactive-streams
 * Author : bassem.zohdy
 * Email : bassem.zohdy@gmail.com
 */
package simple.reactivestreams;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;

// TODO: Auto-generated Javadoc
/**
 * The Class IterableOfSupplier.
 *
 * @param <T>
 *            the generic type
 */
public class IterableOfSupplier<T> implements Iterable<T> {
	
	/** The supplier. */
	final private Supplier<T> supplier;

	/**
	 * Instantiates a new iterable of supplier.
	 *
	 * @param supplier
	 *            the supplier
	 */
	private IterableOfSupplier(Supplier<T> supplier) {
		this.supplier = supplier;
	}

	/**
	 * Of.
	 *
	 * @param <T>
	 *            the generic type
	 * @param supplier
	 *            the supplier
	 * @return the iterable of supplier
	 */
	public static <T> IterableOfSupplier<T> of(Supplier<T> supplier) {
		return new IterableOfSupplier<T>(supplier);
	}

	/* (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			Optional<T> next = Optional.empty();

			@Override
			public boolean hasNext() {
				if (next.isPresent()) {
					return true;
				} else {
					next = Optional.ofNullable(supplier.get());
					return next.isPresent();
				}
			}

			@Override
			public T next() {
				if (next.isPresent() || hasNext()) {
					T result = next.get();
					next = Optional.empty();
					return result;
				} else {
					throw new NoSuchElementException();
				}
			}

		};
	}
}
