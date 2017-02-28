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

import astrobleme.core.datastructures.nodes.BinaryNode;

/**
 * @author Subhomoy Haldar
 * @version 2017.02.28
 */
public class Traversals {
    /**
     * Returns a new Queue as per the size of the given Tree.
     *
     * @param <E>  The type of the Queue needed.
     * @param tree The Tree from which to generate the Queue.
     * @return A new Queue as per the size of this Tree.
     */
    private static <E> Queue<E> newQueue(BinarySearchTree tree) {
        return tree.willProbablyFitArray()
                ? new FixedQueue<>((int) tree.size())
                : new LinkedQueue<>();
    }

    /**
     * Returns a Queue with the elements in the order that would
     * be obtained by a pre-order traversal of the Tree.
     *
     * @return A Queue with the elements in the order that would
     * be obtained by a pre-order traversal of the Tree.
     */
    public static <E extends Comparable<E>> Queue<E> preOrder(BinarySearchTree<E> tree) {
        Queue<E> queue = newQueue(tree);
        preOrder(tree.root, queue);
        return queue;
    }

    /**
     * Returns a Queue with the elements in the order that would
     * be obtained by a post-order traversal of the Tree.
     *
     * @return A Queue with the elements in the order that would
     * be obtained by a post-order traversal of the Tree.
     */
    public static <E extends Comparable<E>> Queue<E> postOrder(BinarySearchTree<E> tree) {
        Queue<E> queue = newQueue(tree);
        postOrder(tree.root, queue);
        return queue;
    }

    /**
     * Returns a Queue with the elements in the order that would
     * be obtained by an in-order traversal of the Tree.
     *
     * @return A Queue with the elements in the order that would
     * be obtained by an in-order traversal of the Tree.
     */
    public static <E extends Comparable<E>> Queue<E> inOrder(BinarySearchTree<E> tree) {
        Queue<E> queue = newQueue(tree);
        inOrder(tree.root, queue);
        return queue;
    }

    /**
     * Performs a pre-order traversal on the given subtree and yields
     * the elements to the queue.
     *
     * @param root  The root of the current subtree.
     * @param queue The Queue to yield the elements to.
     */
    private static <E extends Comparable<E>> void preOrder(BinaryNode<E> root, Queue<E> queue) {
        if (root == null) {
            return;
        }
        queue.enqueue(root.getData());
        preOrder(root.getLeft(), queue);
        preOrder(root.getRight(), queue);
    }

    /**
     * Performs a post-order traversal on the given subtree and yields
     * the elements to the queue.
     *
     * @param root  The root of the current subtree.
     * @param queue The Queue to yield the elements to.
     */
    private static <E extends Comparable<E>> void postOrder(BinaryNode<E> root, Queue<E> queue) {
        if (root == null) {
            return;
        }
        postOrder(root.getLeft(), queue);
        postOrder(root.getRight(), queue);
        queue.enqueue(root.getData());
    }

    /**
     * Performs a in-order traversal on the given subtree and yields
     * the elements to the queue.
     *
     * @param root  The root of the current subtree.
     * @param queue The Queue to yield the elements to.
     */
    private static <E extends Comparable<E>> void inOrder(BinaryNode<E> root, Queue<E> queue) {
        if (root == null) {
            return;
        }
        inOrder(root.getLeft(), queue);
        queue.enqueue(root.getData());
        inOrder(root.getRight(), queue);
    }

    /**
     * Performs a breadth-first search on the tree and yields the
     * elements to a Queue and returns it.
     *
     * @return The queue to store elements yielded by the BFS.
     */
    public static <E extends Comparable<E>> Queue<E> bfs(BinarySearchTree<E> tree) {
        Queue<E> visited = newQueue(tree);
        Queue<BinaryNode<E>> unvisited = newQueue(tree);
        BinaryNode<E> node = tree.root;
        while (node != null) {
            visited.enqueue(node.getData());
            if (node.hasLeft()) {
                unvisited.enqueue(node.getLeft());
            }
            if (node.hasRight()) {
                unvisited.enqueue(node.getRight());
            }
            node = unvisited.isEmpty() ? null : unvisited.dequeue();
        }
        return visited;
    }
}
