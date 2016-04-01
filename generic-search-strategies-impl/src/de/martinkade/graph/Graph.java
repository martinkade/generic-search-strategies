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

import java.util.HashSet;
import java.util.Set;

/**
 * This class reprensents a graph containing a collection of {@link Node}s.
 *
 * @author martinkade
 * @version 2015-10-07
 * <p/>
 * @param <T> Content type of the node
 * @param <C> The {@link Criteria} implementation that enables to compare
 * {@link Node} instances to each other
 */
public class Graph<T, C extends Criteria> {

    /**
     * The nodes of the graph.
     */
    protected Set<Node<T, C>> nodes;

    /**
     * Default constructor.
     */
    public Graph() {
        // empty
    }

    public Set<Node<T, C>> getNodes() {
        return nodes;
    }

    /**
     *
     * @param node
     * @return
     */
    public boolean contains(Node<T, C> node) {
        if (nodes == null || nodes.isEmpty()) {
            return false;
        }
        return nodes.contains(node);
    }

    /**
     * Add a {@link Node} intance to the graph. If the graph already contains
     * this node {@link Node#equals(java.lang.Object)}, it will not be added
     * because of the set characteristic.
     * <p/>
     * If the node has been added successfully with no exception being thrown,
     * this method returns the number of nodes within the graph.
     *
     * @param node The {@link Node} we want to be added
     * @return The number of nodes within the graph (the new node included)
     */
    public int addNode(Node<T, C> node) {
        if (nodes == null) {
            nodes = new HashSet<>();
        }
        nodes.add(node);
        return nodes.size();
    }
}
