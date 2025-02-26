// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.mixin;

import antikyth.taiao.advancement.criteria.TaiaoCriteria;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryChangedCriterion.class)
public class InventoryChangedCriterionMixin {
	/**
	 * Triggers {@link TaiaoCriteria#KETE_CHANGED} when {@link Criteria#INVENTORY_CHANGED} is triggered.
	 */
	@Inject(
		method = "trigger(Lnet/minecraft/server/network/ServerPlayerEntity;Lnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/item/ItemStack;III)V",
		at = @At("HEAD")
	)
	private void addKeteChangedCriterionTrigger(
		ServerPlayerEntity player,
		PlayerInventory inventory,
		ItemStack stack,
		int full,
		int empty,
		int occupied,
		CallbackInfo ci
	) {
		TaiaoCriteria.KETE_CHANGED.trigger(player, stack);
	}
}
