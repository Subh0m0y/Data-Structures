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

package astrobleme.core.datastructures.nodes;

/**
 * A simple, re-usable Node for use in Binary Trees. It has wraps a
 * {@link Comparable} data and has links to its "left" and "right"
 * children.
 *
 * @author Subhomoy Haldar
 * @version 2017.02.25
 */
public class BinaryNode<E extends Comparable<E>> {
    public final E data;
    private BinaryNode<E> left;
    private BinaryNode<E> right;

    /**
     * Creates a new BinaryNode with the given data and no
     * children.
     *
     * @param data The data for this Node.
     */
    public BinaryNode(final E data) {
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

    public int numberOfChildren() {
        int count = 0;
        if (hasLeft()) count++;
        if (hasRight()) count++;
        return count;
    }

    public BinaryNode<E> transplant(final E data) {
        BinaryNode<E> node = new BinaryNode<>(data);
        node.setLeft(this.getLeft());
        node.setRight(this.getRight());
        return node;
    }

    public BinaryNode<E> copy() {
        BinaryNode<E> copy = new BinaryNode<>(data);
        if (hasLeft()) copy.setLeft(getLeft().copy());
        if (hasRight()) copy.setRight(getRight().copy());
        return copy;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
