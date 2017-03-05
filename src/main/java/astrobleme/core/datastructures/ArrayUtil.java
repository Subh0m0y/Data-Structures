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

import java.util.Random;

/**
 * A utility class that performs operation on arrays.
 *
 * @author Subhomoy Haldar
 * @version 2017.02.15
 */
class ArrayUtil {
    /**
     * Reverses the elements of the given array within the specified range.
     *
     * @param a    The array to change.
     * @param from The starting index (inclusive).
     * @param to   The ending index (exclusive).
     * @param <T>  The type of the array.
     */
    static <T> void reverse(T[] a, int from, int to) {
        for (int i = from, j = to - 1; i < j; i++, j--) {
            T temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
    }

    static <T> void swap(T[] array, int index1, int index2) {
        T data = array[index1];
        array[index1] = array[index2];
        array[index2] = data;
    }

    static <T> void shuffle(T[] array, Random random) {
        int length = array.length;
        for (int i = 0; i < length - 1; i++) {
            int j = random.nextInt(length - i - 1) + i + 1;
            swap(array, i, j);
        }
    }
}
