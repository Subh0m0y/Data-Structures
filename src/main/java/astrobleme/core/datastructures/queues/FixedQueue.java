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

package astrobleme.core.datastructures.queues;

import astrobleme.core.datastructures.exceptions.OverflowException;
import astrobleme.core.datastructures.exceptions.UnderflowException;

/**
 * An array-based implementation of a Queue that has a fixed capacity.
 * It is designed to be light-weight and fast as well.
 *
 * @author Subhomoy Haldar
 * @version 2017.02.24
 */
public class FixedQueue<E> extends Queue<E> {
    private final Object[] a;
    private int front;
    private int rear;
    private int size;

    /**
     * Creates a new Queue with the given capacity.
     * <p>
     * <b>NOTE:</b> The capacity must be non-negative.
     *
     * @param capacity The required non-negative capacity.
     */
    public FixedQueue(final int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity must be non-negative.");
        }
        a = new Object[capacity];
        front = rear = -1;
        size = 0;
    }

    /**
     * Returns the number of elements currently in the Queue. It is
     * guaranteed to be a non-negative number.
     *
     * @return The number of elements in this Queue.
     */
    @Override
    public long size() {
        return size;
    }

    /**
     * Adds an element to the "end" of the queue, which will wait until
     * all the elements before it are removed. After that, a call to
     * {@link #dequeue()} will return this element.
     *
     * @param element The element to add to the Queue.
     * @throws OverflowException If the Queue tries to exceed its fixed capacity
     *                           or cannot accommodate any more elements
     */
    @Override
    public void enqueue(E element) throws OverflowException {
        if (front == -1) {
            // This means that the Queue is empty
            front = 0;
            rear = 0;
        } else {
            // Increment the rear with a wrap-around
            rear = (rear + 1) % a.length;
            if (rear == front) {
                throw new OverflowException(a.length);
            }
        }
        a[rear] = element;
        size++;
    }

    /**
     * Returns the element that has been in the Queue for the longest,
     * i.e. the front-most element of the Queue. If the Queue is
     * empty or no element, it will throw an {@link UnderflowException}.
     *
     * @return The front-most element in the Queue.
     * @throws UnderflowException If the Queue has no elements.
     */
    @Override
    @SuppressWarnings("unchecked")
    public E dequeue() throws UnderflowException {
        if (front == -1) {
            // Queue is empty
            throw new UnderflowException();
        }
        E element = (E) a[front];
        a[front] = null;    // Remove the reference to help GC
        if (front == rear) {
            // Reset to the beginning
            front = rear = -1;
        } else {
            front = (front + 1) % a.length;
        }
        size--;
        return element;
    }

    /**
     * Returns the front-most element of the Queue but does not remove it.
     * If the Queue is empty, it will not throw an {@link UnderflowException}.
     * Instead, it will simply return null.
     *
     * @return The front-most element of the Queue.
     */
    @Override
    @SuppressWarnings("unchecked")
    public E peek() {
        return front == -1 ? null : (E) a[front];
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
        if (front == -1) {
            // An empty array for an empty Queue
            return new Object[0];
        }
        Object[] elements = new Object[size];
        if (front <= rear) {
            System.arraycopy(a, front, elements, 0, size);
        } else {
            int separator = a.length - front;
            System.arraycopy(a, front, elements, 0, separator);
            System.arraycopy(a, 0, elements, separator, rear + 1);
        }
        return elements;
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
    @SuppressWarnings("unchecked")
    @Override
    public <T extends E> T[] toArray(T[] array) throws ClassCastException {
        T[] container;
        if (array.length >= size) {
            container = array;
        } else {
            container = (T[]) java.lang.reflect.Array.newInstance(
                    array.getClass().getComponentType(), size
            );
        }
        for (int i = front, j = 0; j < size; i = (i + 1) % a.length, j++) {
            container[j] = (T) a[i];
        }
        return container;
    }

    /**
     * Creates an exact copy of the Queue, with the same initial parameters
     * like capacity, pointer locations and of course, the elements.
     *
     * @return An exact copy of this Queue.
     */
    @Override
    public Queue<E> copy() {
        FixedQueue<E> copy = new FixedQueue<>(a.length);
        System.arraycopy(a, 0, copy.a, 0, a.length);
        copy.front = front;
        copy.rear = rear;
        copy.size = size;
        return copy;
    }
}
