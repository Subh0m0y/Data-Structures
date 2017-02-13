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
import java.util.NoSuchElementException;

/**
 * @author Subhomoy Haldar
 * @version 2017.02.12
 */
public class LinkedStack<E> extends Stack<E> {
    private class Node {
        Node next;
        final E data;

        Node(final E element) {
            data = element;
        }
    }

    private Node top;
    private int size;

    @Override
    public boolean push(E value) throws OverflowException {
        if (top == null) {
            top = new Node(value);
            size++;
            return true;
        }
        Node node = new Node(value);
        node.next = top;
        top = node;
        size++;
        return true;
    }

    @Override
    public E pop() throws UnderflowException {
        if (top == null) {
            throw new UnderflowException();
        }
        E value = top.data;
        top = top.next;
        size--;
        return value;
    }

    @Override
    public E peek() {
        return top != null ? top.data : null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(Object object) {
        for (E element : this) {
            if (element == null) {
                if (object == null) {
                    return true;
                }
            } else if (element.equals(object)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new NodeIterator();
    }

    /**
     * An iterator over the Nodes in this Stack.
     */
    class NodeIterator implements Iterator<E> {
        private Node node;  // The current node

        /**
         * Creates a new NodeIterator in the LIFO order.
         */
        NodeIterator() {
            node = top;
        }

        /**
         * Returns {@code true} if there are more elements to iterate over.
         *
         * @return {@code true} if there are more elements to iterate over.
         */
        @Override
        public boolean hasNext() {
            return node != null;
        }

        /**
         * Returns the next element in the process of iteration, or an Exception if
         * there are no more elements.
         *
         * @return The next element in the process of iteration, or an Exception if
         * there are no more elements.
         * @throws NoSuchElementException If we are out of elements to return.
         */
        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E value = node.data;
            node = node.next;
            return value;
        }

        /**
         * This method throws an Exception. Removal is discouraged via iterators.
         *
         * @throws UnsupportedOperationException Because removal is not allowed.
         */
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("This iterator is read-only. It cannot delete elements.");
        }
    }
}
