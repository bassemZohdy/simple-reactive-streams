/*
 * Project : simple-reactive-streams
 * Author : bassem.zohdy
 * Email : bassem.zohdy@gmail.com
 */
package simple.reactivestreams.test;

// TODO: Auto-generated Javadoc
/**
 * The Class Model.
 */
public class Model {
	
	/** The name. */
	private String name;
	
	/** The size. */
	private long size;
	
	/** The value. */
	private double value;

	/**
	 * Instantiates a new model.
	 *
	 * @param name
	 *            the name
	 * @param size
	 *            the size
	 * @param value
	 *            the value
	 */
	public Model(String name, long size, double value) {
		super();
		this.name = name;
		this.size = size;
		this.value = value;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	public long getSize() {
		return size;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public double getValue() {
		return value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Model [name=" + name + ", size=" + size + ", value=" + value
				+ "]";
	}

}
