// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.mixin;

import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

@Mixin(VillagerEntity.class)
public interface VillagerEntityAccessor {
	@Accessor("GATHERABLE_ITEMS")
	static Set<Item> getGatherableItems() {
		throw new AssertionError();
	}

	@Accessor("GATHERABLE_ITEMS")
	@Mutable
	static void setGatherableItems(Set<Item> gatherableItems) {
		throw new AssertionError();
	}
}
