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

/**
 * A collection of various types of data structures from the commonplace to
 * the uncommon. Implementations are meant to adhere as closely as possible
 * to the practices and conventions followed by the authors of the core
 * library: the Collections Framework (in java.util). This is to ensure
 * maximum compatibility and integration with the core features of the language.
 * <p>
 * Iterators are a necessity if the data structure is meant to be a usable
 * Collection. Therefore, a set of standard iterator implementations are provided
 * for reuse. For sake of simplicity, iterators are generally not used for removal
 * of elements.
 * <p>
 * Data Structures are made generic for maximum ease of use. If you need to store
 * or process primitive (built-in) data types, use their corresponding wrapper
 * classes. For example, use {@link java.lang.Long} for {@code long},
 * {@link java.lang.Integer} for {@code int}, {@link java.lang.Boolean} for
 * {@code boolean} and so on.
 * <p>
 * It is important to note that the implementations are <strong>not thread-safe</strong>
 * and should not be used for concurrent operations. In such cases, the user is
 * advised to use the default implementations provided in the JDK. There maybe a few
 * thread-safe implementations of a few data structures, but it's better to assume that
 * nothing here is thread-safe and should probably not be used in cases where concurrent
 * handling of collections is necessary.
 *
 * @author Subhomoy Haldar
 * @version 2017.02.05
 */
package astrobleme.core.datastructures;