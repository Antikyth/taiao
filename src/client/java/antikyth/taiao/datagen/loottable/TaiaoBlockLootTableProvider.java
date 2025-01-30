// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen.loottable;

import antikyth.taiao.block.TaiaoBlocks;
import antikyth.taiao.block.leaves.FruitLeavesBlock;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.predicate.StatePredicate;
import org.jetbrains.annotations.NotNull;

public class TaiaoBlockLootTableProvider extends FabricBlockLootTableProvider {
    public TaiaoBlockLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        // Kauri foliage
        addDrop(TaiaoBlocks.KAURI_SAPLING);
        addPottedPlantDrops(TaiaoBlocks.POTTED_KAURI_SAPLING);
        addDrop(TaiaoBlocks.KAURI_LEAVES, block -> leavesDrops(block, TaiaoBlocks.KAURI_SAPLING, SAPLING_DROP_CHANCE));
        // Kauri wood
        addDrop(TaiaoBlocks.KAURI_LOG);
        addDrop(TaiaoBlocks.STRIPPED_KAURI_LOG);
        addDrop(TaiaoBlocks.KAURI_WOOD);
        addDrop(TaiaoBlocks.STRIPPED_KAURI_WOOD);
        // Kauri wood family
        addDropsForFamily(TaiaoBlocks.WoodFamily.KAURI.getBlockFamily());

        // Kahikatea foliage
        addDrop(TaiaoBlocks.KAHIKATEA_SAPLING);
        addPottedPlantDrops(TaiaoBlocks.POTTED_KAHIKATEA_SAPLING);
        addDrop(
                TaiaoBlocks.KAHIKATEA_LEAVES,
                block -> fruitLeavesDrops((FruitLeavesBlock) block, TaiaoBlocks.KAHIKATEA_SAPLING, SAPLING_DROP_CHANCE)
        );
        // Kahikatea wood
        addDrop(TaiaoBlocks.KAHIKATEA_LOG);
        addDrop(TaiaoBlocks.STRIPPED_KAHIKATEA_LOG);
        addDrop(TaiaoBlocks.KAHIKATEA_WOOD);
        addDrop(TaiaoBlocks.STRIPPED_KAHIKATEA_WOOD);
        // Kahikatea wood family
        addDropsForFamily(TaiaoBlocks.WoodFamily.KAHIKATEA.getBlockFamily());

        // Rimu foliage
        addDrop(TaiaoBlocks.RIMU_SAPLING);
        addPottedPlantDrops(TaiaoBlocks.POTTED_RIMU_SAPLING);
        addDrop(
                TaiaoBlocks.RIMU_LEAVES,
                block -> fruitLeavesDrops((FruitLeavesBlock) block, TaiaoBlocks.RIMU_SAPLING, SAPLING_DROP_CHANCE)
        );
        // Rimu wood
        addDrop(TaiaoBlocks.RIMU_LOG);
        addDrop(TaiaoBlocks.STRIPPED_RIMU_LOG);
        addDrop(TaiaoBlocks.RIMU_WOOD);
        addDrop(TaiaoBlocks.STRIPPED_RIMU_WOOD);
        // Rimu wood family
        addDropsForFamily(TaiaoBlocks.WoodFamily.RIMU.getBlockFamily());

        // Tī kōuka foliage
        addDrop(TaiaoBlocks.CABBAGE_TREE_SAPLING);
        addPottedPlantDrops(TaiaoBlocks.POTTED_CABBAGE_TREE_SAPLING);
        addDrop(
                TaiaoBlocks.CABBAGE_TREE_LEAVES,
                block -> leavesDrops(block, TaiaoBlocks.CABBAGE_TREE_SAPLING, SAPLING_DROP_CHANCE)
        );
        // Tī kōuka wood
        addDrop(TaiaoBlocks.CABBAGE_TREE_LOG);
        addDrop(TaiaoBlocks.CABBAGE_TREE_WOOD);
        addDrop(TaiaoBlocks.STRIPPED_CABBAGE_TREE_LOG);
        addDrop(TaiaoBlocks.STRIPPED_CABBAGE_TREE_WOOD);

        // Mamaku foliage
        addDrop(TaiaoBlocks.MAMAKU_SAPLING);
        addPottedPlantDrops(TaiaoBlocks.POTTED_MAMAKU_SAPLING);
        addDrop(
                TaiaoBlocks.MAMAKU_LEAVES,
                block -> leavesDrops(block, TaiaoBlocks.MAMAKU_SAPLING, SAPLING_DROP_CHANCE)
        );
        // Mamaku wood
        addDrop(TaiaoBlocks.MAMAKU_LOG);
        addDrop(TaiaoBlocks.MAMAKU_WOOD);
        addDrop(TaiaoBlocks.STRIPPED_MAMAKU_LOG);
        addDrop(TaiaoBlocks.STRIPPED_MAMAKU_WOOD);
        // Mamaku wood family
        addDropsForFamily(TaiaoBlocks.WoodFamily.MAMAKU.getBlockFamily());

        // Whekī ponga foliage
        addDrop(TaiaoBlocks.WHEKII_PONGA_SAPLING);
        addPottedPlantDrops(TaiaoBlocks.POTTED_WHEKII_PONGA_SAPLING);
        addDrop(
                TaiaoBlocks.WHEKII_PONGA_LEAVES,
                block -> leavesDrops(block, TaiaoBlocks.WHEKII_PONGA_SAPLING, SAPLING_DROP_CHANCE)
        );
        // Whekī ponga wood
        addDrop(TaiaoBlocks.WHEKII_PONGA_LOG);
        addDrop(TaiaoBlocks.WHEKII_PONGA_WOOD);
        addDrop(TaiaoBlocks.STRIPPED_WHEKII_PONGA_LOG);
        addDrop(TaiaoBlocks.STRIPPED_WHEKII_PONGA_WOOD);

        addDrop(TaiaoBlocks.RAUPOO, BlockLootTableGenerator::dropsWithShears);
    }

    public void addDropsForFamily(@NotNull BlockFamily family) {
        addDrop(family.getBaseBlock());

        for (Block block : family.getVariants().values()) {
            addDrop(block);
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
}
