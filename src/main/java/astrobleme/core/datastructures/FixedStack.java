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

/**
 * An array-based implementation of a Stack that has a fixed capacity.
 * It is designed to be light-weight and fast.
 *
 * @author Subhomoy Haldar
 * @version 2017.02.15
 */
public class FixedStack<E> extends Stack<E> {
    private final Object[] a;
    private int top;

    /**
     * Creates a new Stack with the given capacity.
     *
     * @param capacity The required capacity.
     */
    public FixedStack(final int capacity) {
        a = new Object[capacity];
        top = -1;
    }

    /**
     * Returns the number of elements currently in the Stack. It is
     * guaranteed to be a non-negative number.
     * <p>
     *
     * @return The number of elements in this Stack.
     */
    @Override
    public long size() {
        return top + 1;
    }

    /**
     * Adds an element onto the Stack. This element (if successfully pushed
     * onto the Stack) will the one that will be returned by a subsequent
     * call to {@link #pop()}.
     *
     * @param element The element to push onto the Stack.
     * @throws OverflowException If the Stack tries to exceed its fixed capacity
     *                           or cannot accommodate too many elements.
     */
    @Override
    public void push(E element) throws OverflowException {
        if (top == a.length - 1) {
            throw new OverflowException(a.length);
        }
        a[++top] = element;
    }

    /**
     * Returns the element that was most recently added to the Stack and
     * also removes it from the Stack. If the Stack is empty, that is no
     * element was added, it will throw an {@link UnderflowException}.
     *
     * @return The most recently added element.
     * @throws UnderflowException If the Stack is empty.
     */
    @Override
    @SuppressWarnings("unchecked")
    public E pop() throws UnderflowException {
        if (top == -1) {
            throw new UnderflowException();
        }
        return (E) a[top--];
    }

    /**
     * Returns the element that was most recently added to the Stack but
     * does not remove it. If the Stack is empty, it will not throw an
     * Exception. Instead, it returns {@code null}.
     *
     * @return The most recently added element.
     */
    @Override
    @SuppressWarnings("unchecked")
    public E peek() {
        return top == -1 ? null : (E) a[top];
    }

    /**
     * Returns the elements of this Stack in an array. It creates an
     * independent "snapshot" of its contents when this method is invoked,
     * which is not affected when the Stack is modified later.
     *
     * @return The elements of this Container in an array.
     */
    @Override
    Object[] toArray() {
        Object[] elements = Arrays.copyOf(a, top + 1);
        ArrayUtil.reverse(elements, 0, elements.length);
        return elements;
    }

    /**
     * Returns the snapshot of the elements of the Stack in the given array,
     * if it can accommodate, or a new array of the same type.
     * <p>
     * If it cannot fit the data into an array, and assuming no
     * {@link OutOfMemoryError} is thrown, this method will return {@code null}.
     *
     * @param array The array in which to store the elements if possible,
     *              or in a new array of the same type.
     * @return The elements of the Container in the given array, if it
     * can accommodate, or a new array of the same type.
     */
    @Override
    @SuppressWarnings("unchecked")
    <T extends E> T[] toArray(T[] array) {
        T[] container;
        if (array.length > top) {
            container = array;
        } else {
            container = (T[]) java.lang.reflect.Array.newInstance(
                    array.getClass().getComponentType(), top + 1
            );
        }
        for (int i = 0, j = top; i <= top; i++, j--) {
            container[j] = (T) a[i];
        }
        return container;
    }
}
