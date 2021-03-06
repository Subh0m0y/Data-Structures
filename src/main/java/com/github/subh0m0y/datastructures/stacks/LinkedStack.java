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

package com.github.subh0m0y.datastructures.stacks;

import com.github.subh0m0y.datastructures.utils.LinkedLists;
import com.github.subh0m0y.datastructures.exceptions.OverflowException;
import com.github.subh0m0y.datastructures.exceptions.UnderflowException;
import com.github.subh0m0y.datastructures.nodes.SinglyLinkedNode;

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
     * Returns the number of elements currently in the Stack. It is
     * guaranteed to be a non-negative number.
     * <p>
     * <b>NOTE:</b> If the number of elements exceeds
     * {@link Long#MAX_VALUE Integer#MAX_VALUE}, then it will return
     * {@code Long#MAX_VALUE}.
     *
     * @return The number of elements in this Stack.
     */
    @Override
    public long size() {
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
        } else {
            node.setNext(top);
            top = node;
        }
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
        return LinkedLists.toArray(top, (int) size);
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
    public <T extends E> T[] toArray(T[] array) throws ClassCastException {
        if (!willProbablyFitArray()) {
            return null;
        }
        return LinkedLists.toArray(array, top, (int) size);
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
        LinkedLists.copy(top, top2);
        LinkedStack<E> copy = new LinkedStack<>();
        copy.top = top2;
        copy.size = size;
        return copy;
    }

    /**
     * Override of the standard implementation, allowing for checking potentially
     * large LinkedStacks reliably.
     *
     * @param object The object to check against.
     * @return {@code true} if the given object is a LinkedStack and has the same
     * elements in the required order.
     */
    @Override
    public boolean equals(Object object) {
        // See if the given object is a different type of Container.
        // If it is a LinkedStack then bingo! Otherwise fallback to
        // the default implementation of equals().
        if (!(object instanceof LinkedStack)) {
            return super.equals(object);
        }
        LinkedStack stack = (LinkedStack) object;
        return size == stack.size && LinkedLists.areEqual(top, stack.top);
    }
}
