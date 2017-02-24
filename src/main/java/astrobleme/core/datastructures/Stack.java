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

/**
 * Defines the basic structure for all Stacks. A Stack is a data structure
 * that provides LIFO (last in first out) functionality. It achieves this
 * via two methods: {@link #push(Object)} and {@link #pop()}. The former
 * "pushes" an element onto the Stack, while the latter returns the last
 * added or "top-most" element of the Stack.
 * <p>
 * There is an additional method called {@link #peek()} that returns the
 * topmost element of the Stack without "popping" it off.
 *
 * @author Subhomoy Haldar
 * @version 2017.02.14
 */
public abstract class Stack<E> extends Container<E> {
    /**
     * Adds an element onto the Stack. This element (if successfully pushed
     * onto the Stack) will the one that will be returned by a subsequent
     * call to {@link #pop()}.
     *
     * @param element The element to push onto the Stack.
     * @throws OverflowException If the Stack tries to exceed its fixed capacity
     *                           or cannot accommodate any more elements.
     */
    public abstract void push(E element) throws OverflowException;

    /**
     * Returns the element that was most recently added to the Stack and
     * also removes it from the Stack. If the Stack is empty, that is no
     * element was added, it will throw an {@link UnderflowException}.
     *
     * @return The most recently added element.
     * @throws UnderflowException If the Stack is empty.
     */
    public abstract E pop() throws UnderflowException;

    /**
     * Returns the element that was most recently added to the Stack but
     * does not remove it. If the Stack is empty, it will not throw an
     * Exception. Instead, it returns {@code null}.
     *
     * @return The most recently added element.
     */
    public abstract E peek();

    /**
     * Creates an exact copy of the Stack, with the same initial parameters
     * (like capacity) and the same state of elements.
     *
     * @return An exact copy of this Stack.
     */
    @Override
    public abstract Stack<E> copy();

    /**
     * Removes all of its elements.
     */
    @Override
    public void clear() {
        while (!isEmpty()) {
            pop();
        }
    }
}
