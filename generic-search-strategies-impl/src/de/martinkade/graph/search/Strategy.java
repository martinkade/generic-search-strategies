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
import de.martinkade.graph.Node;
import de.martinkade.graph.Path;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author martinkade
 * @version 2015-10-07
 * <p/>
 * @param <T> Content type of the node
 * @param <C> The {@link Criteria} implementation that enables to compare
 * {@link Node} instances to each other
 */
public abstract class Strategy<T, C extends Criteria> {

    /**
     *
     */
    protected final Node<T, C> startNode;

    /**
     * Used for performance monitoring.
     */
    protected long startMillis, execTime;

    /**
     *
     * @param startNode
     */
    public Strategy(Node<T, C> startNode) {
        this.startNode = startNode;
    }

    /**
     *
     * @param graph
     */
    protected abstract void setup(Graph<T, C> graph);

    /**
     *
     * @param graph
     * @param targetNode
     * @return
     */
    public Path<T, C> execute(Graph<T, C> graph, Node<T, C> targetNode) {
        Logger.getLogger(getClass().getName()).log(Level.INFO,
                String.format("calculate path from '%s' to '%s'", startNode.getContent().toString(), targetNode.getContent().toString())
        );
        startMillis = System.currentTimeMillis();
        setup(graph);
        return new Path(targetNode);
    }

    public final float getExecTimeSeconds() {
        return (float) execTime / 1000;
    }
}
