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


import astrobleme.core.datastructures.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * @author Subhomoy Haldar
 * @version 2017.02.05
 */
public class Test {
    private static final int SIZE = 1_000_000;
    private static Integer[] array = new Integer[SIZE];

    public static void main(String[] args) {
        for (int i = 0; i < SIZE; i++) {
            array[i] = i;
        }
        ArrayUtil.shuffle(args, new Random());
        long time = System.nanoTime();
        pqSort();               // around 2.2 s
        //Arrays.sort(array);   // around 0.02 s (yes I know, stop laughing)
        time = System.nanoTime() - time;
        assert ArrayUtil.isSorted(array, Comparator.naturalOrder());
        System.out.println(time * 1e-9);
    }

    private static void pqSort() {
        PriorityQueue<Integer> queue = new PriorityQueue<>(SIZE);
        for (Integer element : array) {
            queue.enqueue(element);
        }
        array = queue.toArray(array);
    }
}
