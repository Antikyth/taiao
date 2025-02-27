// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.mixin.entity.villager;

import antikyth.taiao.event.FishermanBoatTradeOfferCallback;
import com.google.common.collect.ImmutableMap;
import com.llamalad7.mixinextras.injector.ModifyReceiver;
import net.minecraft.item.Item;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;
import net.minecraft.village.VillagerType;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(TradeOffers.class)
public class TradeOffersMixin {
	/**
	 * Inserts the {@link FishermanBoatTradeOfferCallback} event to allow custom {@link VillagerType}-aware
	 * {@linkplain VillagerProfession#FISHERMAN fisherman} boat trades.
	 */
	@ModifyReceiver(
		method = "method_16929",
		slice = @Slice(
			from = @At(
				value = "FIELD",
				target = "Lnet/minecraft/village/VillagerProfession;FISHERMAN:Lnet/minecraft/village/VillagerProfession;"
			),
			to = @At(
				value = "FIELD",
				target = "Lnet/minecraft/village/VillagerProfession;SHEPHERD:Lnet/minecraft/village/VillagerProfession;"
			)
		),
		at = @At(
			value = "INVOKE",
			target = "com/google/common/collect/ImmutableMap$Builder.build ()Lcom/google/common/collect/ImmutableMap;"
		),
		remap = false
	)
	private static @NotNull ImmutableMap.Builder<VillagerType, Item> addFishermanTypeAwareTrades(
		ImmutableMap.@NotNull Builder<VillagerType, Item> builder
	) {
		FishermanBoatTradeOfferCallback.EVENT.invoker().addTrades(builder);

		return builder;
	}
}
