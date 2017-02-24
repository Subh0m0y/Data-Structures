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

/**
 * @author Subhomoy Haldar
 * @version 2017.02.05
 */
public class Test {
    public static void main(String[] args) {
        int size = 10;
        Integer[] storage = new Integer[size];
        Queue<Integer> queue = new FixedQueue<>(size);
        for (int i = 0; i < size; i++) {
            queue.enqueue(i);
            storage[i] = i;
        }
        assert queue.equals(queue.copy());
        assert queue.peek() == size - 1;

        assert Arrays.equals(storage, queue.toArray(new Integer[size]));

        for (int i = 0; i < size; i++) {
            assert i == queue.dequeue();
        }

//        Queue<Integer> queue = new FixedQueue<>(10);
//        System.out.println(Arrays.toString(queue.toArray()));
//        for (int i = 0; i < 10; i++) {
//            queue.enqueue(i);
//        }
//        System.out.println(queue);
//        System.out.println(queue.copy());
//        while (!queue.isEmpty()) {
//            System.out.println(queue.dequeue());
//            System.out.println(queue);
//        }
    }
}
