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
 * A Queue is a simple data structure that offers FIFO (first in first out)
 * functionality and is useful in many algorithms. This superclass defines
 * all the operations that an implementation of a Queue should have.
 *
 * @author Subhomoy Haldar
 * @version 2017.02.10
 */
public abstract class Queue<E> extends IterableCollection<E> {

    /**
     * Adds an element to the "end" of the Queue. This will stay until all the
     *
     * @param value The value to enqueue onto the Queue.
     * @return {@code true} if the element was added successfully.
     * @throws OverflowException If the fixed Queue is full or it has
     *                           too many elements.
     */
    public abstract boolean enqueue(E value) throws OverflowException;

    /**
     * Adds the element to this queue. This is the same as enqueueing it
     * onto the Queue. This may throw a {@link OverflowException}
     * depending on the implementation as well as the circumstances.
     *
     * @param value The value to add to Queue.
     * @return {@code true} if the element was successfully added to the Queue.
     */
    @Override
    public boolean add(E value) {
        return enqueue(value);
    }

    /**
     * Returns the "front-most" element of the Queue, i.e. the element that has
     * stayed for the longest in the Queue. This also removes the element from the Queue.
     *
     * @return The "front-most" element of the Queue, i.e. the element that has
     * stayed for the longest in the Queue.
     * @throws UnderflowException If there are no more elements to remove the Queue.
     */
    public abstract E dequeue() throws UnderflowException;

    /**
     * Returns the "front-most" element of the Queue but does not remove it.
     * This does not throw an Exception if the queue is empty.
     *
     * @return The "front-most" element of the Queue but does not remove it.
     */
    public abstract E peek();

    /**
     * Removes all the elements from this queue.
     */
    @Override
    public void clear() {
        while (!isEmpty()) {
            dequeue();
        }
    }

    /**
     * Prevent removal of other types of elements. This is to ensure that it works
     * in FIFO mode only. This is also to ensure good performance.
     *
     * @param object The object to remove. But it won't be removed.
     * @return Nothing. It'll throw an Exception.
     * @throws UnsupportedOperationException Because random removal is not allowed.
     */
    @Override
    public boolean remove(Object object) {
        throw new UnsupportedOperationException("Queues work in FIFO mode only.");
    }

    /**
     * Returns {@code true} if the argument is a Queue and has the same elements
     * in the same order.
     *
     * @param other The other object to check for equality.
     * @return {@code true} if the argument is a Queue and has the same elements
     * in the same order.
     */
    public boolean equals(Object other) {
        if (!(other instanceof Queue)) {
            return false;
        }
        Queue queue = (Queue) other;
        return Arrays.equals(queue.toArray(), toArray());
    }
}
