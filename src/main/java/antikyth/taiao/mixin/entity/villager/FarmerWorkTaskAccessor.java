// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.mixin.entity.villager;

import net.minecraft.entity.ai.brain.task.FarmerWorkTask;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(FarmerWorkTask.class)
public interface FarmerWorkTaskAccessor {
	@Accessor("COMPOSTABLES")
	static List<Item> getCompostableItems() {
		throw new AssertionError();
	}

	@Accessor("COMPOSTABLES")
	@Mutable
	static void setCompostableItems(List<Item> compostables) {
		throw new AssertionError();
	}
}
