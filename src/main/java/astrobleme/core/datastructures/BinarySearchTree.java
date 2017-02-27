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

import java.util.Objects;

/**
 * A very simple Binary Search Tree that <b>does NOT</b> balance itself.
 * This tree does not allow null elements.
 *
 * @author Subhomoy Haldar
 * @version 2017.02.25
 */
public class BinarySearchTree<E extends Comparable<E>> extends Container<E> {
    protected BinaryNode<E> root;
    private long size;

    /**
     * Creates a new blank Binary Search Tree.
     */
    public BinarySearchTree() {
        root = null;
        size = 0;
    }

    /**
     * Inserts the data at the appropriate location.
     *
     * @param data The data to be inserted.
     */
    public void insert(E data) {
        if (root == null) {
            root = new BinaryNode<>(Objects.requireNonNull(data));
        } else {
            insert(root, Objects.requireNonNull(data));
        }
        size++;
    }

    /**
     * Starts looking at the children of this Node and looks for the right
     * spot to insert it (if a spot is empty).
     *
     * @param current The non-null parent node.
     * @param data    The data to insert.
     */
    private void insert(BinaryNode<E> current, E data) {
        if (data.compareTo(current.data) < 0) {
            if (!current.hasLeft()) {
                current.setLeft(new BinaryNode<>(data));
            } else {
                insert(current.getLeft(), data);
            }
        } else {
            if (!current.hasRight()) {
                current.setRight(new BinaryNode<>(data));
            } else {
                insert(current.getRight(), data);
            }
        }
    }

    /**
     * Searches the tree to see if the given data is already present
     * in the tree.
     *
     * @param data The item to look for.
     * @return {@code true} if at least one instance of this data
     * is present in the tree.
     */
    public boolean isPresent(final E data) {
        return getParentAndNodeFor(data).second != null;
    }

    /**
     * A simple edge-case abstraction that starts from the root.
     * Helps simplify the rest of the code.
     *
     * @param data The data we're looking for in the node.
     * @return The parent
     */
    private Tuple<BinaryNode<E>> getParentAndNodeFor(final E data) {
        if (root == null) {
            return new Tuple<>(null, null);
        }
        if (root.data.equals(data)) {
            return new Tuple<>(null, root);
        }
        return getParentAndNodeFor(data, root, null);
    }

    /**
     * This method traverses the tree to find both the node
     * which has the same data and its parent.
     *
     * @param data    The data we're looking for in the node.
     * @param current The current subtree's root.
     * @param parent  The parent of the current subtree's root.
     * @return The required node and its parent.
     */
    private Tuple<BinaryNode<E>> getParentAndNodeFor(final E data,
                                                     BinaryNode<E> current,
                                                     BinaryNode<E> parent) {
        // This condition is reached if the data is not present in the tree.
        if (current == null) {
            return new Tuple<>(null, null);
        }
        // We've found it! Return it and its parent
        if (current.data.equals(data)) {
            return new Tuple<>(parent, current);
        }
        // Recursion step: this node becomes the parent and the current becomes
        // the appropriate child.
        parent = current;
        current = data.compareTo(current.data) < 0 ? current.getLeft() : current.getRight();
        return getParentAndNodeFor(data, current, parent);
    }

    /**
     * Removes the given element from the Tree and returns {@code true} if
     * the element was present and removal was successful. If no change
     * occurred, then {@code false} is returned.
     *
     * @param data The data to remove from the Tree.
     * @return If the tree was changed or not.
     */
    public boolean remove(final E data) {
        if (data == null) {
            // Because all elements in this tree are non null.
            return false;
        }
        Tuple<BinaryNode<E>> tuple = getParentAndNodeFor(data);
        BinaryNode<E> parent = tuple.first;
        BinaryNode<E> node = tuple.second;
        if (node == null) {
            // The data is not present in the tree
            return false;
        }
        // If the root is the only node and we need to remove it
        if (size == 1) {    // And node == root is implied
            root = null;
            size = 0;
            return true;
        }
        deleteNode(node, parent);
        size--;
        return true;
    }

