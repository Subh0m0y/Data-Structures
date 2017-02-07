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

import java.util.Arrays;
import java.util.Iterator;

/**
 * A concrete implementation of a Stack that has a fixed capacity. This
 * is meant too be used for speed. Ideally, the lack of resizing operations
 * and internal use of arrays makes it suitable for high-performance usage.
 *
 * @author Subhomoy Haldar
 * @version 2017.02.07
 */
public class FixedStack<E> extends Stack<E> {

    private final Object[] a;
    private final int limit;    // The maximum value that top is allowed to have
    private int top;            // The index of the topmost element

    /**
     * Creates a new Stack with the given size.
     *
     * @param capacity The required capacity of the Stack.
     */
    public FixedStack(final int capacity) {
        a = new Object[capacity];
        limit = capacity - 1;
        top = -1;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     * @throws StackUnderflowException {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public E pop() throws StackUnderflowException {
        if (top == -1) {
            throw new StackUnderflowException();
        }
        return (E) a[top--];
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public E peek() {
        return top == -1 ? null : (E) a[top];
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int size() {
        return top + 1;
    }

    /**
     * Checks if the given element is present in the collection.
     *
     * @param object The object to check for presence.
     * @return {@code true} if the object is present.
     */
    @Override
    public boolean contains(Object object) {
        for (int i = 0; i <= top; i++) {
            Object item = a[i];
            if (item == null) {
                if (object == null) {
                    return true;
                }
            } else {
                if (item.equals(object)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Creates an iterator that prints out the elements in the order they will
     * be popped.
     *
     * @return
     */
    @Override
    public Iterator<E> iterator() {
        Object[] array = new Object[size()];
        // Reverse the array to show order in which elements will pop
        int index = top, i = 0;
        while (index >= 0) {
            array[i++] = a[index--];
        }
        return new ArrayIterator<>(array);
    }

    /**
     * {@inheritDoc}
     * <p>
     * This can only work the number of times initially specified (as the capacity).
     * Any more than the capacity, and it will throw an Exception.
     *
     * @param value The value to push onto the Stack.
     * @return {@inheritDoc}
     * @throws StackOverflowException If the FixedStack is full and can take
     *                                no more elements.
     */
    @Override
    public boolean push(E value) throws StackOverflowException {
        if (top == limit) {
            throw new StackOverflowException(limit + 1);
        }
        a[++top] = value;
        return true;
    }
}
