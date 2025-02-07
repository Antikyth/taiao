// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen.loottable;

import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.block.leaves.FruitLeavesBlock;
import antikyth.taiao.block.plant.TripleTallPlantBlock;
import antikyth.taiao.block.plant.TripleTallPlantBlock.TripleBlockPart;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.condition.LocationCheckLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.predicate.BlockPredicate;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

public class TaiaoBlockLootTableProvider extends FabricBlockLootTableProvider {
	public TaiaoBlockLootTableProvider(FabricDataOutput dataOutput) {
		super(dataOutput);
	}

	@Override
	public void generate() {
		// Kauri foliage
		this.addDrop(TaiaoBlocks.KAURI_SAPLING);
		addPottedPlantDrops(TaiaoBlocks.POTTED_KAURI_SAPLING);
		this.addDrop(
			TaiaoBlocks.KAURI_LEAVES,
			block -> leavesDrops(block, TaiaoBlocks.KAURI_SAPLING, SAPLING_DROP_CHANCE)
		);
		// Kauri wood
		this.addDrop(TaiaoBlocks.KAURI_LOG);
		this.addDrop(TaiaoBlocks.STRIPPED_KAURI_LOG);
		this.addDrop(TaiaoBlocks.KAURI_WOOD);
		this.addDrop(TaiaoBlocks.STRIPPED_KAURI_WOOD);
		// Kauri wood family
		this.addDropsForFamily(TaiaoBlocks.WoodFamily.KAURI.getBlockFamily());

		// Kahikatea foliage
		this.addDrop(TaiaoBlocks.KAHIKATEA_SAPLING);
		addPottedPlantDrops(TaiaoBlocks.POTTED_KAHIKATEA_SAPLING);
		this.addDrop(
			TaiaoBlocks.KAHIKATEA_LEAVES,
			block -> fruitLeavesDrops((FruitLeavesBlock) block, TaiaoBlocks.KAHIKATEA_SAPLING, SAPLING_DROP_CHANCE)
		);
		// Kahikatea wood
		this.addDrop(TaiaoBlocks.KAHIKATEA_LOG);
		this.addDrop(TaiaoBlocks.STRIPPED_KAHIKATEA_LOG);
		this.addDrop(TaiaoBlocks.KAHIKATEA_WOOD);
		this.addDrop(TaiaoBlocks.STRIPPED_KAHIKATEA_WOOD);
		// Kahikatea wood family
		this.addDropsForFamily(TaiaoBlocks.WoodFamily.KAHIKATEA.getBlockFamily());

		// Rimu foliage
		this.addDrop(TaiaoBlocks.RIMU_SAPLING);
		addPottedPlantDrops(TaiaoBlocks.POTTED_RIMU_SAPLING);
		this.addDrop(
			TaiaoBlocks.RIMU_LEAVES,
			block -> fruitLeavesDrops((FruitLeavesBlock) block, TaiaoBlocks.RIMU_SAPLING, SAPLING_DROP_CHANCE)
		);
		// Rimu wood
		this.addDrop(TaiaoBlocks.RIMU_LOG);
		this.addDrop(TaiaoBlocks.STRIPPED_RIMU_LOG);
		this.addDrop(TaiaoBlocks.RIMU_WOOD);
		this.addDrop(TaiaoBlocks.STRIPPED_RIMU_WOOD);
		this.addDrop(TaiaoBlocks.CHISELED_STRIPPED_RIMU_LOG);
		this.addDrop(TaiaoBlocks.CHISELED_STRIPPED_RIMU_WOOD);
		// Rimu wood family
		this.addDropsForFamily(TaiaoBlocks.WoodFamily.RIMU.getBlockFamily());

		// Tī kōuka foliage
		this.addDrop(TaiaoBlocks.CABBAGE_TREE_SAPLING);
		addPottedPlantDrops(TaiaoBlocks.POTTED_CABBAGE_TREE_SAPLING);
		this.addDrop(
			TaiaoBlocks.CABBAGE_TREE_LEAVES,
			block -> leavesDrops(block, TaiaoBlocks.CABBAGE_TREE_SAPLING, SAPLING_DROP_CHANCE)
		);
		// Tī kōuka wood
		this.addDrop(TaiaoBlocks.CABBAGE_TREE_LOG);
		this.addDrop(TaiaoBlocks.CABBAGE_TREE_WOOD);
		this.addDrop(TaiaoBlocks.STRIPPED_CABBAGE_TREE_LOG);
		this.addDrop(TaiaoBlocks.STRIPPED_CABBAGE_TREE_WOOD);

		// Mamaku foliage
		this.addDrop(TaiaoBlocks.MAMAKU_SAPLING);
		addPottedPlantDrops(TaiaoBlocks.POTTED_MAMAKU_SAPLING);
		this.addDrop(
			TaiaoBlocks.MAMAKU_LEAVES,
			block -> leavesDrops(block, TaiaoBlocks.MAMAKU_SAPLING, SAPLING_DROP_CHANCE)
		);
		// Mamaku wood
		this.addDrop(TaiaoBlocks.MAMAKU_LOG);
		this.addDrop(TaiaoBlocks.MAMAKU_WOOD);
		this.addDrop(TaiaoBlocks.STRIPPED_MAMAKU_LOG);
		this.addDrop(TaiaoBlocks.STRIPPED_MAMAKU_WOOD);
		// Mamaku wood family
		this.addDropsForFamily(TaiaoBlocks.WoodFamily.MAMAKU.getBlockFamily());

		// Whekī ponga foliage
		this.addDrop(TaiaoBlocks.WHEKII_PONGA_SAPLING);
		addPottedPlantDrops(TaiaoBlocks.POTTED_WHEKII_PONGA_SAPLING);
		this.addDrop(
			TaiaoBlocks.WHEKII_PONGA_LEAVES,
			block -> leavesDrops(block, TaiaoBlocks.WHEKII_PONGA_SAPLING, SAPLING_DROP_CHANCE)
		);
		// Whekī ponga wood
		this.addDrop(TaiaoBlocks.WHEKII_PONGA_LOG);
		this.addDrop(TaiaoBlocks.WHEKII_PONGA_WOOD);
		this.addDrop(TaiaoBlocks.STRIPPED_WHEKII_PONGA_LOG);
		this.addDrop(TaiaoBlocks.STRIPPED_WHEKII_PONGA_WOOD);

		this.addDrop(TaiaoBlocks.GIANT_CANE_RUSH, block -> tripleTallPlantDrops(block, Items.WHEAT_SEEDS, 0.125f));
		this.addDrop(TaiaoBlocks.RAUPOO, BlockLootTableGenerator::dropsWithShears);
	}

