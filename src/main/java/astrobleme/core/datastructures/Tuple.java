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
 * A simple immutable class that has two elements.
 *
 * @author Subhomoy Haldar
 * @version 2017.02.26
 */
public class Tuple<E> extends Container<E> implements Iterable<E> {
    /**
     * The first element of the Tuple.
     */
    public final E first;
    /**
     * The second element of the Tuple.
     */
    public final E second;

    /**
     * Creates a tuple from the two elements.
     *
     * @param first  The first element.
     * @param second The second element.
     */
    public Tuple(final E first, final E second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Returns the number of elements in this Tuple, i.e. 2.
     *
     * @return The number of elements in this Tuple i.e. 2.
     */
    @Override
    public long size() {
        return 2;
    }

    /**
     * Returns an array of size 2 with the elements of the tuple.
     *
     * @return An array of size 2 with the elements of the tuple.
     */
    @Override
    public Object[] toArray() {
        return new Object[]{first, second};
    }

    /**
     * Returns the elements of the tuple in the given array if it is of
     * size 2 or an new array of size 2 of the same type. If the types
     * aren't inter-convertible, then a {@link ClassCastException} is
     * thrown.
     *
     * @param array The array in which to store the elements if it's size is 2,
     *              or in a new array of the same type of size 2.
     * @return The elements of the tuple in the given array if it is of
     * size 2 or an new array of size 2 of the same type.
     * @throws ClassCastException If the elements cannot be converted to
     *                            the given type.
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T extends E> T[] toArray(T[] array) throws ClassCastException {
        T[] container = array;
        if (array.length != 2) {
            container = (T[]) java.lang.reflect.Array.newInstance(
                    array.getClass().getComponentType(), 2
            );
        }
        container[0] = (T) first;
        container[1] = (T) second;
        return container;
    }

    /**
     * Throws an Exception.
     */
    @Override
    public void clear() {
        throw new UnsupportedOperationException("Tuples are immutable.");
    }

    /**
     * Returns a new Tuple with the same elements.
     *
     * @return A new Tuple with the same elements.
     */
    @Override
    public Container copy() {
        return new Tuple<>(first, second);
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<E> iterator() {
        return new TupleIterator(this);
    }

    private class TupleIterator implements Iterator<E> {
        private int index;
        private final Tuple<E> tuple;

        TupleIterator(final Tuple<E> tuple) {
            this.tuple = tuple;
            index = 0;
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return index < 2;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Tuple has no more elements.");
            }
            return index++ == 0 ? tuple.first : tuple.second;
        }
    }
}
