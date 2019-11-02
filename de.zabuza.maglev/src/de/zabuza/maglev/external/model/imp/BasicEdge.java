package de.zabuza.maglev.external.model.imp;

import de.zabuza.maglev.external.model.Edge;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * Basic implementation of an {@link Edge} that can be reversed implicitly in O(1).
 *
 * @param <N> Type of node
 *
 * @author Daniel Tischner {@literal <zabuza.dev@gmail.com>}
 */
public final class BasicEdge<N> implements Edge<N>, ReversedConsumer {
	/**
	 * The cost of the edge, i.e. its weight.
	 */
	private final double cost;
	/**
	 * The destination node of the edge.
	 */
	private final N destination;
	/**
	 * The source node of the edge.
	 */
	private final N source;
	/**
	 * An object that provides a reversed flag or <tt>null</tt> if not present.
	 * Can be used to determine if the edge should be interpreted as reversed to
	 * implement implicit edge reversal at constant time.
	 */
	private ReversedProvider reversedProvider;

	/**
	 * Creates a new basic edge.
	 *
	 * @param source      The source node of the edge, not null
	 * @param destination The destination node of the edge, not null
	 * @param cost        The cost of the edge, i.e. its weight, not negative
	 */
	public BasicEdge(final N source, final N destination, final double cost) {
		if (cost < 0) {
			throw new IllegalArgumentException("Cost must not be negative");
		}
		this.source = Objects.requireNonNull(source);
		this.destination = Objects.requireNonNull(destination);
		this.cost = cost;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", BasicEdge.class.getSimpleName() + "[", "]").add(
				getSource() + " -(" + cost + ")-> " + getDestination())
				.toString();
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final BasicEdge<?> basicEdge = (BasicEdge<?>) o;
		return Double.compare(basicEdge.cost, cost) == 0 && destination.equals(basicEdge.destination) && source.equals(
				basicEdge.source);
	}

	@Override
	public int hashCode() {
		return Objects.hash(cost, destination, source);
	}

	@Override
	public double getCost() {
		return cost;
	}

	@Override
	public N getDestination() {
		if (reversedProvider != null && reversedProvider.isReversed()) {
			return source;
		}
		return destination;
	}

	@Override
	public N getSource() {
		if (reversedProvider != null && reversedProvider.isReversed()) {
			return destination;
		}
		return source;
	}

	@Override
	public void setReversedProvider(final ReversedProvider provider) {
		reversedProvider = Objects.requireNonNull(provider);
	}
}