	public void addDropsForFamily(@NotNull BlockFamily family) {
		this.addDrop(family.getBaseBlock());

		for (Block block : family.getVariants().values()) {
			this.addDrop(block);
		}
	}

	public LootTable.Builder fruitLeavesDrops(FruitLeavesBlock leaves, Block drop, float... chance) {
		return leavesDrops(leaves, drop, chance)
			.pool(
				LootPool.builder()
					.conditionally(
						BlockStatePropertyLootCondition.builder(leaves)
							.properties(
								StatePredicate.Builder.create()
									.exactMatch(FruitLeavesBlock.FRUIT, true)
							)
					)
					.apply(ApplyBonusLootFunction.uniformBonusCount(Enchantments.FORTUNE))
					.with(ItemEntry.builder(leaves.fruitItem))
			);
	}

	public LootTable.Builder tripleTallPlantDrops(Block tripleTallPlant, ItemConvertible seeds, float seedsChance) {
		LootTable.Builder table = LootTable.builder();

		LootPoolEntry.Builder<?> drops = ItemEntry.builder(tripleTallPlant)
			.conditionally(WITH_SHEARS)
			.alternatively(
				this.addSurvivesExplosionCondition(tripleTallPlant, ItemEntry.builder(seeds))
					.conditionally(RandomChanceLootCondition.builder(seedsChance))
			);

		// For each block part, validate that the others are in the correct positions.
		for (int i = 0; i < TripleBlockPart.values().length; i++) {
			TripleBlockPart part = TripleBlockPart.values()[i];

			LootPool.Builder pool = LootPool.builder()
				.with(drops)
				.conditionally(
					BlockStatePropertyLootCondition.builder(tripleTallPlant)
						.properties(StatePredicate.Builder.create().exactMatch(TripleTallPlantBlock.PART, part))
				);

			// Ensure that the other parts are in the correct positions.
			for (int j = 0; j < TripleBlockPart.values().length; j++) {
				if (j == i) continue;

				TripleBlockPart otherPart = TripleBlockPart.values()[j];
				BlockPos relativePos = new BlockPos(0, j - i, 0);

				pool.conditionally(
					LocationCheckLootCondition.builder(
						LocationPredicate.Builder.create()
							.block(
								BlockPredicate.Builder.create()
									.blocks(tripleTallPlant)
									.state(
										StatePredicate.Builder.create()
											.exactMatch(TripleTallPlantBlock.PART, otherPart)
											.build()
									)
									.build()
							),
						relativePos
					)
				);
			}

			table.pool(pool);
		}

		return table;
	}
}
