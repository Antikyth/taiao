// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.event;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.item.Item;
import net.minecraft.village.VillagerType;

public interface FishermanBoatTradeOfferCallback {
	Event<FishermanBoatTradeOfferCallback> EVENT = EventFactory.createArrayBacked(
		FishermanBoatTradeOfferCallback.class,
		listeners -> builder -> {
			for (FishermanBoatTradeOfferCallback listener : listeners) {
				listener.addTrades(builder);
			}
		}
	);

	void addTrades(ImmutableMap.Builder<VillagerType, Item> builder);
}
