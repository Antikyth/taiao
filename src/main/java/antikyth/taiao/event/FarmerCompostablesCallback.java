// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.item.ItemConvertible;

public interface FarmerCompostablesCallback {
	Event<FarmerCompostablesCallback> EVENT = EventFactory.createArrayBacked(
		FarmerCompostablesCallback.class,
		listeners -> builder -> {
			for (FarmerCompostablesCallback listener : listeners) {
				listener.addCompostableItems(builder);
			}
		}
	);

	void addCompostableItems(ItemSetBuilder builder);

	@FunctionalInterface
	interface ItemSetBuilder {
		/**
		 * Adds the given {@code items} to the set of items.
		 */
		void add(ItemConvertible... items);
	}
}
