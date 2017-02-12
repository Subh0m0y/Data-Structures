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

import java.util.*;

import static org.testng.Assert.*;

/**
 * Tests for FixedStack. Most are intentionally trivial.
 *
 * @author Subhomoy Haldar
 * @version 2017.02.10
 */
public class FixedStackTest {

    /**
     * Reverses a given array in place.
     *
     * @param a The array whose elements to arrange in reverse order.
     */
    private static void reverse(Integer[] a) {
        int half = a.length >>> 1;
        for (int i = 0, j = a.length - 1; i < half; i++, j--) {
            int temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
    }

    private static final int LENGTH = 30_000;
    private static final int BOUND = LENGTH * 2;
    private static final int SEARCH_LIMIT = LENGTH * 3;
    private static final Random RANDOM = new Random();

    @Test
    public void testPushPopPeek() throws Exception {
        Stack<Integer> stack = new FixedStack<>(LENGTH);
        int[] storage = new int[LENGTH];
        for (int i = 0; i < LENGTH; i++) {
            int randomInt = RANDOM.nextInt(BOUND);
            stack.push(randomInt);
            storage[i] = randomInt;
            assertEquals(stack.size(), i + 1);
        }
        for (int i = LENGTH - 1; i >= 0; i--) {
            assertEquals(stack.peek().intValue(), storage[i],
                    "Didn't match at index: " + i);
            assertEquals(stack.pop().intValue(), storage[i],
                    "Didn't match at index: " + i);
        }
        boolean exceptionCaught = false;
        try {
            stack.pop();
        } catch (UnderflowException e) {
            exceptionCaught = true;
        }
        assertTrue(exceptionCaught);
        assertTrue(stack.isEmpty());
    }

    @Test
    public void testContains() throws Exception {
        Stack<Integer> stack = new FixedStack<>(LENGTH);
        int[] storage = new int[LENGTH];
        for (int i = 0; i < LENGTH; i++) {
            int randomInt = RANDOM.nextInt(BOUND);
            stack.push(randomInt);
            storage[i] = randomInt;
            assertEquals(stack.size(), i + 1);
        }
        Arrays.sort(storage);
        for (int i = 0; i < SEARCH_LIMIT; i++) {
            int randomVal = RANDOM.nextInt(BOUND);
            boolean arrayHasThisValue = Arrays.binarySearch(storage, randomVal) >= 0;
            assertEquals(stack.contains(randomVal), arrayHasThisValue);
        }
    }

    @Test
    public void testIterator() throws Exception {
        Stack<Integer> stack = new FixedStack<>(LENGTH);
        int[] storage = new int[LENGTH];
        for (int i = 0; i < LENGTH; i++) {
            int randomInt = RANDOM.nextInt(BOUND);
            stack.push(randomInt);
            storage[i] = randomInt;
            assertEquals(stack.size(), i + 1);
        }
        int index = LENGTH - 1;
        for (int element : stack) {
            assertEquals(element, storage[index--]);
        }
    }

    @Test
    public void testAdd() throws Exception {
        Stack<Integer> stack = new FixedStack<>(LENGTH);
        int[] storage = new int[LENGTH];
        for (int i = 0; i < LENGTH; i++) {
            int randomInt = RANDOM.nextInt(BOUND);
            stack.add(randomInt);
            storage[i] = randomInt;
            assertEquals(stack.size(), i + 1);
        }
        int index = LENGTH - 1;
        for (int element : stack) {
            assertEquals(element, storage[index--]);
        }
        boolean exceptionCaught = false;
        try {
            stack.add(12);
        } catch (OverflowException e) {
            exceptionCaught = true;
        }
        assertTrue(exceptionCaught);
    }

    @Test
    public void testRemoveAndRetain() throws Exception {
        Stack<Integer> stack = new FixedStack<>(1);
        stack.push(1);
        boolean exceptionCaught = false;
        try {
            stack.remove(1);
        } catch (UnsupportedOperationException e) {
            exceptionCaught = true;
        }
        assertTrue(exceptionCaught);
        exceptionCaught = false;
        try {
            stack.removeAll(1, 2, 3);
        } catch (UnsupportedOperationException e) {
            exceptionCaught = true;
        }
        assertTrue(exceptionCaught);
        exceptionCaught = false;
        try {
            stack.retainAll(Arrays.asList(1, 2, 3));
        } catch (UnsupportedOperationException e) {
            exceptionCaught = true;
        }
        assertTrue(exceptionCaught);
        stack.clear();
        assertTrue(stack.isEmpty());
    }

    @Test
    public void testContainsAll() throws Exception {
        Stack<Integer> stack = new FixedStack<>(LENGTH);
        Integer[] storage = new Integer[LENGTH];
        for (int i = 0; i < LENGTH; i++) {
            storage[i] = RANDOM.nextInt(BOUND);
        }
        stack.addAll(storage);
        assertTrue(stack.containsAll(storage));
        stack.clear();
        java.util.List<Integer> integers = Arrays.asList(storage);
        stack.addAll(integers);
        assertTrue(stack.containsAll(integers));
    }

    @Test
    public void testToArray() throws Exception {
        Stack<Integer> stack = new FixedStack<>(LENGTH);
        Integer[] storage = new Integer[LENGTH];
        for (int i = 0; i < LENGTH; i++) {
            int randomInt = RANDOM.nextInt(BOUND);
            storage[i] = randomInt;
            stack.push(randomInt);
        }
        reverse(storage);
        assertTrue(Arrays.equals(stack.toArray(new Integer[0]), storage));
        assertEquals(stack.toString(), Arrays.toString(storage));
    }

    @Test
    public void testEquals() throws Exception {
        Stack<Integer> stack1 = new FixedStack<>(LENGTH);
        Stack<Integer> stack2 = new FixedStack<>(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            int randomInt = RANDOM.nextInt(BOUND);
            stack1.push(randomInt);
            stack2.push(randomInt);
        }
        assertEquals(stack1, stack2);
        int element = stack2.pop() + 1;
        assertNotEquals(stack1, stack2);
        stack2.push(element);
        assertNotEquals(stack1, stack2);
    }
}