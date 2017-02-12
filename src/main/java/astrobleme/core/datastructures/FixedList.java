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

import java.util.Arrays;
import java.util.Iterator;

/**
 * A fixed-capacity implementation of List backed by an Array.
 * Intended to be light and fast.
 *
 * @author Subhomoy Haldar
 * @version 2017.02.11
 */
public class FixedList<E> extends List<E> {

    private final Object[] a;
    private int size;

    /**
     * Creates a List with the given capacity.
     *
     * @param capacity The maxium capacity of the List.
     */
    public FixedList(final int capacity) {
        a = new Object[capacity];
        size = 0;
    }

    /**
     * {@inheritDoc}
     *
     * @param index The index of the element sought.
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) throws IndexOutOfBoundsException {
        checkIndex(index);
        return (E) a[index];
    }

    /**
     * {@inheritDoc}
     *
     * @param index   The index of the element to be replaced.
     * @param element The new element to replace the old one.
     * @return
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public E set(int index, E element) throws IndexOutOfBoundsException {
        checkIndex(index);
        E value = (E) a[index];
        a[index] = element;
        return value;
    }

    /**
     * {@inheritDoc}
     *
     * @param index   The position to insert the new element at.
     * @param element The element to insert.
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public void add(int index, E element) throws IndexOutOfBoundsException {
        checkIndex(index);
        if (size == a.length) {
            // This list is full
            throw new OverflowException(a.length);
        }
        // Shuffle elements up
        int count = size - index;
        if (count > 0) {
            System.arraycopy(a, index, a, index + 1, count);
        }
        a[index] = element;
    }

    /**
     * {@inheritDoc}
     *
     * @param index The position of the element to delete.
     * @return {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public E remove(int index) {
        checkIndex(index);
        // shuffle elements down
        int count = size - index - 1;
        E element = (E) a[index];
        System.arraycopy(a, index + 1, a, index, count);
        return element;
    }

    /**
     * Returns the number of elements currently in the List.
     *
     * @return The number of elements currently in the List.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns {@code true} if the given object is present in the list.
     *
     * @param object The object to look for.
     * @return {@code true} if the given object is present in the list.
     */
    @Override
    public boolean contains(Object object) {
        return indexOf(object) > -1;
    }

    /**
     * {@inheritDoc}
     *
     * @param value The value to search for.
     * @return {@inheritDoc}
     */
    @Override
    public int indexOf(Object value) {
        for (int i = 0; i < size; i++) {
            if (value == null) {
                if (a[i] == null) {
                    return i;
                }
            } else {
                if (a[i].equals(value)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * {@inheritDoc}
     *
     * @param value The value to search for.
     * @return {@inheritDoc}
     */
    @Override
    public int lastIndexOf(Object value) {
        for (int i = size - 1; i >= 0; i--) {
            if (value == null) {
                if (a[i] == null) {
                    return i;
                }
            } else {
                if (a[i].equals(value)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Returns an iterator over the elements in the List, in the order they
     * are present.
     *
     * @return An iterator over the elements in the List, in the order they
     * are present.
     */
    @Override
    public Iterator<E> iterator() {
        return new ArrayIterator<>(Arrays.copyOf(a, size));
    }

    /**
     * This method removes the first occurrence of the specified element in
     * this List. Returns {@code true} if the element was successfully removed
     * or {@code false} if the element is not present.
     *
     * @param object The object to remove.
     * @return {@code true} if the element was successfully removed or
     * {@code false} if the element is not present.
     */
    @Override
    public boolean remove(Object object) {
        int position = indexOf(object);
        if (position == -1) {
            return false;
        }
        remove(position);
        return true;
    }

    /**
     * Empties the list and resets it.
     */
    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            a[i] = null;
        }
        size = 0;
    }
}
