// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package antikyth.taiao.datagen.tag;

import antikyth.taiao.block.TaiaoBlockTags;
import antikyth.taiao.item.TaiaoItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TaiaoItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public TaiaoItemTagProvider(
            FabricDataOutput output,
            CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup,
            @Nullable FabricTagProvider.BlockTagProvider blockTagProvider
    ) {
        super(output, registryLookup, blockTagProvider);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup lookup) {
        // Conventional item tags
        getOrCreateTagBuilder(TaiaoItemTags.CONVENTIONAL_SEEDS)
                .add(Items.WHEAT_SEEDS)
                .add(Items.MELON_SEEDS)
                .add(Items.PUMPKIN_SEEDS)
                .add(Items.BEETROOT_SEEDS)
                .add(Items.TORCHFLOWER_SEEDS)
                .add(Items.PITCHER_POD);
        getOrCreateTagBuilder(TaiaoItemTags.CONVENTIONAL_BERRIES)
                .add(Items.SWEET_BERRIES)
                .add(Items.GLOW_BERRIES);

        // Te Taiao o Aotearoa item tags
        getOrCreateTagBuilder(TaiaoItemTags.KIWI_FOOD)
                .addOptionalTag(TaiaoItemTags.CONVENTIONAL_SEEDS)
                .addOptionalTag(TaiaoItemTags.CONVENTIONAL_BERRIES);
        getOrCreateTagBuilder(TaiaoItemTags.PUUKEKO_FOOD)
                .addOptionalTag(TaiaoItemTags.CONVENTIONAL_SEEDS);
        getOrCreateTagBuilder(TaiaoItemTags.MOA_FOOD)
                .addTag(ItemTags.LEAVES)
                .addTag(ItemTags.SAPLINGS);

        // Te Taiao o Aotearoa block tags
        copy(TaiaoBlockTags.KAURI_LOGS, TaiaoItemTags.KAURI_LOGS);
        copy(TaiaoBlockTags.CABBAGE_TREE_LOGS, TaiaoItemTags.CABBAGE_TREE_LOGS);
        copy(TaiaoBlockTags.MAMAKU_LOGS, TaiaoItemTags.MAMAKU_LOGS);
        copy(TaiaoBlockTags.THIN_LOGS, TaiaoItemTags.THIN_LOGS);

        // Vanilla block tags
        copy(BlockTags.LEAVES, ItemTags.LEAVES);
        copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
        copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);

        copy(BlockTags.PLANKS, ItemTags.PLANKS);
        copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
        copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
        copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
        copy(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES);
    }
}
