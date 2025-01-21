// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen.tag;

import antikyth.taiao.block.TaiaoBlockTags;
import antikyth.taiao.block.TaiaoBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class TaiaoBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public TaiaoBlockTagProvider(
            FabricDataOutput output,
            CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture
    ) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup lookup) {
        // Te Taiao o Aotearoa tags
        getOrCreateTagBuilder(TaiaoBlockTags.KAURI_LOGS)
                .add(TaiaoBlocks.KAURI_LOG)
                .add(TaiaoBlocks.STRIPPED_KAURI_LOG)
                .add(TaiaoBlocks.KAURI_WOOD)
                .add(TaiaoBlocks.STRIPPED_KAURI_WOOD);
        getOrCreateTagBuilder(TaiaoBlockTags.CABBAGE_TREE_LOGS)
                .add(TaiaoBlocks.CABBAGE_TREE_LOG)
                .add(TaiaoBlocks.CABBAGE_TREE_WOOD)
                .add(TaiaoBlocks.STRIPPED_CABBAGE_TREE_LOG)
                .add(TaiaoBlocks.STRIPPED_CABBAGE_TREE_WOOD);
        getOrCreateTagBuilder(TaiaoBlockTags.MAMAKU_LOGS)
                .add(TaiaoBlocks.MAMAKU_LOG)
                .add(TaiaoBlocks.MAMAKU_WOOD);

        getOrCreateTagBuilder(TaiaoBlockTags.THIN_LOGS)
                .addTag(TaiaoBlockTags.CABBAGE_TREE_LOGS)
                .addTag(TaiaoBlockTags.MAMAKU_LOGS);
        getOrCreateTagBuilder(TaiaoBlockTags.THIN_LOG_CONNECTION_OVERRIDE)
                .addTag(TaiaoBlockTags.THIN_LOGS)
                .addTag(BlockTags.LEAVES);
        getOrCreateTagBuilder(TaiaoBlockTags.DIRECTIONAL_LEAVES)
                .add(TaiaoBlocks.MAMAKU_LEAVES);

        // Vanilla tags
        getOrCreateTagBuilder(BlockTags.FLOWER_POTS)
                .add(TaiaoBlocks.POTTED_KAURI_SAPLING)
                .add(TaiaoBlocks.POTTED_CABBAGE_TREE_SAPLING)
                .add(TaiaoBlocks.POTTED_MAMAKU_SAPLING);
        getOrCreateTagBuilder(BlockTags.LEAVES)
                .add(TaiaoBlocks.KAURI_LEAVES)
                .add(TaiaoBlocks.CABBAGE_TREE_LEAVES)
                .add(TaiaoBlocks.MAMAKU_LEAVES);
        getOrCreateTagBuilder(BlockTags.SAPLINGS)
                .add(TaiaoBlocks.KAURI_SAPLING)
                .add(TaiaoBlocks.CABBAGE_TREE_SAPLING)
                .add(TaiaoBlocks.MAMAKU_SAPLING);
        getOrCreateTagBuilder(BlockTags.WALL_POST_OVERRIDE).addTag(TaiaoBlockTags.THIN_LOGS);

        getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN)
                .addTag(TaiaoBlockTags.KAURI_LOGS)
                .addTag(TaiaoBlockTags.CABBAGE_TREE_LOGS)
                .addTag(TaiaoBlockTags.MAMAKU_LOGS);
        getOrCreateTagBuilder(BlockTags.OVERWORLD_NATURAL_LOGS)
                .add(TaiaoBlocks.KAURI_LOG)
                .add(TaiaoBlocks.CABBAGE_TREE_LOG)
                .add(TaiaoBlocks.MAMAKU_LOG);

        getOrCreateTagBuilder(BlockTags.PLANKS).add(TaiaoBlocks.KAURI_PLANKS);
        getOrCreateTagBuilder(BlockTags.WOODEN_BUTTONS).add(TaiaoBlocks.KAURI_BUTTON);
        getOrCreateTagBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(TaiaoBlocks.KAURI_PRESSURE_PLATE);
        getOrCreateTagBuilder(BlockTags.WOODEN_STAIRS).add(TaiaoBlocks.KAURI_STAIRS);
        getOrCreateTagBuilder(BlockTags.WOODEN_SLABS).add(TaiaoBlocks.KAURI_SLAB);
        getOrCreateTagBuilder(BlockTags.WOODEN_FENCES).add(TaiaoBlocks.KAURI_FENCE);
        getOrCreateTagBuilder(BlockTags.FENCE_GATES).add(TaiaoBlocks.KAURI_FENCE_GATE);
    }
}
