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

package com.github.subh0m0y.datastructures.queues;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Random;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Just to verify that the basic working is okay.
 *
 * @author Subhomoy Haldar
 * @version 2017.02.25
 */
@SuppressWarnings("RedundantThrows")
public class LinkedQueueTest {

    private final int size = 1_000_000;
    private final int limit = size * 2;
    private final Random random = new Random();

    private LinkedQueue<Integer> queue;
    private Integer[] mirror;

    @BeforeMethod
    public void setUp() throws Exception {
        queue = new LinkedQueue<>();
        mirror = new Integer[size];
        for (int i = 0; i < size; i++) {
            int randomInt = random.nextInt(limit);
            mirror[i] = randomInt;
            queue.enqueue(randomInt);
        }
    }

    @Test
    public void testSize() throws Exception {
        assertEquals(size, queue.size());
    }

    @Test
    public void testEnqueue() throws Exception {
        mirror = new Integer[size];
        queue.clear();
        assertTrue(queue.isEmpty());
        for (int i = 0; i < size; i++) {
            int randomInt = random.nextInt(limit);
            queue.enqueue(randomInt);
            mirror[i] = randomInt;
            assertEquals(i + 1, queue.size());
        }
        for (int element : mirror) {
            assertEquals(element, queue.peek().intValue());
            assertEquals(element, queue.dequeue().intValue());
        }
    }

    @Test
    public void testToArray() throws Exception {
        Object[] array = new Object[size];
        System.arraycopy(mirror, 0, array, 0, size);
        assertEquals(array, queue.toArray());
        assertEquals(mirror, queue.toArray(new Integer[size]));
    }

    @Test
    public void testCopy() throws Exception {
        Queue<Integer> copy = queue.copy();
        assertEquals(copy, queue);
        assertEquals(copy.toArray(new Integer[size]), mirror);
    }

    @Test
    public void testWillProbablyFitArray() throws Exception {
        assertTrue(queue.willProbablyFitArray());
    }

    @Test
    public void testToString() throws Exception {
        assertEquals(Arrays.toString(mirror), queue.toString());
    }
}
