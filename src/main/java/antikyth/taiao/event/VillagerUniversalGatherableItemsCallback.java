// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.item.ItemConvertible;

public interface VillagerUniversalGatherableItemsCallback {
	Event<VillagerUniversalGatherableItemsCallback> EVENT = EventFactory.createArrayBacked(
		VillagerUniversalGatherableItemsCallback.class,
		listeners -> builder -> {
			for (VillagerUniversalGatherableItemsCallback listener : listeners) {
				listener.addGatherableItems(builder);
			}
		}
	);

	void addGatherableItems(ItemSetBuilder builder);

	@FunctionalInterface
	interface ItemSetBuilder {
		/**
		 * Adds the given {@code items} to the set of items.
		 */
		void add(ItemConvertible... items);
	}
}
