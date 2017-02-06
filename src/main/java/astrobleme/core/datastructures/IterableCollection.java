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
 *
 */

package astrobleme.core.datastructures;

import java.util.Collection;
import java.util.Iterator;

/**
 * The bare-bones implementation that implements all the secondary methods, simplifying
 * the implementation of the specific primary methods for all data structures.
 *
 * @author Subhomoy Haldar
 * @version 2017.02.05
 */
public abstract class IterableCollection<E> implements Collection<E> {
    /**
     * Returns {@code true} if this {@link Collection} has no elements.
     *
     * @return {@code true} if this {@link Collection} has no elements.
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns {@code true} if this collection contains all the elements in the
     * given collection. It is also assumed that the elements of both the collections
     * are compatible (i.e. can be casted without any problem or loss of data).
     * <p>
     * This will throw an {@link UnsupportedOperationException} if the implementation
     * is immutable or does not support addition of elements.
     *
     * @param collection The generic collection whose elements are to be checked for
     *                   their presence in this collection.
     * @return {@code true} if this collection contains all the elements in the
     * given collection.
     * @throws ClassCastException If the types of one or more elements in the specified
     *                            collection are incompatible with this collection.
     */
    public boolean containsAll(Collection<?> collection) {
        for (Object element : collection) {
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Adds all the elements in the specified collection to this collection.
     *
     * @param collection The collection whose elements to add.
     * @return {@code true} if at least one addition was successful, resulting in
     * modification of the collection.
     */
    public boolean addAll(Collection<? extends E> collection) {
        boolean added = false;
        for (E element : collection) {
            added |= add(element);
        }
        return added;
    }

    /**
     * Adds all the elements in the specified array (or the vararg elements) to
     * this collection.
     *
     * @param elements The array whose elements to add.
     * @return {@code true} if at least one addition was successful, resulting in
     * modification of the collection.
     */
    public boolean addAll(E... elements) {
        boolean added = false;
        for (E element : elements) {
            added |= add(element);
        }
        return added;
    }

    /**
     * Removes all the elements in the specified collection from this collection, so
     * that this has no elements in common with the given collection.
     *
     * @param collection The collection whose elements to remove from this is present.
     * @return {@code true} if at least one removal was successful, resulting in
     * modification of the collection.
     */
    public boolean removeAll(Collection<?> collection) {
        boolean removed = false;
        for (Object element : collection) {
            removed |= remove(element);
        }
        return removed;
    }

    /**
     * Removes all the elements in the specified array from this collection, so
     * that this has no elements in common with the given array.
     *
     * @param elements The array whose elements to remove if present.
     * @return {@code true} if at least one addition was successful, resulting in
     * modification of the collection.
     */
    public boolean removeAll(E... elements) {
        boolean removed = false;
        for (E element : elements) {
            removed |= remove(element);
        }
        return removed;
    }

    /**
     * Retains all the elements of this collection which are contained in the
     * given collection. Removes all other elements.
     *
     * @param collection The collection whose elements to retain if common.
     * @return {@code true} if at least one uncommon element was removed, resulting in
     * modification of the collection.
     */
    public boolean retainAll(Collection<?> collection) {
        boolean modified = false;
        for (Object element : collection) {
            if (!contains(element)) {
                modified |= remove(element);
            }
        }
        return modified;
    }

    /**
     * Returns the elements of this collection in an {@link Object} array of length
     * {@link #size()}. In all the implementations, strictness is applied to ensure
     * that {@link #size()} returns the exact number of elements in the collection
     * and no resizing is needed due to over-allocation or under-allocation.
     *
     * @return The elements of this collection in an {@link Object} array of length
     * {@link #size()}.
     */
    public Object[] toArray() {
        Iterator<E> iterator = iterator();
        int size = size();
        Object[] a = new Object[size];
        // In all implementations, strictness is applied because it doesn't make immediate
        // sense to have more or less elements than the number returned by size()
        for (int i = 0; i < size; i++) {
            a[i] = iterator.next();
        }
        return a;
    }

    /**
     * Returns the elements of this collections as an array of type {@code T}
     * and length {@link #size()}. If the given array is not of the required
     * size, a new array of the same type is returned.
     *
     * @param a   The array to store the elements in, if it is of correct size.
     * @param <T> The type that the elements need to be casted to.
     * @return The elements of this collections as an array of type {@code T}
     * and length {@link #size()}.
     * @throws ClassCastException If the any element of this collection cannot be
     *                            casted to the given type.
     */
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        Iterator<E> iterator = iterator();
        int size = size();
        T[] array = a.length != size
                ? (T[]) java.lang.reflect.Array
                .newInstance(a.getClass().getComponentType(), size)
                : a;
        for (int i = 0; i < size; i++) {
            array[i] = (T) iterator.next();
        }
        return array;
    }
}
