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

import com.sun.istack.internal.NotNull;

import java.util.Comparator;

/**
 * @author Subhomoy Haldar
 * @version 2017.03.05
 */
public class PriorityQueue<E extends Comparable<E>> extends Container<E> {
    private static final int DEFAULT_CAPACITY = 16;

    private final Heap<E> heap;

    public PriorityQueue(final int initialCapacity, final Comparator<E> comparator) {
        heap = new Heap<>(initialCapacity, comparator);
    }

    public PriorityQueue(final int initialCapacity) {
        this(initialCapacity, Comparator.naturalOrder());
    }

    public PriorityQueue(final Comparator<E> comparator) {
        this(DEFAULT_CAPACITY, comparator);
    }

    @SuppressWarnings("RedundantTypeArguments")
    public PriorityQueue() {
        // The redundancy is necessary for it to compile
        this(DEFAULT_CAPACITY, Comparator.<E>naturalOrder());
    }

    private PriorityQueue(Heap<E> heap) {
        this.heap = heap;
    }

    public E peek() {
        return heap.first();
    }

    public E dequeue() {
        E first = heap.first();
        heap.removeIndex(0);
        return first;
    }

    public void enqueue(@NotNull final E data) {
        heap.insert(data);
    }

    /**
     * Returns the number of elements currently in the PriorityQueue. It is
     * guaranteed to be a non-negative number.
     * <p>
     * <b>NOTE:</b> If the number of elements exceeds
     * {@link Long#MAX_VALUE Long#MAX_VALUE}, then it will return
     * {@code Long#MAX_VALUE}.
     *
     * @return The number of elements in this Container.
     */
    @Override
    public long size() {
        return heap.size();
    }

    /**
     * Returns the elements of this Container in an array, if possible.
     * <p>
     * If it cannot fit the data into an array, and assuming no
     * {@link OutOfMemoryError} is thrown, this method will return {@code null}.
     *
     * @return The elements of this Container in an array, if possible.
     */
    @Override
    public Object[] toArray() {
        PriorityQueue<E> copy = copy();
        Object[] array = new Object[(int) size()];
        for (int i = 0; i < size(); i++) {
            array[i] = copy.dequeue();
        }
        return array;
    }

    /**
     * Returns the elements of the Container in the given array, if it
     * can accommodate, or a new array of the same type.
     * <p>
     * If it cannot fit the data into an array, and assuming no
     * {@link OutOfMemoryError} is thrown, this method will return {@code null}.
     *
     * @param array The array in which to store the elements if possible,
     *              or in a new array of the same type.
     * @return The elements of the Container in the given array, if it
     * can accommodate, or a new array of the same type.
     * @throws ClassCastException If the elements cannot be converted to
     *                            the given type.
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T extends E> T[] toArray(T[] array) throws ClassCastException {
        PriorityQueue<E> copy = copy();
        T[] container;
        container = array.length > size() ? array : (T[]) java.lang.reflect.Array.newInstance(
                array.getClass().getComponentType(), (int) size()
        );
        for (int i = 0; i < size(); i++) {
            container[i] = (T) copy.dequeue();
        }
        return container;
    }

    /**
     * Removes all of its elements.
     */
    @Override
    public void clear() {
        while (!isEmpty()) {
            dequeue();
        }
    }

    /**
     * Copies all the elements of this Container to a new one of the same
     * type (i.e. returns a FixedStack for a FixedStack and so on).
     *
     * @return A new Container with the same elements in the same order
     * (if order is defined) and the same properties as this Container.
     */
    @Override
    public PriorityQueue<E> copy() {
        return new PriorityQueue<>(heap.copy());
    }
}
