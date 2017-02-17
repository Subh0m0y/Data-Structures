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

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Random;

import static org.testng.Assert.*;

/**
 * @author Subhomoy Haldar
 * @version 2017.02.15
 */
public class FixedStackTest {

    private final int size = 10;
    private final int limit = size * 2;
    private final Random random = new Random();

    private FixedStack<Integer> stack;
    private Integer[] mirror;

    @BeforeMethod
    public void setUp() throws Exception {
        stack = new FixedStack<>(size);
        mirror = new Integer[size];
        for (int i = size - 1; i >= 0; i--) {
            int randomInt = random.nextInt(limit);
            mirror[i] = randomInt;
            stack.push(randomInt);
        }
    }

    @Test
    public void testSize() throws Exception {
        assertEquals(size, stack.size());
    }

    @Test
    public void testPush() throws Exception {
        int[] store = new int[size];
        stack.clear();
        for (int i = 0, j = size - 1; i < size; i++, j--) {
            int randomInt = random.nextInt(limit);
            stack.push(randomInt);
            store[j] = randomInt;
            assertEquals(randomInt, stack.peek().intValue());
            assertEquals(i + 1, stack.size());
        }
        for (int element : store) {
            assertEquals(element, stack.pop().intValue());
        }
    }

    @Test
    public void testToArray() throws Exception {
        Object[] array = new Object[size];
        System.arraycopy(mirror, 0, array, 0, size);
        assertEquals(array, stack.toArray());
        assertEquals(mirror, stack.toArray(new Integer[size]));
    }

    @Test
    public void testCopy() throws Exception {
        Stack<Integer> copy = stack.copy();
        assertEquals(copy, stack);
        assertEquals(copy.toArray(new Integer[size]), mirror);
    }

    @Test
    public void testWillProbablyFitArray() throws Exception {
        assertTrue(stack.willProbablyFitArray());
    }

    @Test
    public void testToString() throws Exception {
        assertEquals(Arrays.toString(mirror), stack.toString());
    }
}