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

package astrobleme.core.datastructures.nodes;

/**
 * A simple, re-usable wrapper that contains an element and one pointer to
 * the "next" SinglyLinkedNode.
 *
 * @author Subhomoy Haldar
 * @version 2017.02.15
 */
public class SinglyLinkedNode<E> {
    /**
     * This is the data that this Node wraps. Made public and
     * final for easy and safe usage.
     */
    public final E data;
    private SinglyLinkedNode<E> next;

    /**
     * Creates a new SinglyLinkedNode with the given data and
     * and empty link.
     *
     * @param data The data for this Node.
     */
    public SinglyLinkedNode(final E data) {
        this.data = data;
    }

    /**
     * Returns the Node that this one points to.
     *
     * @return The "next" node in the List.
     */
    public SinglyLinkedNode<E> getNext() {
        return next;
    }

    /**
     * Updates the link for this Node.
     * <p>
     * <b>NOTE:</b> This link is allowed to be null, to help
     * unlink the rest of the list and allow garbage collection.
     *
     * @param next The new Node to point to.
     */
    public void setNext(SinglyLinkedNode<E> next) {
        this.next = next;
    }
}
