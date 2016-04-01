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

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represents a path as a possible result of a {@link Strategy}
 * implementations.
 *
 * @author martinkade
 * @version 2015-10-07
 * <p/>
 * @param <T> Content type of the node
 * @param <C> The {@link Criteria} implementation that enables to compare
 * {@link Node} instances to each other
 */
public class Path<T, C extends Criteria> {

    /**
     * The end or target point of the path, respectively.
     */
    private final Node<T, C> targetNode;

    /**
     * Constructor.
     *
     * @param targetNode The last node of the path
     */
    public Path(Node<T, C> targetNode) {
        this.targetNode = targetNode;
    }

    @Override
    public String toString() {
        Logger.getLogger(getClass().getName()).log(Level.INFO,
                String.format("print path to target node '%s'", targetNode.getContent().toString())
        );

        Node<T, C> node = targetNode.getPredecessor();
        final List<T> contents = new ArrayList<>();

        while (node != null) {
            contents.add(0, node.getContent());
            node = node.getPredecessor();
        }

        final StringBuilder sb = new StringBuilder();
        contents.stream().forEach((e) -> {
            sb.append(e.toString()).append(" -> ");
        });
        sb.append(targetNode.getContent().toString());

        return sb.toString();
    }
}
