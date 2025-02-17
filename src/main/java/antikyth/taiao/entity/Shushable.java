// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity;

/**
 * An {@linkplain net.minecraft.entity.Entity entity} which can be told to 'shush' (i.e., not make ambient sounds).
 * <p>
 * <h2>Implementation</h2>
 * An entity implementing this interface can inhibit ambient sounds like so:
 * <pre>{@code
 * @Override
 * public void playAmbientSound() {
 *     if (!this.isShushed()) super.playAmbientSound();
 * }
 * }</code>
 */
public interface Shushable {
	/**
	 * Sets whether the entity should be 'shushed' (i.e., not allowed to make ambient sounds).
	 */
	void setShushed(boolean shushed);

	/**
	 * Whether the entity is 'shushed' (i.e., isn't allowed to make ambient sounds).
	 */
	boolean isShushed();
}
