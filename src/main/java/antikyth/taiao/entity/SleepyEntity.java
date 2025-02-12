// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity;

public interface SleepyEntity {
	/**
	 * Sets whether the entity is sleeping.
	 */
	void setSleeping(boolean sleeping);

	/**
	 * Whether the entity is sleeping.
	 */
	boolean isSleeping();

	/**
	 * Stop sleeping.
	 * <p>
	 * Equivalent to {@link SleepyEntity#setSleeping(boolean) setSleeping}{@code (false)}.
	 */
	default void wake() {
		this.setSleeping(false);
	}

	/**
	 * Start sleeping.
	 * <p>
	 * Equivalent to {@link SleepyEntity#setSleeping(boolean) setSleeping}{@code (true)}.
	 */
	default void startSleeping() {
		this.setSleeping(true);
	}
}
