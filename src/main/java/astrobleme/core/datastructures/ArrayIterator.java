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
 * A convenient implementation of an {@link Iterator} that is meant for traversing an
 * array. It is designed as read-only and does not possess the capability to remove or
 * modify the contents of the array. Hence, the array used to instantiate this is not
 * changed in any way.
 *
 * @author Subhomoy Haldar
 * @version 2017.02.05
 */
class ArrayIterator<E> implements Iterator<E> {

    private final Object[] array;
    private final int size;
    private int index;

    /**
     * Creates a new ArrayIterator that traverses the elements of the given array.
     *
     * @param elements The array whose elements to traverse.
     */
    public ArrayIterator(Object... elements) {
        array = elements;
        size = array.length;
        index = 0;
    }

    /**
     * Returns {@code true} if there are more elements to iterate over.
     *
     * @return {@code true} if there are more elements to iterate over.
     */
    public boolean hasNext() {
        return index < size;
    }

    /**
     * Returns the next element in the process of iteration, or an Exception if
     * there are no more elements.
     *
     * @return The next element in the process of iteration, or an Exception if
     * there are no more elements.
     * @throws NoSuchElementException If we are out of elements to return.
     */
    @SuppressWarnings("unchecked")
    public E next() throws NoSuchElementException {
        if (hasNext()) {
            return (E) array[index++];
        } else {
            throw new NoSuchElementException("Out of elements for iteration.");
        }
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
