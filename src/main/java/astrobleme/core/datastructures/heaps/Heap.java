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

package astrobleme.core.datastructures.heaps;

import com.sun.istack.internal.NotNull;

import java.util.Comparator;
import java.util.Objects;

/**
 * A general superclass for all Heaps. This class all the methods that
 * must be present in any Heap.
 *
 * @author Subhomoy Haldar
 * @version 2017.03.05
 */
public abstract class Heap<E extends Comparable<E>> {
    /**
     * This comparator determines the order of the priorities.
     */
    protected final Comparator<E> comparator;

    /**
     * @param comparator The comparator needed for every comparison.
     */
    Heap(final Comparator<E> comparator) {
        this.comparator = comparator;
    }

    /**
     * The index of the location of the parent of the given index.
     *
     * @param index The index whose parent to find.
     * @return The parent index of the given index.
     */
    protected int parent(final int index) {
        return (index - 1) / 2; // NOTE: (index - 1) >>> 1 doesn't work
    }

    /**
     * The index of the location of the left child of the given index.
     *
     * @param index The index whose left child to find.
     * @return The index of the left child of the given index.
     */
    protected int left(final int index) {
        return 1 + (index << 1);
    }

    /**
     * The index of the location of the right child of the given index.
     *
     * @param index The index whose right child to find.
     * @return The index of the right child of the given index.
     */
    protected int right(final int index) {
        return 2 + (index << 1);
    }

    /**
     * Returns the first element in the heap, i.e. the element with
     * the highest priority.
     *
     * @return The first element in the heap.
     */
    public abstract E first();

    /**
     * Inserts the given item into the heap and makes sure that
     * the heap property is maintained by the end of it.
     *
     * @param item The item to add to the heap.
     */
    public abstract void insert(final E item);

    /**
     * Checks if the given item is present in the heap.
     *
     * @param item The non-null item to search for.
     * @return {@code true} if the item is present.
     */
    public boolean contains(@NotNull final E item) {
        return indexOf(Objects.requireNonNull(item)) > -1;
    }

    /**
     * Returns the index of the item in the heap.
     *
     * @param item The non-null item to search for.
     * @return The index of the item if present, or -1.
     */
    protected abstract int indexOf(final E item);

    /**
     * Removes the element from the heap and maintains the heap
     * property.
     *
     * @param item The non-null item to remove from the heap.
     * @return {@code true} if the item was successfully removed.
     */
    public abstract boolean remove(final E item);

    /**
     * Returns the number of elements currently in the heap.
     *
     * @return The size of the heap.
     */
    public abstract int size();

    /**
     * Creates an identical, independent copy of the heap.
     *
     * @return An identical, independent copy of the heap.
     */
    public abstract Heap<E> copy();
}
