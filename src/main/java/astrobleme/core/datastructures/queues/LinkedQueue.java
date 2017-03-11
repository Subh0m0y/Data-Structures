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

import astrobleme.core.datastructures.utils.LinkedLists;
import astrobleme.core.datastructures.exceptions.OverflowException;
import astrobleme.core.datastructures.exceptions.UnderflowException;
import astrobleme.core.datastructures.nodes.SinglyLinkedNode;

/**
 * A Linked-List based implementation of a Queue that has the potential to
 * store more elements than an array can (depending on the VM configuration).
 *
 * @author Subhomoy Haldar
 * @version 2017.02.25
 */
public class LinkedQueue<E> extends Queue<E> {
    private SinglyLinkedNode<E> front;
    private SinglyLinkedNode<E> rear;
    private long size;

    /**
     * Creates a new empty Queue.
     */
    public LinkedQueue() {
        front = rear = null;
        size = 0;
    }

    /**
     * Returns the number of elements currently in the Queue. It is
     * guaranteed to be a non-negative number.
     * <p>
     * <b>NOTE:</b> If the number of elements exceeds
     * {@link Long#MAX_VALUE Long#MAX_VALUE}, then it will return
     * {@code Long#MAX_VALUE}.
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
        SinglyLinkedNode<E> node = new SinglyLinkedNode<>(element);
        if (front == null) {
            // No elements
            front = rear = node;
        } else {
            rear.setNext(node);
            rear = node;
        }
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
    public E dequeue() throws UnderflowException {
        if (front == null) {
            throw new UnderflowException();
        }
        E data = front.data;
        front = front.getNext();
        size--;
        return data;
    }

    /**
     * Returns the front-most element of the Queue but does not remove it.
     * If the Queue is empty, it will not throw an {@link UnderflowException}.
     * Instead, it will simply return null.
     *
     * @return The front-most element of the Queue.
     */
    @Override
    public E peek() {
        return front == null ? null : front.data;
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
        if (!willProbablyFitArray()) {
            return null;
        }
        return LinkedLists.toArray(front, (int) size);
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
        if (!willProbablyFitArray()) {
            return null;
        }
        return LinkedLists.toArray(array, front, (int) size);
    }

    /**
     * Creates an exact copy of the Queue, with the same initial parameters
     * like capacity, pointer locations and of course, the elements.
     *
     * @return An exact copy of this Queue.
     */
    @Override
    public Queue<E> copy() {
        SinglyLinkedNode<E> front2 = new SinglyLinkedNode<>(front.data);
        LinkedLists.copy(front, front2);
        LinkedQueue<E> copy = new LinkedQueue<>();
        copy.front = front2;
        copy.size = size;
        return copy;
    }

    /**
     * Override of the standard implementation, allowing for checking potentially
     * large LinkedQueues reliably.
     *
     * @param object The object to check against.
     * @return {@code true} if the given object is a LinkedQueue and has the same
     * elements in the required order.
     */
    @Override
    public boolean equals(Object object) {
        // Same reason as LinkedStack
        if (!(object instanceof LinkedQueue)) {
            return super.equals(object);
        }
        LinkedQueue queue = (LinkedQueue) object;
        return size == queue.size && LinkedLists.areEqual(front, queue.front);
    }
}
