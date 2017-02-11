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

import java.util.Iterator;

/**
 * A concrete array-based Queue that is meant to be light and fast. This is
 * a fixed capacity Queue. At any given time it can hold a maximum of the
 * quantity initially specified.
 *
 * @author Subhomoy Haldar
 * @version 2017.02.11
 */
public class FixedQueue<E> extends Queue<E> {
    private final Object[] a;
    private int front;
    private int rear;
    private int size;

    /**
     * Creates a new Queue with the specified capacity.
     *
     * @param capacity The size of the Queue.
     */
    public FixedQueue(final int capacity) {
        a = new Object[capacity];
        front = rear = -1;
    }

    /**
     * {@inheritDoc}
     *
     * @param value {@inheritDoc}
     * @return {@inheritDoc}
     * @throws QueueOverflowException {@inheritDoc}
     */
    @Override
    public boolean enqueue(E value) throws QueueOverflowException {
        if (front == -1) {
            front = rear = 0;
        } else {
            int nextIndex = (rear + 1) % a.length;
            if (nextIndex == front) {
                throw new QueueOverflowException(a.length);
            }
            rear = nextIndex;
        }
        a[rear] = value;
        size++;
        return true;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     * @throws QueueUnderflowException {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public E dequeue() throws QueueUnderflowException {
        // If there are no elements
        if (front == -1) {
            throw new QueueUnderflowException();
        }
        E value = (E) a[front];
        if (front == rear) {
            // Reset
            front = rear = -1;
        } else {
            front = (front + 1) % a.length;
        }
        size--;
        return value;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public E peek() {
        return front != -1 ? (E) a[front] : null;
    }

    /**
     * Returns the number of elements in the Queue.
     *
     * @return The number of elements in the Queue.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Checks if the given element is present in the collection.
     *
     * @param object The object to check for presence.
     * @return {@code true} if the object is present.
     */
    @Override
    public boolean contains(Object object) {
        for (int i = front; i != rear; i = (i + 1) % a.length) {
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
     * Creates an iterator that returns the elements in the order they
     * will be dequeue-ed. This iterator is read-only. It cannot be used
     * to modify this Queue in any way. It should only be used to iterate
     * over the elements.
     *
     * @return An iterator that returns the elements in the order they
     * will be dequeue-ed.
     */
    @Override
    public Iterator<E> iterator() {
        Object[] array = new Object[size];
        int index = 0;
        int i = front;
        while (index < size) {
            array[index++] = a[i];
            i = (i + 1) % a.length;
        }
        return new ArrayIterator<>(array);
    }
}
