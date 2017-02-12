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
 * This defines an ordered collection which assigns a particular index
 * to every element. {@code get(index)}, {@code set(index, element)} and
 * {@code remove(index)} are defined for all lists. The distinguishing feature
 * of Lists is that they are designed to mostly operate on indices.
 *
 * @author Subhomoy Haldar
 * @version 2017.02.06
 */
public abstract class List<E> extends IterableCollection<E> {

    /**
     * Returns the element at the given index.
     *
     * @param index The index of the element sought.
     * @return The element at the given index.
     * @throws IndexOutOfBoundsException If the index is less than 0 or more
     *                                   than or equal to {@link #size()}.
     */
    public abstract E get(final int index) throws IndexOutOfBoundsException;

    /**
     * Replaces the element currently at the given index with the new
     * element and returns the previous element.
     *
     * @param index   The index of the element to be replaced.
     * @param element The new element to replace the old one.
     * @throws IndexOutOfBoundsException If the index is less than 0 or more
     *                                   than or equal to {@link #size()}.
     */
    public abstract E set(final int index, final E element) throws IndexOutOfBoundsException;

    /**
     * Inserts a new element at the required index. And shifts all the other
     * elements up by one, adding 1 to their indices. If an element is added
     * at the index {@link #size()}, then it is added after all the current
     * elements.
     *
     * @param index   The position to insert the new element at.
     * @param element The element to insert.
     * @throws IndexOutOfBoundsException If the index is less than 0 or more
     *                                   than or equal to {@link #size()}.
     */
    public abstract void add(final int index, final E element);

    /**
     * Removes the element currently at the given index and returns it.
     *
     * @param index The position of the element to delete.
     * @throws IndexOutOfBoundsException If the index is less than 0 or more
     *                                   than or equal to {@link #size()}.
     */
    public abstract E remove(final int index);

    /**
     * "Pops" and returns the last element in the list if any. Decreases the size
     * by 1.
     *
     * @return The last element after removing it from the list.
     */
    public E pop() {
        return remove(size() - 1);
    }

    /**
     * Adds the given element to the end of the list. If the List is fixed
     * capacity then it may throw an Exception when the List is full.
     *
     * @param value The value to add to the list.
     * @return {@code true} if the element was successfully added to the List.
     */
    @Override
    public boolean add(E value) {
        add(size(), value);
        return true;
    }

    /**
     * Returns the first index of the value if present or -1 if it is absent.
     *
     * @param value The value to search for.
     * @return The first index of the value if present or -1 if it is absent.
     */
    public abstract int indexOf(Object value);

    /**
     * Returns the last index of the position of presence of the value
     * or -1 if it is absent in the list.
     *
     * @param value The value to search for.
     * @return The last index of the position of presence of the value
     * or -1 if it is absent in the list.
     */
    public abstract int lastIndexOf(Object value);

    /**
     * To be used internally by the subclasses as a quick way to validate indices.
     *
     * @param index The index to check.
     */
    void checkIndex(final int index) throws IndexOutOfBoundsException {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Negative indices not allowed: " + index);
        }
        if (index >= size()) {
            throw new IndexOutOfBoundsException("Invalid index; exceeds the size: " + index);
        }
    }

    /**
     * Compares the given List with this one and makes sure that they have the
     * same elements in the same order. The underlying types maybe different
     * (say of Child class and Parent class) but they should at least respond
     * to the {@link Object#equals(Object)} method.
     *
     * @param other The List to check for equality against.
     * @return {@code true} if the two Lists have the same elements in the
     * same order.
     */
    public boolean equals(Object other) {
        if (!(other instanceof List)) {
            return false;
        }
        List list = (List) other;
        int size = size();
        if (size != list.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            Object elementA = this.get(i);
            Object elementB = list.get(i);
            // Make sure that either both the elements are null
            // or A.equals(B) returns true
            if (elementA == null) {
                if (elementB != null) {
                    return false;
                }
            } else if (!elementA.equals(elementB)) {
                return false;
            }
        }
        return true;
    }
}
