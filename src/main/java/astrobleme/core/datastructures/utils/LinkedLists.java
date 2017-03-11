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

package astrobleme.core.datastructures.utils;

import astrobleme.core.datastructures.nodes.SinglyLinkedNode;

/**
 * A class that performs operations on Linked List based data structures.
 * Refactoring effort to reduce code duplication.
 *
 * @author Subhomoy Haldar
 * @version 2017.02.28
 */
public class LinkedLists {
    /**
     * Starting with the given node, it converts the Linked List to
     * an Object array of the expected size.
     *
     * @param node The Node to start with.
     * @param size The size to traverse. It won't go any further.
     * @param <E>  The type of data the Node stores.
     * @return An array of the given size, with the Linked List's elements.
     */
    public static <E> Object[] toArray(SinglyLinkedNode<E> node, int size) {
        Object[] array = new Object[size];
        int index = 0;
        SinglyLinkedNode<E> current = node;
        while (current != null && index < size) {
            array[index++] = current.data;
            current = current.getNext();
        }
        return array;
    }

    /**
     * Starting with the given node, it converts the Linked List to
     * an typed array of type T[], of the expected size.
     *
     * @param node  The Node to start with.
     * @param size  The size to traverse. It won't go any further.
     * @param array The array to store the data in, if it's big enough.
     * @param <E>   The type of data the Node stores.
     * @return An array of the given size, with the Linked List's elements.
     */
    @SuppressWarnings("unchecked")
    public static <T, E> T[] toArray(T[] array, SinglyLinkedNode<E> node, int size) {
        T[] container;
        container = array.length > size ? array : (T[]) java.lang.reflect.Array.newInstance(
                array.getClass().getComponentType(), size
        );
        int index = 0;
        SinglyLinkedNode<E> current = node;
        while (current != null && index < size) {
            container[index++] = (T) current.data;
            current = current.getNext();
        }
        return container;
    }

    /**
     * Copies the Linked List from the original to the copy.
     *
     * @param original The start of the list to copy.
     * @param copy     The start of the list to copy into.
     * @param <E>      The type of data stored by the node.
     */
    public static <E> void copy(SinglyLinkedNode<E> original, SinglyLinkedNode<E> copy) {
        while (original.getNext() != null) {
            // Progress to the next node of this stack and get ready
            // to link it to the node of the new stack.
            original = original.getNext();
            copy.setNext(new SinglyLinkedNode<>(original.data));
            copy = copy.getNext();
        }
    }

    /**
     * @param node1 The first linked list's node.
     * @param node2 The second linked list's node.
     * @return {@code true} if the lists are identical.
     */
    public static boolean areEqual(SinglyLinkedNode node1, SinglyLinkedNode node2) {
        // Iterate until one of them gets exhausted
        while (node1 != null && node2 != null) {
            // Safe guard against null data
            if (node1.data == null) {
                if (node2.data != null) {
                    return false;
                } else {
                    continue;
                }
            }
            // For normal non-null data
            if (!node1.data.equals(node2.data)) {
                return false;
            }
            node1 = node1.getNext();
            node2 = node2.getNext();
        }
        // Ensure that both of the lists got exhausted
        return node1 == null && node2 == null;
    }
}
