// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity;

public interface ShushableEntity {
	/**
	 * Sets whether the entity should be 'shushed', i.e. not make ambient sounds.
	 */
	void setShushed(boolean shushed);
}
