// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao;

/**
 * Represents a function that accepts six arguments and produces a result.
 *
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 * @param <V> the type of the third argument to the function
 * @param <W> the type of the fourth argument to the function
 * @param <X> the type of the fifth argument to the function
 * @param <Y> the type of the sixth argument to the function
 * @param <R> the type of the result of the function
 */
@FunctionalInterface
public interface HexaFunction<T, U, V, W, X, Y, R> {
    /**
     * Applies this function to the given arguments.
     */
    R accept(T t, U u, V v, W w, X x, Y y);
}
