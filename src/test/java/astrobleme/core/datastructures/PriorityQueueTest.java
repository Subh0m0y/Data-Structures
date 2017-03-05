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

import java.util.Comparator;
import java.util.Random;

import static org.testng.Assert.*;

/**
 * @author Subhomoy Haldar
 * @version 2017.03.05
 */
public class PriorityQueueTest {
    private final int size = 1_000_000;
    //    private final int limit = size * 2;
    private final Random random = new Random();

    private PriorityQueue<Integer> queue;
    private Integer[] mirror;

    @Test
    public void testAll() throws Exception {
        queue = new PriorityQueue<>(size);
        mirror = new Integer[size];
        for (int i = 0; i < size; i++) {
            mirror[i] = i;
        }
        ArrayUtil.shuffle(mirror, random);
        for (int i = 0; i < size; i++) {
            queue.enqueue(mirror[i]);
            assertEquals(queue.size(), i + 1);
        }
        for (int i = 0; i < 10; i++) {
            assertTrue(queue.contains(mirror[i]));
        }
        for (int i = 0; i < size; i++) {
            assertEquals(queue.peek().intValue(), i);
            assertEquals(queue.dequeue().intValue(), i);
        }
        queue.clear();
        assertEquals(queue.size(), 0);
    }

    @Test
    public void testReverse() throws Exception {
        queue = new PriorityQueue<>(size, Comparator.<Integer>reverseOrder());
        mirror = new Integer[size];
        for (int i = 0; i < size; i++) {
            mirror[i] = size - i - 1;
        }
        ArrayUtil.shuffle(mirror, random);
        for (int i = 0; i < size; i++) {
            queue.enqueue(mirror[i]);
            assertEquals(queue.size(), i + 1);
        }
        for (int i = 0; i < 10; i++) {
            assertTrue(queue.contains(mirror[i]));
        }
        for (int i = size - 1; i >= 0; i--) {
            assertEquals(queue.peek().intValue(), i);
            assertEquals(queue.dequeue().intValue(), i);
        }
        queue.clear();
        assertEquals(queue.size(), 0);
    }

    @Test
    public void testAllWithResize() throws Exception {
        queue = new PriorityQueue<>();
        mirror = new Integer[size];
        for (int i = 0; i < size; i++) {
            mirror[i] = i;
        }
        ArrayUtil.shuffle(mirror, random);
        for (int i = 0; i < size; i++) {
            queue.enqueue(mirror[i]);
            assertEquals(queue.size(), i + 1);
        }
        for (int i = 0; i < 10; i++) {
            assertTrue(queue.contains(mirror[i]));
        }
        for (int i = 0; i < size; i++) {
            assertEquals(queue.peek().intValue(), i);
            assertEquals(queue.dequeue().intValue(), i);
        }
        queue.clear();
        assertEquals(queue.size(), 0);
    }

    @Test
    public void testReverseWithResize() throws Exception {
        queue = new PriorityQueue<>(Comparator.<Integer>reverseOrder());
        mirror = new Integer[size];
        for (int i = 0; i < size; i++) {
            mirror[i] = size - i - 1;
        }
        ArrayUtil.shuffle(mirror, random);
        for (int i = 0; i < size; i++) {
            queue.enqueue(mirror[i]);
            assertEquals(queue.size(), i + 1);
        }
        for (int i = 0; i < 10; i++) {
            assertTrue(queue.contains(mirror[i]));
        }
        for (int i = size - 1; i >= 0; i--) {
            assertEquals(queue.peek().intValue(), i);
            assertEquals(queue.dequeue().intValue(), i);
        }
        queue.clear();
        assertEquals(queue.size(), 0);
    }
}