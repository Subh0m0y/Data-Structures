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
 * A Linked-List based implementation of a Stack that has the potential to
 * store more elements than an array can (depending on the VM configuration).
 *
 * @author Subhomoy Haldar
 * @version 2017.02.15
 */
public class LinkedStack<E> extends Stack<E> {
    private SinglyLinkedNode<E> top;
    private long size;

    /**
     * Creates a new empty Stack.
     */
    public LinkedStack() {
        top = null;
        size = 0;
    }

    /**
     * Returns the number of elements currently in the Container. It is
     * guaranteed to be a non-negative number.
     * <p>
     * <b>NOTE:</b> If the number of elements exceeds
     * {@link Long#MAX_VALUE Integer#MAX_VALUE}, then it will return
     * {@code Long#MAX_VALUE}.
     *
     * @return The number of elements in this Container.
     */
    @Override
    long size() {
        return size;
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
        SinglyLinkedNode<E> node = new SinglyLinkedNode<>(element);
        if (top == null) {
            // No elements
            top = node;
            size++;
            return;
        }
        node.setNext(top);
        top = node;
        size++;
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
        if (top == null) {
            throw new UnderflowException();
        }
        E data = top.data;
        top = top.getNext();
        size--;
        return data;
    }

    /**
     * Returns the element that was most recently added to the Stack but
     * does not remove it. If the Stack is empty, it will not throw an
     * Exception. Instead, it returns {@code null}.
     *
     * @return The most recently added element.
     */
    @Override
    public E peek() {
        return top == null ? null : top.data;
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
        SinglyLinkedNode<E> current = top;
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
        SinglyLinkedNode<E> current = top;
        while (current != null) {
            container[index++] = (T) current.data;
            current = current.getNext();
        }
        return container;
    }

    /**
     * Creates an exact copy of the Stack, with the same initial parameters
     * (like capacity) and the same state of elements.
     *
     * @return An exact copy of this Stack.
     */
    @Override
    public Stack<E> copy() {
        SinglyLinkedNode<E> top2 = new SinglyLinkedNode<>(top.data);
        SinglyLinkedNode<E> node1 = top;
        SinglyLinkedNode<E> node2 = top2;
        while (node1.getNext() != null) {
            node1 = node1.getNext();
            node2.setNext(new SinglyLinkedNode<>(node1.data));
            node2 = node2.getNext();
        }
        LinkedStack<E> copy = new LinkedStack<>();
        copy.top = top2;
        copy.size = size;
        return copy;
    }

    /**
     * A simple implementation of the {@link Object#equals(Object)} method  that
     * relies on the {@link #toArray()} method. If there is a possibility that
     * the number of elements might exceed the specified array limit, then it is
     * advised to override this method and provide a custom logic.
     *
     * @param object The object to check against.
     * @return {@code true} if the given object is a LinkedStack and has the same
     * elements in the order specified by their definition (which may imply no
     * order).
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof LinkedStack)) {
            return super.equals(object);
        }
        LinkedStack stack = (LinkedStack) object;
        if (size != stack.size) {
            return false;
        }
        SinglyLinkedNode node1 = top;
        SinglyLinkedNode node2 = stack.top;
        while (node1 != null && node2 != null) {
            if (!node1.data.equals(node2.data)) {
                return false;
            }
            node1 = node1.getNext();
            node2 = node2.getNext();
        }
        return node1 == null && node2 == null;
    }
}
