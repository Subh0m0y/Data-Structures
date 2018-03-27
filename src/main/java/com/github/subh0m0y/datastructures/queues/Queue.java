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

package com.github.subh0m0y.datastructures.queues;

import com.github.subh0m0y.datastructures.Container;
import com.github.subh0m0y.datastructures.exceptions.OverflowException;
import com.github.subh0m0y.datastructures.exceptions.UnderflowException;

/**
 * Defines the basic Structure for all Queues. A Queue is a data structure
 * that provides FIFO (first in first out) functionality. It achieves this
 * via two methods: {@link #enqueue(Object)} and {@link #dequeue()}. The
 * former adds the element to the end of the Queue and will be returned
 * once all the elements before it have been removed (and returned)
 * using the latter. {@link #dequeue()} returns the element which has been
 * in the Queue for the longest, or the "front-most" element.
 * <p>
 * There is an additional method {@link #peek()} that returns the front-most
 * element, without removing it from the Queue.
 *
 * @author Subhomoy Haldar
 * @version 2017.02.24
 */
public abstract class Queue<E> extends Container<E> {
    /**
     * Adds an element to the "end" of the queue, which will wait until
     * all the elements before it are removed. After that, a call to
     * {@link #dequeue()} will return this element.
     *
     * @param element The element to add to the Queue.
     * @throws OverflowException If the Queue tries to exceed its fixed capacity
     *                           or cannot accommodate any more elements
     */
    public abstract void enqueue(E element) throws OverflowException;

    /**
     * Returns the element that has been in the Queue for the longest,
     * i.e. the front-most element of the Queue. If the Queue is
     * empty or no element, it will throw an {@link UnderflowException}.
     *
     * @return The front-most element in the Queue.
     * @throws UnderflowException If the Queue has no elements.
     */
    public abstract E dequeue() throws UnderflowException;

    /**
     * Returns the front-most element of the Queue but does not remove it.
     * If the Queue is empty, it will not throw an {@link UnderflowException}.
     * Instead, it will simply return null.
     *
     * @return The front-most element of the Queue.
     */
    public abstract E peek();

    /**
     * Creates an exact copy of the Queue, with the same initial parameters
     * like capacity, pointer locations and of course, the elements.
     *
     * @return An exact copy of this Queue.
     */
    @Override
    public abstract Queue<E> copy();

    /**
     * Removes all of its elements.
     */
    @Override
    public void clear() {
        while (!isEmpty()) {
            dequeue();
        }
        System.gc();
    }
}