    /**
     * Deletes the node from the tree and updates its parent and children
     * as necessary.
     *
     * @param node   The node to delete.
     * @param parent The parent of the node to delete (maybe null).
     */
    private void deleteNode(BinaryNode<E> node, BinaryNode<E> parent) {
        if (node.numberOfChildren() == 2) {
            recursivelyDelete(node, parent);
        } else {
            unlink(node, parent);
        }
    }

    /**
     * The node either has one child or no children. Unlink as if it were
     * part of a linked list.
     *
     * @param node   The node to unlink.
     * @param parent The parent of the node to unlink.
     */
    private void unlink(BinaryNode<E> node, BinaryNode<E> parent) {
        BinaryNode<E> child = node.hasLeft() ? node.getLeft() : node.getRight();
        updateAppropriateChild(node, parent, child);
    }

    /**
     * This node has two children. To delete this node while maintaining the
     * binary tree property takes some care. The following procedure is
     * followed:
     * <p>
     * 1. Choose the in-order predecessor or successor depending on which side
     * has more depth.
     * 2. Keep it's data and recursively call delete on this node. If this
     * node is a leaf, or has one child, recursion stops and the simple
     * unlinking takes place. Otherwise this procedure itself is applied
     * again to this new node.
     * 3. Transplant the data of the predecessor/successor stored previously
     * to the current node. (Take care of edge cases)
     *
     * @param node   The node to delete.
     * @param parent The parent of the node (null in case of the root).
     */
    private void recursivelyDelete(BinaryNode<E> node, BinaryNode<E> parent) {
        E valueToFind = maxDepth(node.getLeft()) > maxDepth(node.getRight())
                ? max(node.getLeft())
                : min(node.getRight());
        Tuple<BinaryNode<E>> tuple = getParentAndNodeFor(valueToFind);
        // Unlink/cascade first, then update this node
        deleteNode(tuple.second, tuple.first);
        // The transplant must be done later to prevent the promoted
        // nodes from "sticking" to the transplant.
        BinaryNode<E> transplant = node.transplant(valueToFind);
        updateAppropriateChild(node, parent, transplant);
    }

    /**
     * Updates the node's parent (if non-null) and replaces this node with
     * the new node.
     *
     * @param node    The node to replace.
     * @param parent  The parent of the node to replace.
     * @param newNode The new node to replace the existing node.
     */
    private void updateAppropriateChild(BinaryNode<E> node, BinaryNode<E> parent, BinaryNode<E> newNode) {
        if (parent == null) {
            // This happens when the node is the root of the tree
            root = newNode;
            return;
        }
        if (node == parent.getLeft()) {
            parent.setLeft(newNode);
        } else {
            parent.setRight(newNode);
        }
    }

    /**
     * Returns the minimum element in the tree.
     *
     * @return The minimum element in the tree.
     */
    public E min() {
        if (root == null) {
            return null;
        }
        return min(root);
    }

    /**
     * Returns the minimum element in the subtree with this node
     * as the root.
     *
     * @param node The root of the subtree to check.
     * @return The minimum element in the subtree with this node
     * as the root.
     */
    private E min(BinaryNode<E> node) {
        while (node.hasLeft()) {
            node = node.getLeft();
        }
        return node.data;
    }


    /**
     * Returns the maximum element in the tree.
     *
     * @return The maximum element in the tree.
     */
    public E max() {
        if (root == null) {
            return null;
        }
        return max(root);
    }

    /**
     * Returns the maximum element in the subtree with this node
     * as the root.
     *
     * @param node The root of the subtree to check.
     * @return The maximum element in the subtree with this node
     * as the root.
     */
    private E max(BinaryNode<E> node) {
        while (node.hasRight()) {
            node = node.getRight();
        }
        return node.data;
    }

    /**
     * Returns a new Queue as per the size of this Tree.
     *
     * @param <T> The type of the Queue needed.
     * @return A new Queue as per the size of this Tree.
     */
    private <T> Queue<T> newQueue() {
        return willProbablyFitArray()
                ? new FixedQueue<>((int) size)
                : new LinkedQueue<>();
    }

