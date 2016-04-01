/*
 * The MIT License
 *
 * Copyright 2015 martinkade.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package de.martinkade.graph;

import de.martinkade.graph.search.navigation.City;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This class reprensents a node of a {@link Graph} with generic content type
 * and evaluation criteria.
 *
 * @author martinkade
 * @version 2015-10-07
 * <p/>
 * @param <T> Content type of the node
 * @param <C> The {@link Criteria} implementation that enables to compare
 * {@link Node} instances to each other
 */
public class Node<T, C extends Criteria> implements Comparable<Node<T, C>> {

    /**
     * The content of the node, for example a {@link City}.
     */
    protected T content;

    /**
     * The costs of the node, {@link Double#POSITIVE_INFINITY} by default.
     */
    protected double costs;

    /**
     * A {@link Heuristic} implementation to be able to order a collection of
     * nodes.
     */
    protected Heuristic<T, C> heuristic;

    /**
     * Reference to the node being visited previously. We need this reference
     * later on to be able to remember the best path.
     */
    protected Node<T, C> predecessor;

    /**
     * The referenced neighbors of this node as a collection of possible
     * successors.
     */
    protected Map<Node<T, C>, C> successors;

    /**
     * Constructor. Contains default initialisations like the costs being set to
     * positive infinity.
     *
     * @param content The content this node represents
     */
    public Node(T content) {
        costs = Double.POSITIVE_INFINITY;
        this.content = content;
    }

    /**
     * 
     * @param successor
     * @return 
     */
    public boolean hasSuccessor(Node<T, C> successor) {
        if (successors == null || successors.isEmpty()) {
            return false;
        }
        return successors.containsKey(successor);
    }

    /**
     * Add a {@link Node} as neighbor to the map of possible successors of the
     * node.
     * <p/>
     * An exception gets thrown if there already is a reference to the node
     * {@link #equals(java.lang.Object)} we want to add.
     *
     * @param successor Another neighbor of the node
     * @param costs The costs of the successor to get there
     * @throws DuplicateNodeException If there already is a reference to the
     * node we want to add
     */
    public void addSuccessor(Node<T, C> successor, C costs) throws DuplicateNodeException {
        if (successors == null) {
            successors = new HashMap<>();
        }
        if (successors.containsKey(successor)) {
            throw new DuplicateNodeException(
                    String.format("this successor was already present: '%s'",
                            successor.content.toString()
                    )
            );
        }
        successors.put(successor, costs);
    }

    public void setCosts(double costs) {
        this.costs = costs;
    }

    public void setHeuristic(Heuristic<T, C> heuristic) {
        this.heuristic = heuristic;
    }

    public double getCosts() {
        return costs;
    }

    public T getContent() {
        return content;
    }

    public Map<Node<T, C>, C> getSuccessors() {
        return successors;
    }

    public void setPredecessor(Node<T, C> predecessor) {
        this.predecessor = predecessor;
    }

    public Node<T, C> getPredecessor() {
        return predecessor;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Node)) {
            return false;
        }
        return content.equals(((Node) obj).content);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(content);
        return hash;
    }

    @Override
    public final int compareTo(Node<T, C> o) {
        return heuristic.apply(this, o);
    }
}
