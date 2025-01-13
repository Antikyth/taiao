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
        getOrCreateTagBuilder(TaiaoBlockTags.CABBAGE_TREE_LOGS).add(TaiaoBlocks.CABBAGE_TREE_LOG)
                .add(TaiaoBlocks.CABBAGE_TREE_WOOD)
                .add(TaiaoBlocks.STRIPPED_CABBAGE_TREE_LOG)
                .add(TaiaoBlocks.STRIPPED_CABBAGE_TREE_WOOD);
        getOrCreateTagBuilder(TaiaoBlockTags.KAURI_LOGS).add(TaiaoBlocks.KAURI_LOG)
                .add(TaiaoBlocks.STRIPPED_KAURI_LOG)
                .add(TaiaoBlocks.KAURI_WOOD)
                .add(TaiaoBlocks.STRIPPED_KAURI_WOOD);
        getOrCreateTagBuilder(TaiaoBlockTags.THIN_LOGS).addTag(TaiaoBlockTags.CABBAGE_TREE_LOGS);
        getOrCreateTagBuilder(TaiaoBlockTags.THIN_LOG_CONNECTION_OVERRIDE).addTag(TaiaoBlockTags.THIN_LOGS)
                .addTag(BlockTags.LEAVES);

        // Vanilla tags
        getOrCreateTagBuilder(BlockTags.FLOWER_POTS).add(TaiaoBlocks.POTTED_CABBAGE_TREE_SAPLING);
        getOrCreateTagBuilder(BlockTags.LEAVES).add(TaiaoBlocks.CABBAGE_TREE_LEAVES);
        getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN).addTag(TaiaoBlockTags.KAURI_LOGS)
                .addTag(TaiaoBlockTags.CABBAGE_TREE_LOGS);
        getOrCreateTagBuilder(BlockTags.PLANKS).add(TaiaoBlocks.KAURI_PLANKS);
        getOrCreateTagBuilder(BlockTags.SAPLINGS).add(TaiaoBlocks.CABBAGE_TREE_SAPLING);
        getOrCreateTagBuilder(BlockTags.WALL_POST_OVERRIDE).addTag(TaiaoBlockTags.THIN_LOGS);
    }
}
