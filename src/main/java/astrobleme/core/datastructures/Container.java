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
import java.util.StringJoiner;

/**
 * This is the root of the Container hierarchy. This contains methods
 * that provide a common skeletal structure to all implementations. The
 * modifying methods will have different names, depending on the type of
 * data structure it is.
 *
 * @author Subhomoy Haldar
 * @version 2017.02.15
 */
public abstract class Container<E> {
    /**
     * Returns the number of elements currently in the Container. It is
     * guaranteed to be a non-negative number.
     * <p>
     * <b>NOTE:</b> If the number of elements exceeds
     * {@link Long#MAX_VALUE Integer#MAX_VALUE}, then it will return
     * {@code Long#MAX_VALUE}.
     *
     * @return The number of elements in this Container.
     */
    abstract long size();

    /**
     * Returns {@code true} if the number of elements in this Container is
     * within the allowed maximum size for arrays, and hopefully it might
     * be able to create an array out of it.
     * <p>
     * However, it depends on the amount of memory allocated by the VM and
     * even smaller sizes may cause a {@link OutOfMemoryError}. It is advised
     * to re-start the vm with different arguments to allow for allocation
     * of more memory if needed. It is encouraged to compactify the element type
     * instead, to reduce overhead.
     *
     * @return {@code false} if it is absolutely impossible to represent it
     * as an array.
     */
    boolean willProbablyFitArray() {
        // The value allowed by most VMs:
        final long MAX = Integer.MAX_VALUE - 8;
        return size() <= MAX;
    }

    /**
     * Returns the elements of this Container in an array, if possible.
     * <p>
     * If it cannot fit the data into an array, and assuming no
     * {@link OutOfMemoryError} is thrown, this method will return {@code null}.
     *
     * @return The elements of this Container in an array, if possible.
     */
    public abstract Object[] toArray();

    /**
     * Returns the elements of the Container in the given array, if it
     * can accommodate, or a new array of the same type.
     * <p>
     * If it cannot fit the data into an array, and assuming no
     * {@link OutOfMemoryError} is thrown, this method will return {@code null}.
     *
     * @param array The array in which to store the elements if possible,
     *              or in a new array of the same type.
     * @param <T>   The type of the array needed.
     * @return The elements of the Container in the given array, if it
     * can accommodate, or a new array of the same type.
     * @throws ClassCastException If the elements cannot be converted to
     *                            the given type.
     */
    public abstract <T extends E> T[] toArray(T[] array) throws ClassCastException;

    /**
     * Returns {@code true} if this Container has no elements.
     *
     * @return {@code true} if this Container has no elements.
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Removes all of its elements.
     */
    public abstract void clear();

    /**
     * Returns the String representation of this Container. If it has elements
     * within the specified limit, then it tries to return a String with a
     * description of all the elements inside it. If it has too many elements,
     * then it just returns the class name followed by the number of elements.
     *
     * @return The String representation of this Container.
     */
    @Override
    public String toString() {
        // The String representation will be stored in a char array ultimately.
        // So, it'd be better if was representable as an array.
        // Otherwise, don't bother. Just return the class name and size.
        if (!willProbablyFitArray()) {
            return this.getClass().getName() + " : " + size();
        }
        Object[] array = toArray();
        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        for (Object element : array) {
            joiner.add(element == this ? "(this container)" : element.toString());
        }
        return joiner.toString();
    }

    /**
     * A simple implementation of the {@link Object#equals(Object)} method  that
     * relies on the {@link #toArray()} method. If there is a possibility that
     * the number of elements might exceed the specified array limit, then it is
     * advised to override this method and provide a custom logic.
     *
     * @param object The object to check against.
     * @return {@code true} if the given object is a Container and has the same
     * elements in the order specified by their definition (which may imply no
     * order).
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Container)) {
            return false;
        }
        Container container = (Container) object;
        return size() == container.size()
                && Arrays.equals(toArray(), container.toArray());
    }
}
