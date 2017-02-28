/*
 * The MIT License (MIT)
 * Copyright (c) 2016-2017 Subhomoy Haldar
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package astrobleme.core.datastructures;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Random;

import static org.testng.Assert.*;

/**
 * Checks for repeatable, random values. If it passes, then the
 * BST is most probably bug free.
 *
 * @author Subhomoy Haldar
 * @version 2017.02.27
 */
public class BinarySearchTreeTest {
    private final int size = 1_000_000;
    private final int limit = size * 2;
    private final Random random = new Random();

    private BinarySearchTree<Integer> tree;
    private Integer[] mirror;

    @Test
    public void testInsertAndIsPresent() throws Exception {
        tree = new BinarySearchTree<>();
        mirror = new Integer[size];
        for (int i = 0; i < size; i++) {
            int randomInt = random.nextInt(limit);
            tree.insert(randomInt);
            assertEquals(i + 1, tree.size());
            mirror[i] = randomInt;
        }
        for (int element : mirror) {
            assertTrue(tree.isPresent(element));
        }
    }

    @Test
    public void testRemove() throws Exception {
        tree = new BinarySearchTree<>();
        mirror = new Integer[size];
        for (int i = 0; i < size; i++) {
            int randomInt = random.nextInt(limit);
            tree.insert(randomInt);
            assertEquals(i + 1, tree.size());
            mirror[i] = randomInt;
        }
        int s = size;
        for (int element : mirror) {
            if (tree.remove(element)) {
                s--;
                assertEquals(s, tree.size());
            }
        }
    }

    @Test
    public void testMaxMinInOrder() throws Exception {
        tree = new BinarySearchTree<>();
        mirror = new Integer[size];
        for (int i = 0; i < size; i++) {
            int randomInt = random.nextInt(limit);
            tree.insert(randomInt);
            assertEquals(i + 1, tree.size());
            mirror[i] = randomInt;
        }
        Arrays.sort(mirror);
        assertEquals(mirror[0], tree.min());
        assertEquals(mirror[size - 1], tree.max());
        assertEquals(mirror, tree.toArray());
        assertEquals(mirror, tree.toArray(new Integer[size]));
    }

    @Test
    public void testMaxDepth() throws Exception {
        tree = new BinarySearchTree<>();
        for (int i = 0; i < size; i++) {
            int randomInt = random.nextInt(limit);
            tree.insert(randomInt);
            assertEquals(i + 1, tree.size());
        }
        int maxDepth = tree.maxDepth();
        assertTrue(minDepth(tree.size()) <= maxDepth);
        assertTrue(tree.size() >= maxDepth);
    }

    @Test
    public void testCopy() throws Exception {
        tree = new BinarySearchTree<>();
        for (int i = 0; i < size; i++) {
            int randomInt = random.nextInt(limit);
            tree.insert(randomInt);
            assertEquals(i + 1, tree.size());
        }
        BinarySearchTree<Integer> copy = tree.copy();
        assertEquals(tree, copy);
        assertEquals(tree.inOrder(), copy.inOrder());
        assertEquals(tree.preOrder(), copy.preOrder());
        assertEquals(tree.postOrder(), copy.postOrder());
        assertEquals(tree.bfs(), copy.bfs());
    }

    private double minDepth(long size) {
        return Math.log(size) / Math.log(2);
    }
}