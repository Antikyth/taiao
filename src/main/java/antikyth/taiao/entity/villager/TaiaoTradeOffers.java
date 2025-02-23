// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.entity.villager;

import antikyth.taiao.Taiao;
import antikyth.taiao.banner.TaiaoBanners;
import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.event.FishermanBoatTradeOfferCallback;
import antikyth.taiao.item.TaiaoItems;
import com.google.common.collect.ImmutableMap;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class TaiaoTradeOffers {
	public static void initialize() {
		Taiao.LOGGER.debug("Registering trade offers");

		// Fisherman
		FishermanBoatTradeOfferCallback.EVENT.register(builder -> builder.put(
			TaiaoVillagerTypes.MAAORI,
			TaiaoItems.KAURI_BOAT
		));
		TradeOfferHelper.registerVillagerOffers(
			VillagerProfession.FISHERMAN,
			2,
			factories -> {
				factories.add(new TradeOffers.ProcessItemFactory(TaiaoItems.EEL, 6, TaiaoItems.COOKED_EEL, 6, 16, 5));
				factories.add(new TradeOffers.SellItemFactory(TaiaoBlocks.HIINAKI.asItem(), 2, 1, 5));
			}
		);
		TradeOfferHelper.registerVillagerOffers(
			VillagerProfession.FISHERMAN,
			3,
			factories -> factories.add(new TradeOffers.BuyForOneEmeraldFactory(TaiaoItems.EEL, 13, 16, 20))
		);

		// Shepherd
		TradeOfferHelper.registerVillagerOffers(
			VillagerProfession.SHEPHERD,
			1,
			factories -> factories.add(new TradeOffers.TypeAwareBuyForOneEmeraldFactory(
				18,
				16,
				2,
				ImmutableMap.of(TaiaoVillagerTypes.MAAORI, TaiaoBlocks.HARAKEKE.asItem())
			))
		);
		TradeOfferHelper.registerVillagerOffers(
			VillagerProfession.SHEPHERD,
			2,
			factories -> {
				factories.add(new TypeAwareSellItemFactory(
					ImmutableMap.of(TaiaoVillagerTypes.MAAORI, new ItemStack(TaiaoBlocks.HARAKEKE)),
					1,
					16,
					5
				));
				factories.add(new TypeAwareSellItemFactory(
					ImmutableMap.of(TaiaoVillagerTypes.MAAORI, new ItemStack(TaiaoBlocks.HARAKEKE_MAT, 4)),
					1,
					16,
					5
				));
			}
		);
		TradeOfferHelper.registerVillagerOffers(VillagerProfession.SHEPHERD, 5, TaiaoTradeOffers::addTukutukuFactories);

		// Cartographer
		TradeOfferHelper.registerVillagerOffers(
			VillagerProfession.CARTOGRAPHER,
			5,
			TaiaoTradeOffers::addTukutukuFactories
		);
	}

	protected static void addTukutukuFactories(@NotNull List<TradeOffers.Factory> factories) {
		factories.add(new TypeAwareSellItemFactory(
			ImmutableMap.of(TaiaoVillagerTypes.MAAORI, new ItemStack(TaiaoItems.KIWI_BANNER_PATTERN)),
			6,
			12,
			40
		));

		factories.add(new TypeAwareSellItemFactory(
			ImmutableMap.of(TaiaoVillagerTypes.MAAORI, TaiaoBanners.KIWI_TUKUTUKU.getOrCreateStack()),
			4,
			12,
			30
		));
		factories.add(new TypeAwareSellItemFactory(
			ImmutableMap.of(TaiaoVillagerTypes.MAAORI, TaiaoBanners.KAOKAO_TUKUTUKU.getOrCreateStack()),
			4,
			12,
			30
		));
		factories.add(new TypeAwareSellItemFactory(
			ImmutableMap.of(TaiaoVillagerTypes.MAAORI, TaiaoBanners.POUTAMA_TUKUTUKU_LEFT.getOrCreateStack()),
			4,
			12,
			30
		));
		factories.add(new TypeAwareSellItemFactory(
			ImmutableMap.of(TaiaoVillagerTypes.MAAORI, TaiaoBanners.POUTAMA_TUKUTUKU_RIGHT.getOrCreateStack()),
			4,
			12,
			30
		));
		factories.add(new TypeAwareSellItemFactory(
			ImmutableMap.of(TaiaoVillagerTypes.MAAORI, TaiaoBanners.PAATIKI_TUKUTUKU.getOrCreateStack()),
			4,
			12,
			30
		));
		factories.add(new TypeAwareSellItemFactory(
			ImmutableMap.of(TaiaoVillagerTypes.MAAORI, TaiaoBanners.PAATIKI_TUKUTUKU_TOP.getOrCreateStack()),
			4,
			12,
			30
		));
		factories.add(new TypeAwareSellItemFactory(
			ImmutableMap.of(TaiaoVillagerTypes.MAAORI, TaiaoBanners.PAATIKI_TUKUTUKU_BOTTOM.getOrCreateStack()),
			4,
			12,
			30
		));
	}

	public static class TypeAwareSellItemFactory implements TradeOffers.Factory {
		protected final Map<VillagerType, ItemStack> map;

		protected final int price;
		protected final int maxUses;
		protected final int experience;
		protected final float multiplier;

		public TypeAwareSellItemFactory(
			Map<VillagerType, ItemStack> sell,
			int price,
			int maxUses,
			int experience
		) {
			this(sell, price, maxUses, experience, 0.05f);
		}

		public TypeAwareSellItemFactory(
			Map<VillagerType, ItemStack> sell,
			int price,
			int maxUses,
			int experience,
			float multiplier
		) {
			this.map = sell;

			this.price = price;
			this.maxUses = maxUses;
			this.experience = experience;
			this.multiplier = multiplier;
		}

		@Override
		public @Nullable TradeOffer create(Entity entity, Random random) {
			if (entity instanceof VillagerDataContainer villager) {
				ItemStack sell = this.map.get(villager.getVariant());

				if (sell != null) {
					return new TradeOffer(
						new ItemStack(Items.EMERALD, this.price),
						sell,
						this.maxUses,
						this.experience,
						this.multiplier
					);
				}
			}

			return null;
		}
	}
}
