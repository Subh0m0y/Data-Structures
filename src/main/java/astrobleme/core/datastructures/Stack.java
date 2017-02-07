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

/**
 * A Stack is a simple data-structure that offers LIFO (last in first out)
 * functionality and is a central data structure in many important applications.
 * This is the superclass that defines all the properties that an implementation
 * of a Stack should have.
 *
 * @author Subhomoy Haldar
 * @version 2017.02.07
 */
public abstract class Stack<E> extends IterableCollection<E> {

    /**
     * Adds an element to the "top" of the stack. Unless another element is
     * pushed onto the Stack, this element will be popped next.
     *
     * @param value The value to push onto the Stack.
     * @return {@code true} if the element was added successfully.
     * @throws StackOverflowException If the fixed Stack is full or it has
     *                                too many elements.
     */
    public boolean push(E value) throws StackOverflowException {
        return add(value);
    }

    /**
     * Returns the "topmost" element of the Stack, i.e. the one that was
     * added last and removes it from the Stack.
     *
     * @return The "topmost" element of the Stack, i.e. the one that was
     * added last.
     * @throws StackUnderflowException If there are no more elements in
     *                                 the Stack.
     */
    public abstract E pop() throws StackUnderflowException;

    /**
     * Returns the "topmost" element of the Stack, i.e. the one that was
     * added last but does not remove it from the Stack.
     *
     * @return The "topmost" element of the Stack, i.e. the one that was
     * added last, without removing it.
     */
    public abstract E peek();

    /**
     * Removes all the elements from this stack.
     */
    @Override
    public void clear() {
        while (!isEmpty()) {
            try {
                pop();
            } catch (StackUnderflowException ignore) {
            }
        }
    }

    /**
     * Prevent removal of other types of elements. This is to ensure that it works
     * in LIFO mode only. This is also to ensure good performance.
     *
     * @param object The object to remove. But it won't be removed.
     * @return Nothing. It'll throw an Exception.
     * @throws UnsupportedOperationException Because random removal is not allowed.
     */
    @Override
    public boolean remove(Object object) {
        throw new UnsupportedOperationException("Stacks work in LIFO mode only.");
    }
}