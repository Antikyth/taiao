// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen.loottable;

import antikyth.taiao.block.TaiaoBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.data.family.BlockFamily;
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
                block -> leavesDrops(block, TaiaoBlocks.KAHIKATEA_SAPLING, SAPLING_DROP_CHANCE)
        );
        // Kahikatea wood
        addDrop(TaiaoBlocks.KAHIKATEA_LOG);
        addDrop(TaiaoBlocks.STRIPPED_KAHIKATEA_LOG);
        addDrop(TaiaoBlocks.KAHIKATEA_WOOD);
        addDrop(TaiaoBlocks.STRIPPED_KAHIKATEA_WOOD);
        // Kahikatea wood family
        addDropsForFamily(TaiaoBlocks.WoodFamily.KAHIKATEA.getBlockFamily());

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
    }

    public void addDropsForFamily(@NotNull BlockFamily family) {
        addDrop(family.getBaseBlock());

        for (Block block : family.getVariants().values()) {
            addDrop(block);
        }
    }
}
