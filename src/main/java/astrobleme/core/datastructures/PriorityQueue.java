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

import astrobleme.core.datastructures.exceptions.UnderflowException;
import astrobleme.core.datastructures.heaps.BinaryHeap;
import astrobleme.core.datastructures.heaps.Heap;
import com.sun.istack.internal.NotNull;

import java.util.Comparator;

/**
 * PriorityQueue is an implementation which cosmetically similar to any
 * Queue, but it has an additional property: each element has an associated
 * priority with it. The priority can be the position in which the element
 * would normally be when sorted, or any custom priority, which can be
 * implemented using a {@link Comparator}.
 * <p>
 * <b>NOTE:</b>
 * This is backed by a min-heap, so the <i>lower</i> the priority value,
 * the more important it is. For example, in the case of Integers,
 * normally, 1 has more priority than 2, 2 has more priority than 3 and
 * so on.
 * <p>
 * This PriorityQueue expands as needed.
 *
 * @author Subhomoy Haldar
 * @version 2017.03.05
 * @see Comparable
 * @see Comparator
 */
public class PriorityQueue<E extends Comparable<E>> extends Queue<E> {
    private static final int DEFAULT_CAPACITY = 16;

    // The backing Heap. It is modular, so that I can test different Heaps with it
    private final Heap<E> heap;

    /**
     * Creates an empty {@link PriorityQueue} with the desired initial minimum
     * capacity and the comparator to be used to judge priority.
     * <p>
     * <b>NOTE:</b> The smallest value judged by the Comparator gets the most
     * priority.
     *
     * @param initialCapacity The required minimum capacity.
     * @param comparator      It is used to order the priorities.
     */
    public PriorityQueue(final int initialCapacity, final Comparator<E> comparator) {
        heap = new BinaryHeap<>(initialCapacity, comparator);
    }

    /**
     * Creates a new {@link PriorityQueue} with the desired initial minimum
     * capacity.
     *
     * @param initialCapacity The required minimum capacity.
     */
    public PriorityQueue(final int initialCapacity) {
        this(initialCapacity, Comparator.naturalOrder());
    }

    /**
     * Creates a new {@link PriorityQueue} with the default size and
     * the comparator to be used to judge priority.
     *
     * @param comparator It is used to order the priorities.
     */
    public PriorityQueue(final Comparator<E> comparator) {
        this(DEFAULT_CAPACITY, comparator);
    }

    /**
     * Creates a new {@link PriorityQueue} with the default size and
     * the default Comparator.
     */
    @SuppressWarnings("RedundantTypeArguments")
    public PriorityQueue() {
        // The redundancy is necessary for it to compile
        this(DEFAULT_CAPACITY, Comparator.<E>naturalOrder());
    }

    /**
     * An internal constructor that is to be used to create a copy
     * of a Priority Queue.
     *
     * @param heap The copied heap.
     */
    private PriorityQueue(Heap<E> heap) {
        this.heap = heap;
    }

    /**
     * Returns the element currently with the highest priority (with
     * the least value as per the {@link Comparator}), but does not
     * remove it. If the Queue is empty, it returns {@code null}
     *
     * @return The element currently with the highest priority (with
     * the least value as per the {@link Comparator}), without removing it.
     */
    @Override
    public E peek() {
        return isEmpty() ? null : heap.first();
    }

    /**
     * Returns the element currently with the highest priority (with
     * the least value as per the {@link Comparator}) and also removes
     * it. If the Queue is empty, an UnderflowException is thrown.
     *
     * @return The element currently with the highest priority (with
     * the least value as per the {@link Comparator}), and also
     * removes it from the queue.
     * @throws UnderflowException If the Queue is empty.
     */
    @Override
    public E dequeue() throws UnderflowException {
        if (isEmpty()) {
            throw new UnderflowException();
        }
        E first = heap.first();
        heap.remove(first);
        return first;
    }

    @Override
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
     * Returns the elements of the Queue in the given array in sorted
     * order, if it can accommodate, or a new array of the same type.
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
     * Copies all the elements of this PriorityQueue to a new one of the same
     * type (i.e. returns a FixedStack for a FixedStack and so on).
     *
     * @return A new Priority Queue with the same elements in the same order
     * and the same properties.
     */
    @Override
    public PriorityQueue<E> copy() {
        return new PriorityQueue<>(heap.copy());
    }
}
