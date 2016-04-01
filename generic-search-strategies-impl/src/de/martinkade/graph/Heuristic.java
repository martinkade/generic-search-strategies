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

/**
 *
 * @author martinkade
 * @version 2015-11-02
 * <p/>
 * @param <T> Content type of the node
 * @param <C> The {@link Criteria} implementation that enables to compare
 * {@link Node} instances to each other
 */
public interface Heuristic<T, C extends Criteria> {

    /**
     * Im most cases this is just a wrapper method containing a specific
     * {@link Node#compareTo(de.martinkade.graph.Node)} implementation.
     *
     * @param node A node
     * @param otherNode The node we want to be compared to the first one
     * @return -1 if the first argument node is placed before the second one, 1
     * if is placed behind or 0 if they are equal according to the implemented
     * comparator
     */
    int apply(Node<T, C> node, Node<T, C> otherNode);

}
