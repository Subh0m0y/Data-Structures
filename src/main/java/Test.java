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


import astrobleme.core.datastructures.FixedStack;
import astrobleme.core.datastructures.Stack;

/**
 * @author Subhomoy Haldar
 * @version 2017.02.05
 */
public class Test {
    public static void main(String[] args) {
        Stack<Integer> stack1 = new FixedStack<>(10);
        Stack<Integer> stack2 = new FixedStack<>(10);
        for (int i = 0; i < 10; i++) {
            stack1.push(i);
            stack2.push(i);
        }
        System.out.println(stack1);
        assert stack1.equals(stack2);
        while (!stack1.isEmpty()) {
            System.out.println(stack1.pop());
        }
        stack2.clear();
        assert stack1.equals(stack2);
    }
}
