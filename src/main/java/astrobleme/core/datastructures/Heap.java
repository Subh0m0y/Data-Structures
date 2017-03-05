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

import com.sun.istack.internal.NotNull;

import java.util.Comparator;
import java.util.Objects;

import static astrobleme.core.datastructures.ArrayUtil.swap;

/**
 * Represents a simple, MinHeap that is capable of working with
 * generic Comparable elements and also with a custom Comparator.
 * It is meant to be a backing data-structure, for use in other,
 * bigger ones, like PriorityQueue.
 * <p>
 * This heap is expandable. It will resize when it is out of space.
 *
 * @author Subhomoy Haldar
 * @version 2017.03.02
 */
class Heap<E extends Comparable<E>> {
    private Object[] data;
    private int size;
    private final Comparator<E> comparator;

    Heap(final int initialCapacity, final Comparator<E> comparator) {
        data = new Object[initialCapacity];
        size = 0;
        this.comparator = comparator;
    }

    private int parent(final int index) {
        return (index - 1) / 2;
    }

    private int left(final int index) {
        return 1 + (index << 1);
    }

    private int right(final int index) {
        return 2 + (index << 1);
    }

    @SuppressWarnings("unchecked")
    E first() {
        return (E) data[0];
    }

    void insert(@NotNull final E value) {
        if (size == data.length) {
            resize();
        }
        data[size++] = Objects.requireNonNull(value);
        heapify();
    }

    @SuppressWarnings("unchecked")
    private void heapify() {
        int i = size - 1;
        int nextI = parent(i);
        E child = (E) data[i];
        E parent = (E) data[nextI];
        while (i > 0 && comparator.compare(child, parent) < 0) {
            swap(data, i, nextI);
            i = nextI;
            nextI = parent(i);
            child = (E) data[i];
            parent = (E) data[nextI];
        }
    }

    private void resize() {
        int newSize = (int) (data.length * 1.5);
        Object[] newArray = new Object[newSize];
        System.arraycopy(data, 0, newArray, 0, size);
        data = newArray;
    }

    @SuppressWarnings("unchecked")
    private int indexOf(final E item) {
        // index is the location of the current element being examined
        int index = 0;
        // This stores the number of nodes in each level
        int nodes = 1;
        while (index < size) {
            // Every iteration of this loop
            index = nodes - 1;
            int end = nodes + index;
            int count = 0;  // Keeps track of the number of elements counted in this level
            while (index < size && index < end) {
                E current = (E) data[index];
                E parent = (E) data[parent(index)];
                if (current.equals(item)) {
                    return index;
                } else if (comparator.compare(item, parent) > 0
                        && comparator.compare(item, current) < 0) {
                    // The item should be in this level, and it can't be
                    // in any other level. Keep track of the number of
                    // elements accessed in this level
                    count++;
                }
                index++;
            }
            if (count == nodes) {
                // The element was supposed to be in this level,
                // but we couldn't find it. Therefore, it can't
                // be in any other level
                return -1;
            }
            // The number of nodes doubles after each level
            nodes <<= 1;
        }
        // Ran out of elements before we found the element
        return -1;
    }

    boolean remove(@NotNull final E item) {
        int index = indexOf(Objects.requireNonNull(item));
        return index >= 0 && removeIndex(index);
    }

    @SuppressWarnings("unchecked")
    boolean removeIndex(final int indexToRemove) {
        int index = indexToRemove;
        size--;
        if (size == 0) {
            // No need to promote
            return true;
        }
        data[index] = data[size];
        int left = left(index);
        int right = right(index);
        E current = (E) data[index];
        while ((left < size && comparator.compare(current, (E) data[left]) > 0) ||
                (right < size && comparator.compare(current, (E) data[right]) > 0)) {
            // Promote the smaller of the two children
            int nextIndex = comparator.compare((E) data[left], (E) data[right]) < 0
                    ? left
                    : right;
            swap(data, index, nextIndex);
            index = nextIndex;
            // Update all helpers
            left = left(index);
            // Prevent ArrayIndexOutOfBoundsException
            if (left >= size) {
                return true;
            }
            right = right(index);
            current = (E) data[index];
        }
        return true;
    }

    int size() {
        return size;
    }

    Heap<E> copy() {
        Object[] array = new Object[data.length];
        System.arraycopy(data, 0, array, 0, size);
        Heap<E> heap = new Heap<>(data.length, comparator);
        heap.data = array;
        heap.size = size;
        return heap;
    }
}
