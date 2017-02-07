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

    @Override
    @SuppressWarnings("unchecked")
    public E pop() throws StackUnderflowException, ClassCastException {
        if (top == -1) {
            throw new StackUnderflowException();
        }
        return (E) a[top--];
    }

    @Override
    @SuppressWarnings("unchecked")
    public E peek() throws ClassCastException {
        return top == -1 ? null : (E) a[top];
    }

    @Override
    public int size() {
        return top + 1;
    }

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

    @Override
    public Iterator<E> iterator() {
        return new ArrayIterator<>(Arrays.copyOf(a, size()));
    }

    @Override
    public boolean push(E value) throws StackOverflowException {
        if (top == limit) {
            throw new StackOverflowException(limit + 1);
        }
        a[++top] = value;
        return true;
    }

    @Override
    public boolean add(E value) {
        a[++top] = value;
        return true;
    }
}
