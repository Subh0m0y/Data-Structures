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
        Object[] array = new Object[(int) size];
        int index = 0;
        SinglyLinkedNode<E> current = front;
        while (current != null) {
            array[index++] = current.data;
            current = current.getNext();
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
        T[] container;
        if (array.length > size) {
            container = array;
        } else {
            container = (T[]) java.lang.reflect.Array.newInstance(
                    array.getClass().getComponentType(), (int) size
            );
        }
        int index = 0;
        SinglyLinkedNode<E> current = front;
        while (current != null) {
            container[index++] = (T) current.data;
            current = current.getNext();
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
        SinglyLinkedNode<E> top2 = new SinglyLinkedNode<>(front.data);
        SinglyLinkedNode<E> node1 = front;
        SinglyLinkedNode<E> node2 = top2;
        while (node1.getNext() != null) {
            // Progress to the next node of this queue and get ready
            // to link it to the node of the new queue.
            node1 = node1.getNext();
            node2.setNext(new SinglyLinkedNode<>(node1.data));
            node2 = node2.getNext();
        }
        LinkedQueue<E> copy = new LinkedQueue<>();
        copy.front = top2;
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
        if (size != queue.size) {
            return false;
        }
        // NOTE: Node 1 is for this queue and node 2
        // is for the other queue.
        SinglyLinkedNode node1 = front;
        SinglyLinkedNode node2 = queue.front;
        // Iterate until one of them gets exhausted
        while (node1 != null && node2 != null) {
            // Safe guard against null data
            if (node1.data == null) {
                if (node2 .data!= null) {
                    return false;
                } else {
                    continue;
                }
            }
            // For normal non-null data
            if (!node1.data.equals(node2.data)) {
                return false;
            }
            node1 = node1.getNext();
            node2 = node2.getNext();
        }
        // Ensure that both of the lists got exhausted
        return node1 == null && node2 == null;
    }
}
