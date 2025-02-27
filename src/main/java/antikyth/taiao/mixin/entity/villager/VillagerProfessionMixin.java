// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.mixin.entity.villager;

import antikyth.taiao.event.VillagerProfessionGatherableItemsCallback;
import com.google.common.collect.ImmutableSet;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.village.VillagerProfession;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Arrays;
import java.util.Objects;

@Mixin(VillagerProfession.class)
public class VillagerProfessionMixin {
	/**
	 * Add gatherable items registered in the {@link VillagerProfessionGatherableItemsCallback} for
	 * this profession.
	 */
	@ModifyReturnValue(method = "gatherableItems", at = @At("RETURN"))
	public ImmutableSet<Item> addGatherableItems(ImmutableSet<Item> original) {
		ImmutableSet.Builder<Item> builder = ImmutableSet.<Item>builder().addAll(original);

		VillagerProfessionGatherableItemsCallback.EVENT.invoker()
			.addGatherableItems(
				(VillagerProfession) (Object) this,
				items -> builder.addAll(
					Arrays.stream(items)
						.filter(Objects::nonNull)
						.map(ItemConvertible::asItem)
						.iterator()
				)
			);

		return builder.build();
	}
}
