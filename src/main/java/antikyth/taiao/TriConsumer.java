// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao;

/**
 * Represents an operation that accepts three arguments and returns no result.
 *
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 * @param <V> the type of the third argument to the function
 */
@FunctionalInterface
public interface TriConsumer<T, U, V> {
	/**
	 * Applies this consumer to the given arguments.
	 */
	void accept(T t, U u, V v);
}