    public Queue<E> preOrder() {
        Queue<E> queue = newQueue();
        preOrder(root, queue);
        return queue;
    }

    public Queue<E> postOrder() {
        Queue<E> queue = newQueue();
        postOrder(root, queue);
        return queue;
    }

    public Queue<E> inOrder() {
        Queue<E> queue = newQueue();
        inOrder(root, queue);
        return queue;
    }

    private void preOrder(BinaryNode<E> root, Queue<E> queue) {
        if (root == null) {
            return;
        }
        queue.enqueue(root.data);
        preOrder(root.getLeft(), queue);
        preOrder(root.getRight(), queue);
    }

    private void postOrder(BinaryNode<E> root, Queue<E> queue) {
        if (root == null) {
            return;
        }
        postOrder(root.getLeft(), queue);
        postOrder(root.getRight(), queue);
        queue.enqueue(root.data);
    }

    private void inOrder(BinaryNode<E> root, Queue<E> queue) {
        if (root == null) {
            return;
        }
        inOrder(root.getLeft(), queue);
        queue.enqueue(root.data);
        inOrder(root.getRight(), queue);
    }

    public Queue<E> BFS() {
        Queue<E> visited = newQueue();
        Queue<BinaryNode<E>> unvisited = newQueue();
        BinaryNode<E> node = root;
        while (node != null) {
            visited.enqueue(node.data);
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

    /**
     * Returns the maximum depth of this tree.
     *
     * @return The maximum depth of this tree.
     */
    public int maxDepth() {
        return maxDepth(root);
    }

    /**
     * Returns the maximum depth of the subtree with the given root.
     *
     * @param node The root of the subtree to analyse.
     * @return The maximum depth of the subtree with the given root.
     */
    private int maxDepth(BinaryNode<E> node) {
        if (node == null) {
            return 0;
        }
        return Math.max(maxDepth(node.getLeft()), maxDepth(node.getRight())) + 1;
    }

    /**
     * Returns the number of elements currently in the Tree. It is
     * guaranteed to be a non-negative number.
     * <p>
     * <b>NOTE:</b> If the number of elements exceeds
     * {@link Long#MAX_VALUE Long#MAX_VALUE}, then it will return
     * {@code Long#MAX_VALUE}.
     *
     * @return The number of elements in this Tree.
     */
    @Override
    public long size() {
        return size;
    }

    /**
     * Returns the elements of this Tree in an array in sorted order,
     * if possible.
     * <p>
     * If it cannot fit the data into an array, and assuming no
     * {@link OutOfMemoryError} is thrown, this method will return {@code null}.
     *
     * @return The elements of this Tree in an array, if possible.
     */
    @Override
    public Object[] toArray() {
        return inOrder().toArray();
    }

    /**
     * Returns the elements of the Tree in the given array in sorted
     * order, if it can accommodate, or a new array of the same type.
     * <p>
     * If it cannot fit the data into an array, and assuming no
     * {@link OutOfMemoryError} is thrown, this method will return {@code null}.
     *
     * @param array The array in which to store the elements if possible,
     *              or in a new array of the same type.
     * @return The elements of the tree in the given array, if it
     * can accommodate, or a new array of the same type.
     * @throws ClassCastException If the elements cannot be converted to
     *                            the given type.
     */
    @Override
    public <T extends E> T[] toArray(T[] array) throws ClassCastException {
        return inOrder().toArray(array);
    }

    /**
     * Removes all of its elements.
     */
    @Override
    public void clear() {
        root = null;
        System.gc();
    }

    /**
     * Returns an Exact copy of this Binary tree.
     *
     * @return A new Container with the same elements in the same order
     * (if order is defined) and the same properties as this Container.
     */
    @Override
    public BinarySearchTree<E> copy() {
        BinarySearchTree<E> tree = new BinarySearchTree<E>();
        tree.root = root.copy();
        tree.size = size;
        return tree;
    }
}
