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
package de.martinkade.graph.search;

import de.martinkade.graph.Criteria;
import de.martinkade.graph.Graph;
import de.martinkade.graph.Heuristic;
import de.martinkade.graph.Node;
import de.martinkade.graph.Path;

import java.util.Iterator;
import java.util.Map.Entry;

/**
 *
 * @author martinkade
 * @version 2015-10-07
 * <p/>
 * @param <T> Content type of the node
 * @param <C> The {@link Criteria} implementation that enables to compare
 * {@link Node} instances to each other
 */
public class Dijkstra<T, C extends Criteria> extends Strategy<T, C> {

    /**
     * The {@link NodePriorityQueue} implemented as binary heap.
     */
    protected NodePriorityQueue<T, C> priorityQueue;

    /**
     * A {@link Heuristic} implementation to be able to order a collection of
     * {@link Node}s.
     */
    protected Heuristic<T, C> heuristic = (Node<T, C> node, Node<T, C> otherNode) -> {
        if (otherNode.getCosts() < node.getCosts()) {
            return 1;
        }
        if (otherNode.getCosts() > node.getCosts()) {
            return -1;
        }
        return 0;
    };

    /**
     * Constructor.
     *
     * @param startNode The node for the algorithm to start at. See
     * {@link #execute(de.martinkade.graph.Graph, de.martinkade.graph.Node)}
     */
    public Dijkstra(Node<T, C> startNode) {
        super(startNode);
    }

    @Override
    protected void setup(Graph<T, C> graph) {
        startNode.setCosts(0.0d);

        priorityQueue = new NodePriorityQueue<>();
        graph.getNodes().stream().forEach((n) -> {
            n.setHeuristic(heuristic);
            priorityQueue.add(n);
        });
    }

    @Override
    public Path<T, C> execute(Graph<T, C> graph, Node<T, C> targetNode) {
        super.execute(graph, targetNode);

        Node<T, C> node;
        while (!priorityQueue.isEmpty()) {
            node = priorityQueue.poll();

            if (node.getSuccessors() != null) {
                final Iterator entries = node.getSuccessors().entrySet().iterator();
                while (entries.hasNext()) {
                    final Entry<Node<T, C>, C> e = (Entry) entries.next();
                    if (priorityQueue.contains(e.getKey())) {
                        final double altCosts = node.getCosts() + e.getValue().numericRepresentation();
                        if (altCosts < e.getKey().getCosts()) {
                            e.getKey().setCosts(altCosts);
                            e.getKey().setPredecessor(node);
                        }
                    }
                }
            }

            if (node.equals(targetNode)) {
                execTime = System.currentTimeMillis() - startMillis;
                return new Path(targetNode);
            }
        }

        execTime = System.currentTimeMillis() - startMillis;
        return new Path(targetNode);
    }

}
