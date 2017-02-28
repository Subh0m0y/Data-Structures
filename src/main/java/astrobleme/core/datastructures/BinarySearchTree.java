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
        if (data.compareTo(current.getData()) < 0) {
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
        return firstNodeFor(data) != null;
    }

    /**
     * Returns the first Node in the Tree that has the same data
     * as this given data.
     *
     * @param data The data to search for.
     * @return The Node having the same data.
     */
    private BinaryNode<E> firstNodeFor(E data) {
        return root == null ? null : root.locate(data);
    }

    public boolean remove(E data) {
        if (root == null) {
            // The tree is empty, nothing to remove
            return false;
        }
        if (root.getData().equals(data)) {
            // Workaround for the root. We append it as a child to a
            // Temporary root, perform removal on it and then reset the
            // root as per removal result.
            BinaryNode<E> auxiliaryRoot = new BinaryNode<>(null);
            auxiliaryRoot.setLeft(root);
            boolean result = root.removeRecursive(data, auxiliaryRoot);
            root = auxiliaryRoot.getLeft();
            if (result) size--;
            return result;
        } else {
            boolean result = root.removeRecursive(data, null);
            ;
            if (result) size--;
            return result;
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
        return root.min();
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
        return root.max();
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
        return Traverals.inOrder(this).toArray();
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
        return Traverals.inOrder(this).toArray(array);
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
        BinarySearchTree<E> tree = new BinarySearchTree<>();
        tree.root = root.copy();
        tree.size = size;
        return tree;
    }

    /**
     * If the given Object is a Tree, then checks if the tree has the
     * same structure as this one.
     * <p>
     * If it is not a tree, then it checks if has the same elements
     * in the same order as the inorder traversal would return.
     *
     * @param object The object to check against.
     * @return {@code true} if the given object is a Container and has the same
     * elements in the order specified by their definition (which may imply no
     * order) or if it is an identical tree.
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof BinarySearchTree)) {
            return super.equals(object);
        }
        BinarySearchTree tree = (BinarySearchTree) object;
        return root == tree.root
                || !(root == null || tree.root == null)
                && root.equals(tree.root);
    }
}
