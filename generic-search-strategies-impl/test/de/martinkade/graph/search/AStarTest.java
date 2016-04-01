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

import de.martinkade.graph.DuplicateNodeException;
import de.martinkade.graph.Graph;
import de.martinkade.graph.Heuristic;
import de.martinkade.graph.Node;
import de.martinkade.graph.Path;
import de.martinkade.graph.search.navigation.City;
import de.martinkade.graph.search.navigation.Highway;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test implementations of {@link AStar} strategy.
 *
 * @author martinkade
 * @version 2015-11-02
 */
public class AStarTest {

    /**
     *
     */
    private static final String TAG = AStarTest.class.getName();

    /**
     * The {@link Graph} instance.
     */
    private Graph<City, Highway> map;

    /**
     * References to the start and target node, respectively.
     */
    private Node<City, Highway> startNode, targetNode;

    /**
     * The navigation specific {@link Heuristic} implementation.
     */
    private Heuristic<City, Highway> heuristic;

    /**
     * Default constructor.
     */
    public AStarTest() {
        // empty
    }

    @BeforeClass
    public static void setUpClass() {
        // empty
    }

    @AfterClass
    public static void tearDownClass() {
        // empty
    }

    @Before
    public void setUp() {
        map = new Graph<>();

        final Node<City, Highway> bayreuthNode = new Node<>(new City("Bayreuth"));
        final Node<City, Highway> munichNode = new Node<>(new City("Munich"));
        final Node<City, Highway> cologneNode = new Node<>(new City("Cologne"));
        final Node<City, Highway> berlinNode = new Node<>(new City("Berlin"));
        final Node<City, Highway> deggendorfNode = new Node<>(new City("Deggendorf"));

        try {
            bayreuthNode.addSuccessor(munichNode, new Highway("A9", 233.0d));
            bayreuthNode.addSuccessor(cologneNode, new Highway("A45", 472.0d));
            bayreuthNode.addSuccessor(deggendorfNode, new Highway("A93", 233.0d));
            map.addNode(bayreuthNode);

            deggendorfNode.addSuccessor(munichNode, new Highway("A92", 153.0d));
            map.addNode(deggendorfNode);

            munichNode.addSuccessor(cologneNode, new Highway("A3", 574.0d));
            map.addNode(munichNode);

            cologneNode.addSuccessor(berlinNode, new Highway("A2", 575.0d));
            map.addNode(cologneNode);

            berlinNode.addSuccessor(bayreuthNode, new Highway("A9", 416.0d));
            map.addNode(berlinNode);
        } catch (DuplicateNodeException ex) {
            Logger.getLogger(AStarTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        startNode = bayreuthNode;
        targetNode = berlinNode;

        heuristic = (Node<City, Highway> node, Node<City, Highway> otherNode) -> {
            if (otherNode.getCosts() < node.getCosts()) {
                return 1;
            }
            if (otherNode.getCosts() > node.getCosts()) {
                return -1;
            }
            return 0;
        };
    }

    @After
    public void tearDown() {
        // empty
    }

    /**
     * Test of
     * {@link AStar#execute(de.martinkade.graph.Graph, de.martinkade.graph.Node)}.
     */
    @Test
    public void testExecute() {
        System.out.println(String.format("@%s#testExecute", TAG));

        final Strategy<City, Highway> aStar = new AStar<>(startNode, heuristic);
        final Path<City, Highway> path = aStar.execute(map, targetNode);

        final String expResult = "Bayreuth -> Cologne -> Berlin";
        final String result = path.toString();

        assertEquals(expResult, result);
        
        System.out.println("Path: " + path.toString());
        System.out.println(
                String.format("execution time: %.3f [s]",
                        aStar.getExecTimeSeconds()
                )
        );
    }
}
