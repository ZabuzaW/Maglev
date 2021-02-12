package io.github.zabuzard.maglev.external.algorithms;

import java.util.Collection;

/**
 * Interface for classes that provide landmarks. Landmark are special nodes on a graph that can be used for distance
 * approximation. A good landmark should be a node that is important in the graph, i.e. is used for many shortest paths
 * between nodes.
 *
 * @param <E> The type of nodes and landmarks
 *
 * @author Daniel Tischner {@literal <zabuza.dev@gmail.com>}
 */
@FunctionalInterface
public interface LandmarkProvider<E> {
	/**
	 * Provides the given amount of landmarks. Landmark selection might take a while depending on the size of the graph
	 * and the amount to generate.
	 *
	 * @param amount The amount of landmarks to provide
	 *
	 * @return A collection consisting of the given amount of landmarks. If the given amount is more than the underlying
	 * resource offers, the method should get all the resource offers.
	 */
	Collection<E> getLandmarks(int amount);
}