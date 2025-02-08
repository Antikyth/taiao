// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.loot;

import antikyth.taiao.Taiao;
import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.item.TaiaoItems;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

@SuppressWarnings("unused")
public class TaiaoLootTables {
	protected static Map<Identifier, LootTable.Builder> CHEST_LOOT_TABLES = new HashMap<>();

	public static final Identifier VILLAGE_MARAE_HOUSE_CHEST = register(
		Taiao.id("chests/village/village_marae_house"),
		LootTable.builder()
			.pool(
				LootPool.builder()
					.rolls(UniformLootNumberProvider.create(3f, 8f))
					.with(
						ItemEntry.builder(TaiaoItems.CONIFER_FRUIT)
							.weight(10)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 10f)))
					)
					.with(
						ItemEntry.builder(Items.FERN)
							.weight(2)
					)
					.with(
						ItemEntry.builder(Items.LARGE_FERN)
							.weight(2)
					)
					.with(
						ItemEntry.builder(Items.FEATHER)
							.weight(1)
					)
					.with(
						ItemEntry.builder(Items.EMERALD)
							.weight(2)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 4f)))
					)
					.with(
						ItemEntry.builder(TaiaoBlocks.RIMU_SAPLING)
							.weight(1)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 2f)))
					)
					.with(
						ItemEntry.builder(TaiaoBlocks.KAURI_SAPLING)
							.weight(1)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 2f)))
					)
					.with(
						ItemEntry.builder(TaiaoBlocks.CABBAGE_TREE_SAPLING)
							.weight(1)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 3f)))
					)
					.with(
						ItemEntry.builder(TaiaoBlocks.MAMAKU_SAPLING)
							.weight(1)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 4f)))
					)
					.with(
						ItemEntry.builder(TaiaoBlocks.WHEKII_PONGA_SAPLING)
							.weight(1)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 5f)))
					)
					.with(
						ItemEntry.builder(TaiaoBlocks.RIMU_LOG)
							.weight(4)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 5f)))
					)
					.with(
						ItemEntry.builder(TaiaoBlocks.KAURI_LOG)
							.weight(4)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 5f)))
					)
					.with(
						ItemEntry.builder(TaiaoBlocks.CHISELED_STRIPPED_RIMU_LOG)
							.weight(3)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 3f)))
					)
					.with(
						ItemEntry.builder(TaiaoBlocks.CHISELED_STRIPPED_RIMU_WOOD)
							.weight(1)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 2f)))
					)
			)
	);
	public static final Identifier VILLAGE_MARAE_PAATAKA_KAI_CHEST = register(
		Taiao.id("chests/village/village_marae_paataka_kai"),
		LootTable.builder()
			.pool(
				LootPool.builder()
					.rolls(UniformLootNumberProvider.create(3f, 8f))
					.with(
						ItemEntry.builder(TaiaoItems.CONIFER_FRUIT)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(4f, 14f)))
					)
					.with(
						ItemEntry.builder(Items.POTATO)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 7f)))
					)
					.with(
						ItemEntry.builder(Items.BREAD)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 4f)))
					)
					.with(
						ItemEntry.builder(Items.APPLE)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 5f)))
					)
					.with(
						ItemEntry.builder(Items.CARROT)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(4f, 8f)))
					)
					.with(
						ItemEntry.builder(Items.WHEAT)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(8f, 21f)))
					)
			)
	);

	public static void bootstrap(BiConsumer<Identifier, LootTable.Builder> exporter) {
		Taiao.LOGGER.debug("Registering loot tables");

		for (Map.Entry<Identifier, LootTable.Builder> entry : CHEST_LOOT_TABLES.entrySet()) {
			exporter.accept(entry.getKey(), entry.getValue());
		}
	}

	public static Identifier register(Identifier id, LootTable.Builder builder) {
		CHEST_LOOT_TABLES.put(id, builder);

		return id;
	}
}
