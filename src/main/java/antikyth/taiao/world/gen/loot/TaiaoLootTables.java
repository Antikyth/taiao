// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.world.gen.loot;

import antikyth.taiao.Taiao;
import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.item.TaiaoItems;
import antikyth.taiao.world.gen.feature.TaiaoConfiguredFeatures;
import antikyth.taiao.world.gen.structure.processor.TaiaoStructureProcessorLists;
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
	protected static Map<Identifier, LootTable.Builder> BAIT_LOOT_TABLES = new HashMap<>();

	// TODO: add wētā
	/**
	 * The bait used for {@linkplain TaiaoConfiguredFeatures#HIINAKI hīnaki features}.
	 * <p>
	 * For the bait put in a marae village fisher hīnaki, see {@link TaiaoStructureProcessorLists#FISHER_MARAE}.
	 */
	public static final Identifier HIINAKI_BAIT = registerBait(
		Taiao.id("bait/hiinaki"),
		LootTable.builder()
			.pool(
				LootPool.builder()
					.with(ItemEntry.builder(TaiaoItems.EEL))
					.with(ItemEntry.builder(Items.CHICKEN))
					.with(ItemEntry.builder(Items.FROGSPAWN))
			)
	);

	public static final Identifier VILLAGE_MARAE_HOUSE_CHEST = registerChest(
		Taiao.id("chests/village/village_marae_house"),
		LootTable.builder()
			.pool(
				LootPool.builder()
					.rolls(UniformLootNumberProvider.create(3f, 8f))
					.with(
						ItemEntry.builder(TaiaoItems.CONIFER_FRUIT)
							.weight(5)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 10f)))
					)
					.with(
						ItemEntry.builder(TaiaoBlocks.HARAKEKE)
							.weight(10)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 7f)))
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
							.weight(2)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 2f)))
					)
					.with(
						ItemEntry.builder(TaiaoBlocks.CHISELED_STRIPPED_RIMU_WOOD)
							.weight(2)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 2f)))
					)
			)
	);
	public static final Identifier VILLAGE_MARAE_PAATAKA_KAI_CHEST = registerChest(
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
	public static final Identifier VILLAGE_MARAE_PAA_HARAKEKE_CHEST = registerChest(
		Taiao.id("chests/village/village_marae_paa_harakeke"),
		LootTable.builder()
			.pool(
				LootPool.builder()
					.rolls(UniformLootNumberProvider.create(3f, 8f))
					.with(
						ItemEntry.builder(TaiaoBlocks.HARAKEKE)
							.weight(16)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 10f)))
					)
					.with(
						ItemEntry.builder(TaiaoBlocks.HARAKEKE_MAT)
							.weight(6)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 5f)))
					)
					.with(
						ItemEntry.builder(TaiaoBlocks.RAUPOO)
							.weight(4)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 7f)))
					)
					.with(
						ItemEntry.builder(TaiaoBlocks.THATCH_ROOF)
							.weight(2)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 3f)))
					)
					.with(
						ItemEntry.builder(TaiaoBlocks.THATCH_ROOF_TOP)
							.weight(1)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 3f)))
					)
					// TODO: add tukutuku
					.with(
						ItemEntry.builder(Items.EMERALD)
							.weight(2)
					)
					.with(
						ItemEntry.builder(Items.SHEARS)
							.weight(3)
					)
			)
	);
	public static final Identifier VILLAGE_MARAE_KAAUTA_CHEST = registerChest(
		Taiao.id("chests/village/village_marae_kaauta"),
		LootTable.builder()
			.pool(
				LootPool.builder()
					.rolls(UniformLootNumberProvider.create(3f, 8f))
					.with(
						ItemEntry.builder(Items.CHICKEN)
							.weight(12)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 3f)))
					)
					.with(
						ItemEntry.builder(Items.COD)
							.weight(4)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 3f)))
					)
					.with(
						ItemEntry.builder(TaiaoItems.EEL)
							.weight(4)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 3f)))
					)
					.with(
						ItemEntry.builder(Items.WHEAT)
							.weight(2)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 3f)))
					)
					.with(
						ItemEntry.builder(Items.POTATO)
							.weight(2)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 3f)))
					)
					.with(
						ItemEntry.builder(Items.CARROT)
							.weight(2)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 3f)))
					)
					.with(
						ItemEntry.builder(Items.FEATHER)
							.weight(3)
					)
					.with(
						ItemEntry.builder(Items.CHARCOAL)
							.weight(3)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 3f)))
					)
					.with(
						ItemEntry.builder(Items.EMERALD)
							.weight(1)
					)
			)
	);
	public static final Identifier VILLAGE_MARAE_FISHER_CHEST = registerChest(
		Taiao.id("chests/village/village_marae_fisher"),
		LootTable.builder()
			.pool(
				LootPool.builder()
					.rolls(UniformLootNumberProvider.create(3f, 8f))
					.with(
						ItemEntry.builder(TaiaoItems.EEL)
							.weight(3)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 3f)))
					)
					.with(
						ItemEntry.builder(Items.COD)
							.weight(2)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 3f)))
					)
					.with(
						ItemEntry.builder(Items.SALMON)
							.weight(1)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 3f)))
					)
					.with(
						ItemEntry.builder(TaiaoBlocks.HIINAKI)
							.weight(3)
					)
					.with(
						ItemEntry.builder(Items.BARREL)
							.weight(1)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 3f)))
					)
					.with(
						ItemEntry.builder(Items.WATER_BUCKET)
							.weight(1)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 3f)))
					)
					.with(
						ItemEntry.builder(Items.CHARCOAL)
							.weight(2)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 3f)))
					)
					.with(
						ItemEntry.builder(TaiaoBlocks.RAUPOO)
							.weight(1)
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 3f)))
					)
					.with(
						ItemEntry.builder(Items.EMERALD)
							.weight(1)
					)
			)
	);

	public static void bootstrapChestLootTables(BiConsumer<Identifier, LootTable.Builder> exporter) {
		Taiao.LOGGER.debug("Registering chest loot tables");

		for (Map.Entry<Identifier, LootTable.Builder> entry : CHEST_LOOT_TABLES.entrySet()) {
			exporter.accept(entry.getKey(), entry.getValue());
		}
	}

	public static void bootstrapBaitLootTables(BiConsumer<Identifier, LootTable.Builder> exporter) {
		Taiao.LOGGER.debug("Registering bait loot tables");

		for (Map.Entry<Identifier, LootTable.Builder> entry : BAIT_LOOT_TABLES.entrySet()) {
			exporter.accept(entry.getKey(), entry.getValue());
		}
	}

	public static Identifier registerChest(Identifier id, LootTable.Builder builder) {
		CHEST_LOOT_TABLES.put(id, builder);

		return id;
	}

	public static Identifier registerBait(Identifier id, LootTable.Builder builder) {
		BAIT_LOOT_TABLES.put(id, builder);

		return id;
	}
}
