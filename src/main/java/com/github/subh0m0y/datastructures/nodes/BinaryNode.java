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

package com.github.subh0m0y.datastructures.nodes;

import java.util.Comparator;

/**
 * A simple, re-usable Node for use in Binary Trees. It has wraps a
 * {@link Comparable} data and has links to its "left" and "right"
 * children.
 *
 * @author Subhomoy Haldar
 * @version 2017.02.25
 */
public class BinaryNode<E extends Comparable<E>> {
    private E data;
    private BinaryNode<E> parent;
    private BinaryNode<E> left;
    private BinaryNode<E> right;

    /**
     * Creates a new BinaryNode with the given data and no
     * children.
     *
     * @param parent The parent for this Node (maybe null).
     * @param data   The data for this Node.
     */
    public BinaryNode(final BinaryNode<E> parent, final E data) {
        this.parent = parent;
        this.data = data;
    }

    /**
     * Returns the left child of this Node, with data that is "smaller" as
     * defined by the Comparator.
     *
     * @return The left child of this Node.
     */
    public BinaryNode<E> getLeft() {
        return left;
    }

    /**
     * Updates the left child of the Node.
     *
     * @param left The new left child.
     */
    public void setLeft(BinaryNode<E> left) {
        this.left = left;
        if (hasLeft()) {
            left.parent = this;
        }
    }

    /**
     * Returns the right child of this Node, with data that is "larger" as
     * defined by the Comparator.
     *
     * @return The right child of this Node.
     */
    public BinaryNode<E> getRight() {
        return right;
    }

    /**
     * Updates the right child of the Node.
     *
     * @param right The new left child.
     */
    public void setRight(BinaryNode<E> right) {
        this.right = right;
        if (hasRight()) {
            right.parent = this;
        }
    }

    /**
     * Returns the data encapsulated by this Node.
     *
     * @return The data encapsulated by this Node.
     */
    public E getData() {
        return data;
    }

    /**
     * Sets the data of this node to the new given value.
     *
     * @param data The new data to replace the old one.
     */
    public void setData(E data) {
        this.data = data;
    }

    /**
     * Returns {@code true} if the Node has a left subtree.
     * <p>
     * Helps to write cleaner, more readable code.
     *
     * @return {@code true} if the Node has a left subtree.
     */
    public boolean hasLeft() {
        return left != null;
    }

    /**
     * Returns {@code true} if the Node has a right subtree.
     * <p>
     * Helps to write cleaner, more readable code.
     *
     * @return {@code true} if the Node has a right subtree.
     */
    public boolean hasRight() {
        return right != null;
    }

    /**
     * Looks for the data in the subtree represented by this Node.
     * Returns the Node which has the same data as the given data.
     *
     * @param data       The data to search for.
     * @param comparator The The Comparator which defines the ordering of
     *                   the Comparable elements.
     * @return The Node containing the data.
     */
    public BinaryNode<E> locate(E data, Comparator<E> comparator) {
        int comparison = comparator.compare(getData(), data);
        if (comparison == 0) {
            return this;
        } else if (comparison > 0) {
            return hasLeft() ? left.locate(data, comparator) : null;
        } else {
            return hasRight() ? right.locate(data, comparator) : null;
        }
    }

    /**
     * Recursively tries to remove the given data from the subtree
     * represented by this Node. If deletion was successful, it
     * returns {@code true}, otherwise it returns {@code false}.
     *
     * @param data       The data to remove.
     * @param comparator The The Comparator which defines the ordering of
     *                   the Comparable elements.
     * @return If deletion was successful, it
     * returns {@code true}, otherwise it returns {@code false}.
     */
    public boolean removeRecursive(E data, Comparator<E> comparator) {
        int comparisonResult = comparator.compare(getData(), data);
        if (comparisonResult < 0) {
            return hasLeft() && left.removeRecursive(data, comparator);
        } else if (comparisonResult > 0) {
            return hasRight() && right.removeRecursive(data, comparator);
        }
        // The node to be removed is this one...
        if (hasLeft() && hasRight()) {
            // Make a random choice between the inorder successor or the predecessor
            E newData = Math.random() < 0.5 ? right.min() : left.max();
            setData(newData);
            right.removeRecursive(newData, comparator);
            return true;
        }
        BinaryNode<E> child = hasLeft() ? left : right;
        if (parent.left == this) {
            parent.setLeft(child);
        } else {
            parent.setRight(child);
        }
        return true;
    }

    /**
     * Returns the minimum element in the subtree with this node
     * as the root.
     *
     * @return The minimum element in the subtree with this node
     * as the root.
     */
    public E min() {
        BinaryNode<E> node = this;
        while (node.hasLeft()) {
            node = node.getLeft();
        }
        return node.getData();
    }

    /**
     * Returns the maximum element in the subtree with this node
     * as the root.
     *
     * @return The maximum element in the subtree with this node
     * as the root.
     */
    public E max() {
        BinaryNode<E> node = this;
        while (node.hasRight()) {
            node = node.getRight();
        }
        return node.getData();
    }

    /**
     * Creates a new independent subtree from this node.
     *
     * @return A new independent subtree from this node.
     */
    public BinaryNode<E> copy() {
        BinaryNode<E> copy = new BinaryNode<>(parent, data);
        if (hasLeft()) copy.setLeft(getLeft().copy());
        if (hasRight()) copy.setRight(getRight().copy());
        return copy;
    }

    /**
     * Returns a String representation of this Node.
     *
     * @return A String representation of this Node.
     */
    @Override
    public String toString() {
        return data.toString();
    }

    /**
     * Returns {@code true} if the given object is a Node and it
     * is exactly identical to this node, that is has the same
     * data and children.
     *
     * @param object The object to check against.
     * @return {@code true} if the given object is a Node and it
     * is exactly identical to this node.
     */
    public boolean equals(Object object) {
        return object instanceof BinaryNode && equals(this, (BinaryNode) object);
    }

    /**
     * Returns {@code true} if the subtrees represented by the two
     * nodes are equal/identical.
     *
     * @param root1 The root of the first subtree.
     * @param root2 The root of the second subtree.
     * @return {@code true} if the subtrees represented by the two
     * nodes are equal/identical.
     */
    private boolean equals(BinaryNode root1, BinaryNode root2) {
        return root1 == root2
                || !(root1 == null || root2 == null)
                && root1.data.equals(root2.data)
                && equals(root1.getLeft(), root2.getLeft())
                && equals(root1.getRight(), root2.getRight());
    }
}
